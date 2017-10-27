package poc.akkastream.suscriber

import akka.stream.actor.{ActorSubscriber, OneByOneRequestStrategy}
import poc.akkastream.protocol.{ACK, INITMESSAGE}

class Subscriber extends ActorSubscriber {

  override val requestStrategy = OneByOneRequestStrategy

  override def receive = {
    case msg:String =>
      println("received %s" format msg)
      sender ! ACK
      context.actorSelection("akka://some-system/user/camelConsumer") ! ACK
    case INITMESSAGE =>
      println(s"initMessage")
      sender ! ACK
    case msg =>
      println("Untyped Message %s" format msg)
      sender ! ACK
  }
}
