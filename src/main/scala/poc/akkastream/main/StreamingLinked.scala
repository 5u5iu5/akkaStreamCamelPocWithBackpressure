package poc.akkastream.main

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.{Concat, Flow, GraphDSL, RunnableGraph, Sink, Source}

object StreamingLinked extends App {
  implicit val system = ActorSystem("some-system")
  implicit val materializer = ActorMaterializer()

  val source1 = Source(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
  val source2 = Source(List("a", "b", "c", "d", "e"))
  val sink = Sink.foreach(println)
  val tranformFlow = Flow[String].map(n => s"Number $n")

  source1.concat(source2).map(f => s"This is $f").map(n => s"And now is $n").runForeach(println)


  //  val g: RunnableGraph[_] = RunnableGraph.fromGraph(GraphDSL.create() {
  //    implicit builder =>
  //
  //      val A: Outlet[String] = builder.add(Source.fromIterator(() => source2)).out
  //      val B: FlowShape[String, String] = builder.add(Zip)
  //      val C: Inlet[Any] = builder.add(Sink.ignore).in
  //
  //      import GraphDSL.Implicits._ // allows us to build our graph using ~> combinators
  //
  //      // Graph
  //      A ~> B ~> C
  //
  //      ClosedShape // defines this as a "closed" graph, not exposing any inlets or outlets
  //  })

  //  val sourceList = List( Source single "ONE", "TWO", "THREE", "FOUR")
  //
  //
  //  val oneSourceToRuleThemAll : Source[String, _] =
  //    (sourceList./:(source1))(_ + _)


}
