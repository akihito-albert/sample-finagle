package sample.finagle.services
import com.twitter.finagle.Service
import com.twitter.finagle.http._
import com.twitter.util.Future

class EchoService extends Service[Request, Response]{

  def apply(req: Request): Future[Response] = {

    val res = Response(req.version, Status.Ok)
    res.setContentString(req.contentString)
    Future.value(res)
  }
}
