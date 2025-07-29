//package weather.code
//
//import org.scalatra.test.scalatest.*
//import org.testcontainers.junit.jupiter.Container
//import org.testcontainers.junit.jupiter.Testcontainers
//import org.wiremock.integrations.testcontainers.WireMockContainer
//import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
//import sttp.client4.quick.*
//import weather.code.services.WeatherService
//
//// поскольку данный класс находится в пакете weather.code
//// то json file ищется в этом же пакете
////case class WeatherResource() {}
//
////@Testcontainers
//class WeatherServletTests extends ScalatraFunSuite with BeforeAndAfterAll:
////  addServlet(classOf[WeatherServlet], "/*")
////
////  val Port = 8080
////  val Host = "localhost"
////
////  @Container
////  val wireMockServer = new WireMockContainer("wiremock/wiremock:3.6.0")
////    .withMappingFromResource("/", classOf[WeatherResource], "current.json")
//
////  override def beforeAll(): Unit =
////    wireMockServer.start()
////
////
////  override def afterAll(): Unit =
////    wireMockServer.stop()
//
////  test("testing wiremock") {
////    get("/current") {
////      assert(status == 200)
////    }
////  }