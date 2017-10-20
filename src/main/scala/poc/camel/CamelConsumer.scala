package poc.camel

import akka.camel.Consumer
import akka.stream.actor.ActorPublisher

class CamelConsumer extends Consumer with ActorPublisher[String] {
  def endpointUri = "rabbitmq://http://127.0.0.1:8081/consumerExchange?username=guest&password=guest&autoDelete=false&routingKey=camel&queue=cola1"

  import akka.stream.actor.ActorPublisherMessage._

  def receive = {
    case Request(_) => //ignored
    case Cancel =>
      context.stop(self)
    case msg if totalDemand > 0 => onNext(msg.toString)
  }
}
