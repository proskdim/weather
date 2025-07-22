package weather.code

import org.scalatra.test.scalatest._

class WeatherServletTests extends ScalatraFunSuite {

  addServlet(classOf[WeatherServlet], "/*")

  test("GET / on WeatherServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}
