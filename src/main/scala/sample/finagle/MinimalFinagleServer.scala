package sample.finagle

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.util.{Await, Future}

/**
 * 最小構成のHttpサーバ
 */
object MinimalFinagleServer extends App {

  // APIはサービスとして定義する
  // サービスは
  val service = new Service[Request, Response] {
    def apply(request: Request): Future[Response] = {
      println("Request received.")

      val response = Response(request.version, Status.Ok)
      response.setContentString("Hello, Finagle.")

      Future.value(response)
    }
  }

  val server = Http.serve(":8080", service)
  Await.ready(server)
}
