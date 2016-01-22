package sample.finagle.filters

import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.finagle.http._
import com.twitter.util.Future

// 例えば、Filterを使って例外ハンドラのようなものも作れる
class ExceptionFilter extends SimpleFilter[Request, Response] {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    println("exception filter start.")
    service(request) handle { case error =>
        val statusCode = error match {
          case ex: IllegalArgumentException => Status.BadRequest
          case _ => Status.InternalServerError
        }
        println(s"statusCode : ${statusCode.code}")
        val errorResponse = Response(Versions.HTTP_1_1, statusCode)
        errorResponse.setContentString(error.getMessage)

        errorResponse
    }

  }
}
