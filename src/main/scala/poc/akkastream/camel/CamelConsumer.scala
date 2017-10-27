package poc.akkastream.camel

import akka.camel.{CamelMessage, Consumer}
import akka.stream.actor.ActorPublisher
import akka.stream.actor.ActorPublisherMessage.Cancel
import poc.akkastream.protocol.ACK

class CamelConsumer extends Consumer with ActorPublisher[String]{

  def endpointUri = "rabbitmq://localhost:8081/consumerExchange?username=guest&password=guest&autoDelete=false&routingKey=camel&queue=cola1"

  override def autoAck: Boolean = false

  var rabbitRefAmpq: String = ""

  def receive = {
    case Cancel => context.stop(self)
    case msg: CamelMessage if totalDemand > 0 =>
      rabbitRefAmpq = sender.path.toString
      onNext(msg.bodyAs[String])
    case ACK => context.actorSelection(rabbitRefAmpq) ! ACK
  }

}
