name := "prometheus-akka-http-example"

version := "1.0"

scalaVersion := "2.13.6"

libraryDependencies ++= {
  val akkaVersion = "2.6.16"
  val akkaHttpVersion = "10.2.6"
  val prometheusAkkaHttpVersion = "0.6.0"

  Seq(
    "com.typesafe.akka" %% "akka-actor"           % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j"           % akkaVersion,
    "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core"       % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % "test",
    "com.varwise"       %% "prometheus-akka-http" % prometheusAkkaHttpVersion
  )
}

addCommandAlias("formatAll", ";scalafmt;Test/scalafmt;scalafmtSbt")
