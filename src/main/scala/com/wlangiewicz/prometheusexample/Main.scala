package com.wlangiewicz.prometheusexample

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.lonelyplanet.prometheus.PrometheusResponseTimeRecorder
import com.lonelyplanet.prometheus.api.MetricsEndpoint
import com.lonelyplanet.prometheus.directives.ResponseTimeRecordingDirectives
import akka.http.scaladsl.server.Directives._

object Main extends App {
  implicit val system = ActorSystem("prometheusexample")
  implicit val dispatcher = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val api = new Api()

  val server = Http().bindAndHandle(api.routes, interface = "0.0.0.0", port = 8080)

}

class Api {
  val prometheusRegistry = PrometheusResponseTimeRecorder.DefaultRegistry
  val prometheusResponseTimeRecorder = PrometheusResponseTimeRecorder.Default

  val metricsEndpoint = new MetricsEndpoint(prometheusRegistry)

  val testEndpoint = new TestEndpoint(prometheusResponseTimeRecorder)

  val routes = metricsEndpoint.routes ~ testEndpoint.routes
}


class TestEndpoint(responseTimeRecorder: PrometheusResponseTimeRecorder) {
  private val responseTimeDirectives = ResponseTimeRecordingDirectives(responseTimeRecorder)
  import responseTimeDirectives._
  val r = scala.util.Random

  val routes = {
    get {
      path("example") {
        recordResponseTime("/example") {
          complete {
            val sleepTime = r.nextInt(1000)
            Thread.sleep(sleepTime)
            "OK"
          }
        }
      }
    }
  }
}