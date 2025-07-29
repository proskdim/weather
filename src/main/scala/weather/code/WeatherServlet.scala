package weather.code

import org.json4s.JValue
import org.scalatra.*
import weather.code.services.{WeatherParser, WeatherService}
import org.json4s.jackson.JsonMethods.*
import org.json4s.JsonDSL.*
import scala.util.{Failure, Success}

class WeatherServlet extends ApplicationServlet:
  private val currentUrl = "http://api.weatherapi.com/v1/current.json"
  private val historicApi = "http://api.weatherapi.com/v1/forecast.json"

  private def renderSuccess(value: JValue) = compact(render(value))
  private def renderFailure(value: JValue) = compact(render("error" -> value))
  private def renderNotFound = compact(render("error" -> "data not found"))

  private def defaultParams = Map(
    "q" -> "Krasnoyarsk",
    "key" -> sys.env.get("API_KEY").getOrElse("no"),
    "aqi" -> "no",
    "lang" -> "ru"
  )

  private def historicParams = defaultParams ++ Map(
    "days" -> "1",
    "alerts" -> "no"
  )

  get("/current") {
    val response = WeatherService.fetchWeather(currentUrl, defaultParams)

    if response.isSuccess then
      WeatherParser.parseCurrentWeather(response.body) match
        case Success(value) => renderSuccess(value)
        case Failure(exc)   => renderFailure(exc.getMessage)
    else renderNotFound
  }

  get("/historical") {
    val response = WeatherService.fetchWeather(historicApi, historicParams)

    if response.isSuccess then
      WeatherParser.parseHistoricWeather(response.body) match
        case Success(value) => renderSuccess(value)
        case Failure(exc)   => renderFailure(exc.getMessage)
    else renderNotFound
  }

  get("/historical/max") {
    val response = WeatherService.fetchWeather(historicApi, historicParams)

    if response.isSuccess then
      WeatherParser.parseHistoricWeatherByTag(response.body, "maxtemp_c") match
        case Success(value) => renderSuccess(value)
        case Failure(exc)   => renderFailure(exc.getMessage)
    else renderNotFound
  }

  get("/historical/min") {
    val response = WeatherService.fetchWeather(historicApi, historicParams)

    if response.isSuccess then
      WeatherParser.parseHistoricWeatherByTag(response.body, "mintemp_c") match
        case Success(value) => renderSuccess(value)
        case Failure(exc)   => renderFailure(exc.getMessage)
    else renderNotFound
  }

  get("/historical/avg") {
    val response = WeatherService.fetchWeather(historicApi, historicParams)

    if response.isSuccess then
      WeatherParser.parseHistoricWeatherByTag(response.body, "avgtemp_c") match
        case Success(value) => renderSuccess(value)
        case Failure(exc)   => renderFailure(exc.getMessage)
    else renderNotFound
  }
