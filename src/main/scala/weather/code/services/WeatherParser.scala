package weather.code.services

import org.json4s.JValue
import org.json4s.JsonDSL.*
import ujson.*
import scala.collection.mutable
import scala.util.Try

case object WeatherParser:
  def parseCurrentWeather(data: String): Try[JValue] =
    Try {
      val json = ujson.read(data)

      val current = fetchObj(json, "current")
      val temp = fetchNum(current, "temp_c")
      val wind = fetchNum(current, "wind_mph")

      val condition = fetchObj(current, "condition")
      val text = fetchStr(condition, "text")

      val location = fetchObj(json, "location")
      val time = fetchStr(location, "localtime")

      ("temp" -> temp) ~
        ("wind" -> wind) ~
        ("text" -> text) ~
        ("time" -> time)
    }

  def parseHistoricWeather(data: String): Try[JValue] =
    Try {
      val json = ujson.read(data)

      val forecast = fetchObj(json, "forecast")
      val forecastDay = forecast("forecastday").arr(0).obj
      val hours = forecastDay("hour").arr.map(h => h.obj)

      ("hours" -> hours.map { h =>
        (("time" -> h("time").str) ~
          ("temp" -> h("temp_c").num))
      })
    }

  def parseHistoricWeatherByTag(data: String, tag: String): Try[JValue] =
    Try {
      val json = ujson.read(data)
      val temp = fetchTempFromDay(json, tag)
      ("temp" -> temp)
    }

  private def fetchForecastDay(
      value: ujson.Value
  ): mutable.Map[String, ujson.Value] =
    val forecast = fetchObj(value, "forecast")
    val forecastDay = forecast("forecastday").arr(0).obj
    fetchObj(forecastDay, "day")

  private def fetchTempFromDay(value: ujson.Value, key: String): Double =
    val day = fetchForecastDay(value)
    fetchNum(day, key)

  private def fetchObj(
      value: Value,
      key: String
  ): mutable.Map[String, Value] = value(key).obj

  private def fetchNum(value: Value, key: String): Double = value(key).num

  private def fetchStr(value: Value, key: String): String = value(key).str
