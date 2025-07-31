package weather.code

import org.scalatest.funspec.AnyFunSpec
import weather.code.services.DataSourceProvider

class DataSourceProviderSuite extends AnyFunSpec:
  describe("h2 connection") {
    it("success connection") {
      val d = DataSourceProvider.findAllActorsNamesProgram
      val r = d.map(_.toString())
      r
    }
  }