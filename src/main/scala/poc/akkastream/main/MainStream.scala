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

  /** Publishing **/
  publishInRabbit
  publishInKafka
  /** End Publishing **/

  val actorRabbitSource =  getSource /*via flowFormat via flowIdentifier*/ to getACKSink run()
  val actorKafkaSource =  getSource /*via flowFormat via flowIdentifier*/ to getACKSink run()

  val asyncRabbitMessageActor = system.actorOf(Props(new AsyncMessageConsumer(actorRabbitSource)))
  val asyncKafkaMessageActor = system.actorOf(Props(new AsyncMessageConsumer(actorKafkaSource)))

  val kafkaConsumer = system.actorOf(Props(new KafkaConsumer(actorRef = asyncKafkaMessageActor)))
  kafkaConsumer.tell("",kafkaConsumer)
  val camelConsumer = system.actorOf(Props(new CamelConsumer(actorRef = asyncRabbitMessageActor)))


  private def publishInRabbit = {
    val publish: PublisherBase = Publisher.apply
    publish.basicPublish("192.168.16.172", 8081, "hola soy un mensaje de rabbit")("consumerExchange", "cola1", "camel", 5000)
  }

  private def publishInKafka = {
    val kafka:KafkaProducer = new KafkaProducer
    kafka.produce
  }

  private def getSource() = {
    Source.actorRef(50000, OverflowStrategy.fail)
  }

  private def getACKSink = {
    Sink.actorRefWithAck[String](system.actorOf(Props[CamelSubscriber]), INITMESSAGE, ACK, ONCOMPLETE, th => th.getMessage)
  }
}


