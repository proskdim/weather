package weather.code

import org.scalatest.BeforeAndAfterAll
import org.scalatest.funspec.AnyFunSpec
import org.testcontainers.junit.jupiter.*
import org.wiremock.integrations.testcontainers.WireMockContainer
import weather.code.services.{WeatherParser, WeatherService}

import scala.util.{Failure, Success}

case class WeatherResource() {}

@Testcontainers
class WeatherParserSuite extends AnyFunSpec with BeforeAndAfterAll:

  @Container
  val mockServerCurrent = new WireMockContainer("wiremock/wiremock:3.6.0")
    .withMappingFromResource("/", classOf[WeatherResource], "current.json")

  @Container
  val mockServerHistoric = new WireMockContainer("wiremock/wiremock:3.6.0")
    .withMappingFromResource("/", classOf[WeatherResource], "historic.json")

  override def beforeAll(): Unit =
    mockServerCurrent.start()
    mockServerHistoric.start()

  override def afterAll(): Unit =
    mockServerCurrent.stop()
    mockServerHistoric.stop()

  describe("/current") {
    it("current parse with valid data") {
      val url =
        mockServerCurrent.getUrl("http://api.weatherapi.com/v1/current.json")
      val response = WeatherService.fetchWeather(url, Map())
      val result = WeatherParser.parseCurrentWeather(response.body)

      assert(result.isSuccess)

      val Success(sc) = result
      assert(sc.getClass.getSimpleName == "JObject")
    }

    it("current parse with invalid data") {
      val url = mockServerCurrent.getUrl(
        "http://api.weatherapi.com/v1/invalid-current.json"
      )
      val response = WeatherService.fetchWeather(url, Map())
      val result = WeatherParser.parseCurrentWeather(response.body)
      assert(result.isFailure)

      val Failure(fl) = result
      assert(fl.getMessage.equals("key not found: temp_c"))
    }

    it("current parse with invalid json") {
      val url = mockServerCurrent.getUrl(
        "http://api.weatherapi.com/v1/invalid-current-json.json"
      )
      val response = WeatherService.fetchWeather(url, Map())
      val result = WeatherParser.parseCurrentWeather(response.body)
      assert(result.isFailure)

      val Failure(fl) = result
      assert(fl.getMessage.equals("key not found: current"))
    }
  }

  describe("/historic") {
    it("historic parse with valid data") {
      val url =
        mockServerHistoric.getUrl("http://api.weatherapi.com/v1/forecast.json")
      val response = WeatherService.fetchWeather(url, Map())
      val result = WeatherParser.parseHistoricWeather(response.body)

      assert(result.isSuccess)

      val Success(sc) = result
      assert(sc.getClass.getSimpleName == "JObject")
    }

    it("historic parse with invalid data") {
      val url = mockServerHistoric.getUrl(
        "http://api.weatherapi.com/v1/invalid-forecast.json"
      )
      val response = WeatherService.fetchWeather(url, Map())
      val result = WeatherParser.parseHistoricWeather(response.body)
      assert(result.isFailure)

      val Failure(fl) = result
      assert(fl.getMessage.equals("key not found: forecast"))
    }

    it("historic parse max temp") {
      val url =
        mockServerHistoric.getUrl("http://api.weatherapi.com/v1/forecast.json")
      val response = WeatherService.fetchWeather(url, Map())
      val result =
        WeatherParser.parseHistoricWeatherByTag(response.body, "maxtemp_c")
      assert(result.isSuccess)

      assert(result.get.toSome.isDefined)
    }

    it("historic parse min temp") {
      val url =
        mockServerHistoric.getUrl("http://api.weatherapi.com/v1/forecast.json")
      val response = WeatherService.fetchWeather(url, Map())
      val result =
        WeatherParser.parseHistoricWeatherByTag(response.body, "mintemp_c")
      assert(result.isSuccess)

      assert(result.get.toSome.isDefined)
    }

    it("historic parse avg temp") {
      val url =
        mockServerHistoric.getUrl("http://api.weatherapi.com/v1/forecast.json")
      val response = WeatherService.fetchWeather(url, Map())
      val result =
        WeatherParser.parseHistoricWeatherByTag(response.body, "avgtemp_c")
      assert(result.isSuccess)

      assert(result.get.toSome.isDefined)
    }
  }
