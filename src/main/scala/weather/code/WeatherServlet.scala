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
    else halt(404, "Weather data not found: ")
  }

  get("/weather/historical") {}

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
