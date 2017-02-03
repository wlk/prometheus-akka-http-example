name := "prometheus-akka-http-example"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq(
  "-unchecked",
  "-deprecation",
  "-encoding", "utf8",
  "-Xlint:missing-interpolator",
  "-Ywarn-unused-import",
  "-Ywarn-dead-code")


resolvers ++= Seq(
  "Sonatype release repository" at "https://oss.sonatype.org/content/repositories/releases/",
  Resolver.bintrayRepo("lonelyplanet", "maven")
)

libraryDependencies ++= {
  val akkaVersion           = "2.4.16"
  val akkaHttpVersion       = "10.0.3"
  val prometheusAkkaHttpVersion = "0.3.1"

  Seq(
    "com.typesafe.akka"    %% "akka-actor"                           % akkaVersion,
    "com.typesafe.akka"    %% "akka-slf4j"                           % akkaVersion,
    "com.typesafe.akka"    %% "akka-stream"                          % akkaVersion,
    "com.typesafe.akka"    %% "akka-http-core"                       % akkaHttpVersion,
    "com.typesafe.akka"    %% "akka-http"                            % akkaHttpVersion,
    "com.typesafe.akka"    %% "akka-http-spray-json"                 % akkaHttpVersion,
    "com.typesafe.akka"    %% "akka-http-testkit"                    % akkaHttpVersion % "test",
    "com.lonelyplanet"     %% "prometheus-akka-http"                 % prometheusAkkaHttpVersion
  )
}