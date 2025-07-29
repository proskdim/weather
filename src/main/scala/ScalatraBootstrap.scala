import weather.code.*
import org.scalatra.*
import jakarta.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle:
  override def init(context: ServletContext): Unit =
    context.mount(new WeatherServlet, "/weather/*")
    context.mount(new ApplicationServlet, "/*")
