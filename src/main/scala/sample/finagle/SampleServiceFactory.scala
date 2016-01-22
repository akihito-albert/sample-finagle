package sample.finagle


import com.twitter.finagle.{ClientConnection, Service, ServiceFactory, http}
import com.twitter.util.{Future, Time}
import sample.finagle.filters.SampleFilter
import sample.finagle.services.EchoService

/**
 * Created by akihito on 2016/01/09.
 */
object SampleServiceFactory extends ServiceFactory[http.Request, http.Response] {

  def apply(conn: ClientConnection): Future[Service[http.Request, http.Response]] = Future.value {

    val sampleFilter = new SampleFilter("This is SampleFilter", 99)
    val service = new EchoService

    val serviceWithSampleFilter: Service[http.Request, http.Response] =
    // こんな感じでService実行前にFilterを設定できる
      sampleFilter andThen sampleFilter andThen service

    serviceWithSampleFilter
  }

  def close(deadline: Time) = Future.value {}
}
