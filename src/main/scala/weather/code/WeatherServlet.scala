package weather.code

import org.scalatra.*
import weather.code.services.WeatherService
import org.json4s.jackson.JsonMethods.*
import org.json4s.JsonDSL.*
import weather.code.utils.*

import scala.collection.mutable

class WeatherServlet extends ScalatraServlet:
  before() {
    contentType = "application/json"
  }

  after() {
    response.setHeader("Access-Control-Allow-Origin", "*")
  }

  get("/current") {
    val response = WeatherService.getCurrentWeather()

    if response.code.isSuccess then
      val json = ujson.read(response.body)

      val current = fetchObj(json, "current")
      val temp = fetchNum(current, "temp_c")
      val wind = fetchNum(current, "wind_mph")

      val condition = fetchObj(current, "condition")
      val text = fetchStr(condition, "text")

      val location = fetchObj(json, "location")
      val time = fetchStr(location, "localtime")

      compact(
        render(
          ("temp" -> temp) ~
            ("wind" -> wind) ~
            ("text" -> text) ~
            ("time" -> time)
        )
      )
    else halt(404, "Weather data not found")
  }

  get("/historical") {
    val response = WeatherService.getForecast()

    if response.code.isSuccess then
      val json = ujson.read(response.body)

      val forecast = fetchObj(json, "forecast")
      val forecastDay = forecast("forecastday").arr(0).obj
      val hours = forecastDay("hour").arr.map(h => h.obj)

      compact(
        render(
          ("hours" -> hours.map { h =>
            (("time" -> h("time").str) ~
              ("temp" -> h("temp_c").num))
          })
        )
      )
    else halt(404, "Weather data not found")
  }

  get("/historical/max") {
    val response = WeatherService.getForecast()

    if response.code.isSuccess then
      val json = ujson.read(response.body)
      val temp = fetchTempFromDay(json, "maxtemp_c")

      compact(
        render(
          ("temp" -> temp)
        )
      )
    else halt(404, "Weather data not found")
  }

  get("/historical/min") {
    val response = WeatherService.getForecast()

    if response.code.isSuccess then
      val json = ujson.read(response.body)
      val temp = fetchTempFromDay(json, "mintemp_c")

      compact(
        render(
          ("temp" -> temp)
        )
      )
    else halt(404, "Weather data not found")
  }

  get("/historical/avg") {
    val response = WeatherService.getForecast()

    if response.code.isSuccess then
      val json = ujson.read(response.body)
      val temp = fetchTempFromDay(json, "avgtemp_c")

      compact(
        render(
          ("temp" -> temp)
        )
      )
    else halt(404, "Weather data not found")
  }

  // helpers
  private def fetchForecastDay(
      value: ujson.Value
  ): mutable.Map[String, ujson.Value] =
    val forecast = fetchObj(value, "forecast")
    val forecastDay = forecast("forecastday").arr(0).obj
    fetchObj(forecastDay, "day")

  private def fetchTempFromDay(value: ujson.Value, key: String): Double =
    val day = fetchForecastDay(value)
    fetchNum(day, key)
