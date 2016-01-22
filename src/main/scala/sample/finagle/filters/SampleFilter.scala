package sample.finagle.filters

import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

// Filterを使ってサービス呼び出しの前後に処理を挟み込める
// extendsしているSimpleFilterはRequest, Responseに何も手を加えないFilter.
// req/resのロギングなどに使える
class SampleFilter(val1: String, val2: Int) extends SimpleFilter[Request, Response] {
  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    println(s"Sample filter(val1: $val1, val2: $val2) start.")
    val res = service(request)
    res.onSuccess(rep => println(s"${rep.status}"))
    res.onFailure(rep => println(s"Error: ${rep.getMessage}"))
    res
  }
}
