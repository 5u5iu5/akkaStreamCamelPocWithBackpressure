package poc.akkastream.main

import akka.actor.{ActorSystem, Props}
import akka.camel.CamelMessage
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import poc.akkastream.camel.{CamelConsumer, CamelSubscriber}
import poc.akkastream.protocol.{ACK, INITMESSAGE, ONCOMPLETE}
import poc.akkastream.publisher.{Publisher, PublisherBase}
import poc.akkastream.AsyncMessageConsumer
import poc.akkastream.kafka.{KafkaConsumer, KafkaProducer}

object MainStream extends App {

  implicit val system = ActorSystem("some-system")
  implicit val materializer = ActorMaterializer()

  val source = Source.actorRef(50000,OverflowStrategy.fail)
  val sink = Sink.actorRefWithAck[String](system.actorOf(Props[CamelSubscriber]),INITMESSAGE,ACK,ONCOMPLETE, th => th.getMessage)
  val sink2 = Sink.actorRefWithAck[String](system.actorOf(Props[CamelSubscriber]),INITMESSAGE,ACK,ONCOMPLETE, th => th.getMessage)

  val flowFormat = Flow[String].map(s => s.toString)
    //s.split(":").filterNot(_.exists(_.isDigit)).mkString(" ")

  val flowIdentifier = Flow[String].filter(c => c.contains("pepe")).map(s => s.replace("pepe", "Sr. Pepe"))

  /** Publishing **/
  publishInRabbit
  publishInKafka
  /** Publishing **/

  val actorSource =  source /*via flowFormat via flowIdentifier*/ to sink run()
  val actorSource2 =  source /*via flowFormat via flowIdentifier*/ to sink2 run()

  val asyncMessageActor = system.actorOf(Props(new AsyncMessageConsumer(actorSource)))
  val asyncMessageActor2 = system.actorOf(Props(new AsyncMessageConsumer(actorSource2)))


  val kafkaConsumer = system.actorOf(Props(new KafkaConsumer(actorRef = asyncMessageActor2)))
  kafkaConsumer.tell("",kafkaConsumer)
  val camelConsumer = system.actorOf(Props(new CamelConsumer(actorRef = asyncMessageActor)))

  private def publishInRabbit = {
    val publish: PublisherBase = Publisher.apply
    publish.basicPublish("localhost", 8081, "hola:soy:pepe")("consumerExchange", "cola1", "camel", 5000)
  }

  private def publishInKafka = {
    val kafka:KafkaProducer = new KafkaProducer
    kafka.produce
  }
}


