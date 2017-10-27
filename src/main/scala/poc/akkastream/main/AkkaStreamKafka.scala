package poc.akkastream.main

import akka.actor.{ActorRef, Props}
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}
import akka.stream.{ActorMaterializer, ClosedShape, OverflowStrategy}
import poc.akkastream.camel.CamelSubscriber
import poc.akkastream.kafka.KafkaConsumer
import poc.akkastream.main.LaunchStream.system
import poc.akkastream.protocol.{ACK, INITMESSAGE, ONCOMPLETE}
import poc.akkastream.publisher.{PublisherBase, PublisherKafkaMain}

object AkkaStreamKafka {
  def apply: AkkaStreamKafka = new AkkaStreamKafka()
}

class AkkaStreamKafka {
  implicit val materializer = ActorMaterializer()

  def graphNormalKafkaScenario(source: Source[String, ActorRef], sink: Sink[String, ActorRef], buffer: Int) =
    RunnableGraph.fromGraph(GraphDSL.create(sink) { implicit b =>
      sink =>
        import GraphDSL.Implicits._
        val in = source.buffer(buffer, OverflowStrategy.backpressure)
        val out = sink


        in ~> f1 ~> f2 ~> out
        ClosedShape
    })


  def goStream = sourceForKafka via f1 via f2 to sinkForKafka(Props[CamelSubscriber]) run()

  def f1 = Flow[String].map(_.toString)

  def f2 = Flow[String].map(_ + " Flow2")


  def publishInKafka = {
    val publish: PublisherBase = PublisherKafkaMain.apply
    publish.basicPublish("localhost", 9092, "hola vengo de kafka", 1000)("", "", "", "topic1")
  }

  def consumerKafkaActor(actorRef: ActorRef) = system.actorOf(Props(new KafkaConsumer(actorRef)))

  def sourceForKafka: Source[String, ActorRef] = {
    Source.actorRef(50000, OverflowStrategy.fail)
  }

  def sinkForKafka(consumer: Props) = {
    Sink.actorRefWithAck[String](system.actorOf(consumer), INITMESSAGE, ACK, ONCOMPLETE, th => th.getMessage)
  }
}
