package weather.code

import org.scalatra.Ok

class HealthServlet extends ApplicationServlet:
  get("/health") {
    Ok("""{"status": "ok"}""")
  }
