package sample.finagle

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Method, Request, Response}
import com.twitter.util.{Await, Future}

object MinimalFinagleClient extends App {

  val httpService: Service[Request, Response] = Http.newService("localhost:8080")
  val request = Request(Method.Get, "/")

  val response: Future[Response] = httpService(request)
  response.onSuccess { res: Response =>
    println(s"Success: $res")
  }
  response.onFailure { th: Throwable =>
    println(s"Failure: ${th.getMessage}")
  }

  val result = Await.result(response)
  println(s"content : ${result.getContentString()}");
}
