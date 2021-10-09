name := "prometheus-akka-http-example"

version := "1.0"

scalaVersion := "3.0.2"

libraryDependencies ++= {
  val akkaVersion = "2.6.16"
  val akkaHttpVersion = "10.2.6"
  val prometheusAkkaHttpVersion = "0.6.0"

  Seq(
    ("com.typesafe.akka" %% "akka-actor"           % akkaVersion).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka" %% "akka-slf4j"           % akkaVersion).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka" %% "akka-stream"          % akkaVersion).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka" %% "akka-http-core"       % akkaHttpVersion).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka" %% "akka-http"            % akkaHttpVersion).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion).cross(CrossVersion.for3Use2_13),
    "com.varwise" %% "prometheus-akka-http" % prometheusAkkaHttpVersion
  )
}

addCommandAlias("formatAll", ";scalafmt;Test/scalafmt;scalafmtSbt")
