package poc.akkastream.main

import akka.actor.{ActorRef, Props}
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}
import akka.stream.{ActorMaterializer, ClosedShape, OverflowStrategy}
import poc.akkastream.kafka.KafkaConsumer
import poc.akkastream.main.LaunchStream.system
import poc.akkastream.protocol.{ACK, INITMESSAGE, ONCOMPLETE}
import poc.akkastream.publisher.{PublisherBase, PublisherKafkaMain}
import poc.akkastream.suscriber.KafkaSubscriber

object AkkaStreamKafka {
  def apply: AkkaStreamKafka = new AkkaStreamKafka()
}

class AkkaStreamKafka {
  implicit val materializer = ActorMaterializer()

  //def goStream = sourceForKafka via f1 via f2 to sinkForKafka(Props[KafkaSubscriber]) run()

  def f1 = Flow[String].map(_.toString)

  def f2 = Flow[String].map(_ + " Kafka Flow")


  def publishInKafka = {
    val publish: PublisherBase = PublisherKafkaMain.apply
    publish.basicPublish("localhost", 9092, "hola vengo de kafka", 1000)("", "", "", "topic1")
  }

  def consumerKafkaActor(actorRef: ActorRef) = system.actorOf(Props(new KafkaConsumer(actorRef)), "kafkaConsumer")

  def sourceForKafka: Source[String, ActorRef] = {
    Source.actorRef(500, OverflowStrategy.fail)
  }

  def sinkForKafka(consumer: Props) = {
    Sink.actorRefWithAck[String](system.actorOf(consumer), INITMESSAGE, ACK, ONCOMPLETE, th => th.getMessage)
  }
}
