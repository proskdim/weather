val ScalatraVersion = "3.1.1"

ThisBuild / scalaVersion := "3.3.4"
ThisBuild / organization := "com.example"

lazy val hello = (project in file("."))
  .settings(
    name := "weather",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra-jakarta" % ScalatraVersion,
      "org.scalatra" %% "scalatra-scalatest-jakarta" % ScalatraVersion % "test",
      "ch.qos.logback" % "logback-classic" % "1.5.6" % "runtime",
      "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % "provided",
      "org.scalatra" %% "scalatra-json-jakarta" % "3.0.0",
      "org.json4s"   %% "json4s-jackson" % "4.0.6",
      "com.softwaremill.sttp.client4" %% "core" % "4.0.9",
      "com.lihaoyi" %% "upickle" % "4.1.0"
    ),
  )

enablePlugins(SbtTwirl)
enablePlugins(SbtWar)

Test / fork := true
