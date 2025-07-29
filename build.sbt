val ScalatraVersion = "3.1.1"

ThisBuild / scalaVersion := "3.3.4"
ThisBuild / organization := "com.example"

val scalatra = "org.scalatra" %% "scalatra-jakarta" % ScalatraVersion
val scalatest = "org.scalatest" %% "scalatest" % "3.2.19" % Test
val scalatratest =
  "org.scalatra" %% "scalatra-scalatest-jakarta" % ScalatraVersion % "test"
val logback = "ch.qos.logback" % "logback-classic" % "1.5.6" % "runtime"
val jakartaapi =
  "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % "provided"
val scalatrajson = "org.scalatra" %% "scalatra-json-jakarta" % "3.0.0"
val jackson = "org.json4s" %% "json4s-jackson" % "4.0.6"
val sttp = "com.softwaremill.sttp.client4" %% "core" % "4.0.9"
val upickle = "com.lihaoyi" %% "upickle" % "4.1.0"
val testcontainers = "org.testcontainers" % "testcontainers" % "1.21.3" % Test
val containersjunit = "org.testcontainers" % "junit-jupiter" % "1.21.3" % Test
val containersspock = "org.testcontainers" % "spock" % "1.21.3" % Test
val containerwiremock =
  "org.wiremock.integrations.testcontainers" % "wiremock-testcontainers-module" % "1.0-alpha-15"

lazy val hello = (project in file("."))
  .settings(
    name := "weather",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      scalatra,
      scalatest,
      scalatratest,
      logback,
      jakartaapi,
      scalatrajson,
      jackson,
      sttp,
      upickle,
      testcontainers,
      containersjunit,
      containersspock,
      containerwiremock
    )
  )

enablePlugins(SbtWar)

Test / fork := true
