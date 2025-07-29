package weather.code

import org.scalatra.ScalatraServlet

class ApplicationServlet extends ScalatraServlet:
  before() {
    contentType = "application/json"
  }

  after() {
    response.setHeader("Access-Control-Allow-Origin", "*")
  }

  notFound {
    """{"error": "resource not found"}"""
  }
