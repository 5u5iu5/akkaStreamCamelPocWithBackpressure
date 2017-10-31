package poc.akkastream.suscriber

import akka.stream.actor.{ActorSubscriber, OneByOneRequestStrategy}
import poc.akkastream.protocol.{ACK, INITMESSAGE}

class KafkaSubscriber extends ActorSubscriber {

  override val requestStrategy = OneByOneRequestStrategy

  override def receive = {
    case msg:String =>
      println("received %s " format msg)
      sender ! ACK
    case INITMESSAGE =>
      println(s"initMessage")
      sender ! ACK
  }
}
