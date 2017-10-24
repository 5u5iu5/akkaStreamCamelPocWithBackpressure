package poc.akkastream.camel

import akka.actor.ActorRef
import akka.camel.{CamelMessage, Consumer}
import akka.stream.actor.ActorPublisher
import akka.stream.actor.ActorPublisherMessage.Cancel

class CamelConsumer(actorRef: ActorRef) extends Consumer with ActorPublisher[String]{

  def endpointUri = "rabbitmq://localhost:8081/consumerExchange?username=guest&password=guest&autoDelete=false&routingKey=camel&queue=cola1"

  def receive = {
    case Cancel => context.stop(self)
    case msg: CamelMessage => actorRef ! msg.bodyAs[String]
  }

}
