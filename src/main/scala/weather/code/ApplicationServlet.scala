package weather.code

import org.scalatra.ScalatraServlet

class ApplicationServlet extends ScalatraServlet:
  before() {
    contentType = "application/json"
  }

  get("/health") { "OK" }
