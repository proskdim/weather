package weather.code

import org.scalatra.test.scalatest.*

class HealthServletTests extends ScalatraFunSuite:
  addServlet(classOf[HealthServlet], "/*")

  test("testing health status") {
    get("/health") {
      assert(status == 200)
      assert(body == """{"status": "ok"}""")
    }
  }

  test("testing 404") {
    get("/foo") {
      assert(status == 404)
      assert(body == """{"error": "resource not found"}""")
    }
  }
