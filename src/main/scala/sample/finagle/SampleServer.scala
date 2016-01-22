package sample.finagle

import java.net.InetSocketAddress

import com.twitter.conversions.time._
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.Http
import com.twitter.finagle.http.Method.{Post, Put}
import com.twitter.finagle.http.path._
import com.twitter.finagle.http.service.RoutingService
import sample.finagle.filters.{ExceptionFilter, SampleFilter}
import sample.finagle.services.{CustomerAttrRegisterService, EchoService}

object SampleServer extends App {

  // Filter
  val sampleFilter    = new SampleFilter("This is SampleFilter", 99)
  val exceptionFilter = new ExceptionFilter

  // com.twitter.finagle.http.service.RoutingServiceを利用するとルーティングを定義できる
  val routing = RoutingService.byMethodAndPathObject {
    case Post -> Root / "echo" => new EchoService
    // こんな感じでuri中のidなどを取ることもできる。取った値はクラスインスタンス生成時に属性として渡す。
    case Put  -> Root / "register_customer_attr" / Integer(id) => new CustomerAttrRegisterService(id)
  }

  val server = ServerBuilder()
    .codec(Http())
    .bindTo(new InetSocketAddress(8080))
    .name("sampleServer")
    .maxConcurrentRequests(1000)
    .keepAlive(true)
    .readTimeout(1000.seconds)
    .build{
      sampleFilter andThen exceptionFilter andThen routing
    }
}
