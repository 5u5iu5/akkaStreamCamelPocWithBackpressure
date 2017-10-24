package poc.akkastream.camel

import akka.stream.actor.{ActorSubscriber, OneByOneRequestStrategy}
import poc.akkastream.protocol.{ACK, INITMESSAGE}

class CamelSubscriber extends ActorSubscriber {

  override val requestStrategy = OneByOneRequestStrategy

  def receive = {
    case msg: String =>
      println("received %s" format msg)
      sender ! ACK

    case INITMESSAGE =>
      println(s"initMessage")
      sender ! ACK

  }
}
