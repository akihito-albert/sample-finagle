package sample.finagle.services

import com.twitter.finagle.Service
import com.twitter.finagle.http._
import com.twitter.util.Future

/**
  * Created by akihito on 2016/01/09.
  */
class CustomerAttrRegisterService(id: Int) extends Service[Request, Response] {

  def apply(req: Request): Future[Response] = {
    println(s"id: $id")
    if (id == 0) {
      throw new IllegalArgumentException(s"using id:$id is Not allowed.")
    }
    val res = Response(req.version, Status.Accepted)
    res.setContentString(s"request accepted. id: $id")

    Future.value(res)
  }

}

