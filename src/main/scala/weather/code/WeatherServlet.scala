package weather.code

import org.scalatra.*
import weather.code.services.WeatherService

import scala.collection.mutable

// JSON-related libraries
import org.json4s.jackson.JsonMethods.*
import org.json4s.JsonDSL.*

class WeatherServlet extends ScalatraServlet:
  before() {
    contentType = "application/json"
  }

  get("/weather/current") {
    val response = WeatherService.getCurrentWeather()

    if response.code.isSuccess then
      val json = ujson.read(response.body)
      val weather: mutable.Map[String, ujson.Value] = json("current").obj
      val temperature: Double = weather("temp_c").num
      val windSpeed: Double = weather("wind_mph").num
      val result = ("temperature" -> temperature) ~ ("windSpeed" -> windSpeed)

      compact(render(result))
    else halt(404, "Weather data not found")
  }

  get("/weather/historical") {
    val response = WeatherService.getForecast()

    if response.code.isSuccess then
      val json = ujson.read(response.body)
      val forecast: mutable.Map[String, ujson.Value] = json("forecast").obj
      val forecastDay: mutable.Map[String, ujson.Value] =
        forecast("forecastday").arr(0).obj

      val day: mutable.Map[String, ujson.Value] = forecastDay("day").obj
      val maxTemp: Double = day("maxtemp_c").num
      val minTemp: Double = day("mintemp_c").num
      val avgTemp: Double = day("avgtemp_c").num

      val dayResult =
        ("maxTemp" -> maxTemp) ~ ("minTemp" -> minTemp) ~ ("avgTemp" -> avgTemp)

      val hourObjects = forecastDay("hour").arr
        .map(h => h.obj)

      val hoursResult = ("hours" -> hourObjects.map { h =>
        (("time" -> h("time").str) ~ ("temp" -> h("temp_c").num))
      })

      compact(render(dayResult ~ hoursResult))
    else halt(404, "Weather data not found")
  }

  get("/weather/historical/max") {}

  get("/weather/historical/min") {}

  get("/weather/historical/avg") {}

  get("/health") {
    "OK"
  }

//example of json
//  get("/") {
//    val winners = List(
//      Winner(1, List(1, 2)),
//      Winner(2, List(3, 4)),
//    )
//
//    val json = ("winners" -> winners.map { w =>
//      (("id" -> w.id) ~ ("numbers" -> w.numbers))
//    })
//
//    compact(render(json))
//  }
