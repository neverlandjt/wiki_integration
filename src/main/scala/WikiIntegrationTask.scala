import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.unmarshalling.Unmarshal

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.xml.NodeSeq

object WikiIntegrationTask {

  def retrieveLatestChanges(): Future[Seq[WikiChange]] = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val uri = Uri("https://en.wikipedia.org/w/api.php") withQuery ("action", "feedrecentchanges") +: ("feedformat", "rss") +: Query.Empty

    Http().singleRequest(HttpRequest(uri = uri, headers = Accept(MediaTypes.`application/xml`) :: Nil))
      .map(_.entity).flatMap(Unmarshal(_).to[NodeSeq]).map(n =>
      (n \ "channel" \ "item").map(item =>
        WikiChange(title = (item \ "title").text, link = Uri((item \ "link").text),
          guid = Uri((item \ "guid").text), description = (item \ "description").text,
          pubDate = LocalDateTime.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse((item \ "pubDate").text)))))

  }

  case class WikiChange(title: String, link: Uri, guid: Uri, description: String, pubDate: LocalDateTime)

}