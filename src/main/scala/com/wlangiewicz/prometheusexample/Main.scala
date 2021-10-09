package com.wlangiewicz.prometheusexample

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.varwise.akka.http.prometheus.PrometheusResponseTimeRecorder
import com.varwise.akka.http.prometheus.api.MetricsEndpoint
import com.varwise.akka.http.prometheus.directives.ResponseTimeRecordingDirectives
import io.prometheus.client.CollectorRegistry

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object Main extends App {
  implicit private val system: ActorSystem = ActorSystem("prometheusexample")
  implicit private val dispatcher: ExecutionContextExecutor = system.dispatcher

  private val api = new Api()
  private val interface = "0.0.0.0"
  private val port = 8080
  private val server = Http().newServerAt(interface, port).bind(api.routes)

  server
    .onComplete {
      case Success(_) =>
        println(s"Example API up on port at $interface:$port")
      case Failure(ex) =>
        println(s"Unable to start example API on port $interface:$port")
        println(ex)
    }
}

class Api {
  private val prometheusRegistry: CollectorRegistry = PrometheusResponseTimeRecorder.DefaultRegistry
  private val prometheusResponseTimeRecorder: PrometheusResponseTimeRecorder = PrometheusResponseTimeRecorder.Default

  private val metricsEndpoint = new MetricsEndpoint(prometheusRegistry)

  private val testEndpoint = new TestEndpoint(prometheusResponseTimeRecorder)

  val routes: Route = metricsEndpoint.routes ~ testEndpoint.routes
}

class TestEndpoint(responseTimeRecorder: PrometheusResponseTimeRecorder) {
  private val responseTimeDirectives = ResponseTimeRecordingDirectives(responseTimeRecorder)

  import responseTimeDirectives._

  private val r = scala.util.Random

  val routes: Route = {
    get {
      path("example") {
        recordResponseTime("/example") {
          complete {
            val sleepTime = r.nextLong(1000)
            Thread.sleep(sleepTime)
            "OK"
          }
        }
      }
    }
  }
}
