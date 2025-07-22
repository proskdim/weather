package weather.code

import org.scalatra.*

class WeatherServlet extends ScalatraServlet:
  get("/") {
    views.html.hello()
  }
