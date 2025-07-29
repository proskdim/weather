package weather.code

import org.scalatra.test.scalatest.*

class ApplicationServletTests extends ScalatraFunSuite:
  addServlet(classOf[ApplicationServlet], "/*")

  test("health check") {
    get("/health") {
      assert(status == 200)
      assert(body == """{"status": "ok"}""")
    }
  }
