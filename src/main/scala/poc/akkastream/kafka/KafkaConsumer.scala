package poc.akkastream.kafka

import akka.actor.ActorRef
import akka.kafka.Subscriptions
import akka.kafka.scaladsl.Consumer
import akka.stream.actor.ActorPublisher
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.consumer.ConsumerRecord
import poc.akkastream.protocol.{ACK, INITMESSAGE, ONCOMPLETE}


class KafkaConsumer(actorRef: ActorRef) extends KafkaConn with ActorPublisher[String] {
  def consume = {
    Consumer.plainSource(consumerSettings, Subscriptions.topics("topic1"))
      .map(doSomething)
      .to(Sink.actorRefWithAck(actorRef, INITMESSAGE, ACK, ONCOMPLETE, th => th.getMessage)).run()
  }

  def doSomething(record: ConsumerRecord[Array[Byte], String]): String = {
    record.value().toString
  }

  override def receive = {
    case msg: String => consume
  }
}
