package weather.code.services

import sttp.client4.Response
import sttp.client4.quick.*

case object WeatherService:
  def fetchWeather(url: String, params: Map[String, String]): Response[String] =
    quickRequest
      .get(uri"$url?$params")
      .header("Content-Type", "application/json")
      .send()
