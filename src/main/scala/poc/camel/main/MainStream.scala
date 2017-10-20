package poc.camel.main

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import poc.camel.protocol.{ACK, INITMESSAGE, ONCOMPLETE}
import poc.camel.publisher.{Publisher, PublisherBase}
import poc.camel.{CamelConsumer, CamelSubscriber}

object MainStream extends App {

  implicit val system = ActorSystem("some-system")
  implicit val materializer = ActorMaterializer()

  val source = Source.actorPublisher[String](Props[CamelConsumer])
  val sink = Sink.actorRefWithAck[String](system.actorOf(Props[CamelSubscriber]),INITMESSAGE,ACK,ONCOMPLETE, th => th.getMessage)

  val publish: PublisherBase = Publisher.apply
  publish.basicPublish("127.0.0.1", 8081, "hola", "consumerExchange", "cola1", "camel", 5000)

  source
    .map(input => input.toUpperCase)
    .to(sink)
    .run()

}
