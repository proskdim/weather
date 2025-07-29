package weather.code

class HealthServlet extends ApplicationServlet:
  get("/health") {
    """{"status": "ok"}"""
  }
