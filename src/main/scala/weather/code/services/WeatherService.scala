package weather.code.services

import sttp.client4.Response
import sttp.client4.quick.*

case object WeatherService:
  private val city = "Krasnoyarsk"
  private val apiKey = sys.env.get("API_KEY")

  def getCurrentWeather(): Response[String] =
    val queryParams = Map(
      "q" -> city,
      "key" -> apiKey.getOrElse("no"),
      "aqi" -> "no"
    )

    quickRequest
      .get(uri"http://api.weatherapi.com/v1/current.json?$queryParams")
      .header("Content-Type", "application/json")
      .send()

  def getForecast(): Response[String] =
    val queryParams = Map(
      "q" -> city,
      "days" -> 1,
      "key" -> apiKey.getOrElse("no"),
      "aqi" -> "no",
      "alerts" -> "no"
    )

    quickRequest
      .get(uri"http://api.weatherapi.com/v1/forecast.json?$queryParams")
      .header("Content-Type", "application/json")
      .send()
