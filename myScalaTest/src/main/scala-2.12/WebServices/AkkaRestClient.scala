package WebServices

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.util.ByteString


/**
  * Created by miguel.ludena on 28/08/2018.
  */
class AkkaRestClient extends Actor with ActorLogging{

  import context.dispatcher
  import akka.pattern.pipe


  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))

  val http = Http(context.system)

  override def preStart() = {
    http.singleRequest(HttpRequest(uri = "https://www.livescore.in/es/")).pipeTo(self)
  }

  def receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach{ body =>
        log.info("Tenemos Respuesta: " + body.utf8String)
      }

    case resp @ HttpResponse(code, _, _, _) =>
      log.info("Request failed : " + code)
      resp.discardEntityBytes()
  }

}

object AkkaRestClient {
  case class HttpResponse()
}

object Test extends App {
  implicit val system = ActorSystem("myTest")
  implicit val executionContext = system.dispatcher
  val launcher = system.actorOf(Props(new AkkaRestClient))

  launcher ! AkkaRestClient.HttpResponse

}
