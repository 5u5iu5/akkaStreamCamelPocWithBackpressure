package poc.akkastream.kafka

import akka.Done
import akka.actor.ActorRef
import akka.kafka.Subscriptions
import akka.kafka.scaladsl.Consumer
import akka.stream.actor.ActorPublisher
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.consumer.ConsumerRecord

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


class KafkaConsumer(actorRef:ActorRef) extends KafkaConn with ActorPublisher[String]{

  def consume = {
      Consumer.plainSource(consumerSettings, Subscriptions.topics("topic1"))
        .map(doSomething)
        .runWith(Sink.ignore)

  }

  def doSomething(record: ConsumerRecord[Array[Byte], String]): Unit = {
    println(record.value())
    actorRef ! record.value()
  }

  override def receive = {
    case msg:String => consume
  }
}
