package poc.akkastream.kafka

import akka.actor.ActorRef
import akka.kafka.Subscriptions
import akka.kafka.scaladsl.Consumer
import akka.stream.actor.ActorPublisher
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.consumer.ConsumerRecord


class KafkaConsumer(actorRef: ActorRef) extends KafkaConn with ActorPublisher[String] {

  def consume = {
    Consumer.plainSource(consumerSettings, Subscriptions.topics("topic1"))
      .map(doSomething)
      //.log("Log",s => println(s))
      .runWith(Sink.ignore)
  }

  def doSomething(record: ConsumerRecord[Array[Byte], String]): Unit = {
    actorRef ! record.value().toString
  }

  override def receive = {
    case msg: String => consume
  }
}
