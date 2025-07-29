package weather.code

import org.scalatra.ScalatraServlet
import org.scalatra.NotFound

class ApplicationServlet extends ScalatraServlet:
  before() {
    contentType = "application/json"
  }

  after() {
    response.setHeader("Access-Control-Allow-Origin", "*")
  }

  notFound {
    NotFound("""{"error": "resource not found"}""")
  }
