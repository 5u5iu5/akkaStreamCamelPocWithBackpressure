package poc.camel

import akka.stream.actor.{ActorSubscriber, OneByOneRequestStrategy}
import poc.camel.protocol.{ACK, INITMESSAGE}

class CamelSubscriber extends ActorSubscriber {

  override val requestStrategy = OneByOneRequestStrategy

  def receive = {
    case msg: String => println(msg)
      sender ! ACK
    case INITMESSAGE => println(s"initMessage")
      sender ! ACK
  }
}
