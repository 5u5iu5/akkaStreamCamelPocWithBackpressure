package poc.akkastream.main

import akka.NotUsed
import akka.actor.{ActorRef, Props}
import akka.stream.actor.ActorPublisher
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}
import akka.stream.{ClosedShape, OverflowStrategy}
import org.reactivestreams.Publisher
import poc.akkastream.camel.CamelConsumer
import poc.akkastream.main.LaunchStream.system
import poc.akkastream.protocol.{ACK, INITMESSAGE, ONCOMPLETE}
import poc.akkastream.publisher.{PublisherBase, PublisherRabbitMain}
import poc.akkastream.suscriber.CamelSubscriber

import scala.util.Random

object AkkaStreamCamel {
  def apply: AkkaStreamCamel = new AkkaStreamCamel()
}

class AkkaStreamCamel {

  def graphNormalCamelScenario(source: Source[String, NotUsed], sink: Sink[String, NotUsed], buffer: Int) =
    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._
      val in = source.buffer(buffer, OverflowStrategy.backpressure)
      val out = sink

      in ~> f1 ~> f2 ~> f3 ~> out
      ClosedShape
    })

  def f1 = Flow[String].map(_.toString)

  def f2 = Flow[String].map(_ + " Flow2")

  def f3 = Flow[String].map(s => {
    getRandom(50, 500)
    s.toString
  })

  def publishInRabbit = {
    val publish: PublisherBase = PublisherRabbitMain.apply
    publish.basicPublish("localhost", 8081, "hola vengo de rabbit", 10000)("consumerExchange", "cola1", "camel", "")
  }

  def consumerCamelActor = system.actorOf(Props(new CamelConsumer), "camelConsumer")

  def sourceForCamel(consumer: ActorRef): Source[String, NotUsed] = {
    val publisher: Publisher[String] = ActorPublisher(consumer)
    Source.fromPublisher(publisher)
  }

  def sinkForCamel =
    Sink.actorRefWithAck[String](system.actorOf(Props[CamelSubscriber]),
      INITMESSAGE, ACK, ONCOMPLETE, th => th.getMessage)

  def getRandom(from: Int, to: Int): Int = {
    if (from < to) return from + Random.nextInt(Math.abs(to - from))
    from - Random.nextInt(Math.abs(to - from))
  }
}
