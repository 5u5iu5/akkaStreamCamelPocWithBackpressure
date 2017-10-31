package poc.akkastream.main

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import poc.akkastream.suscriber.{KafkaSubscriber}

object LaunchStream extends App {

  implicit val system = ActorSystem("some-system")
  implicit val materializer = ActorMaterializer()

  //callCamelRabbitProcess
  callKafkaProcess

  def callCamelRabbitProcess = {

    val akkaStream: AkkaStreamCamel = AkkaStreamCamel.apply
    akkaStream.publishInRabbit

    //Scenario with 1000 buffered
    val camelConsumerActor = akkaStream.consumerCamelActor
    akkaStream.graphNormalCamelScenario(akkaStream.sourceForCamel(camelConsumerActor), akkaStream.sinkForCamel, 1000).run()

  }

  def callKafkaProcess = {

    val kafkaStream: AkkaStreamKafka = AkkaStreamKafka.apply
    //kafkaStream.publishInKafka

    //Scenario with 1000 buffered
    //val streamActor = kafkaStream.graphNormalKafkaScenario(kafkaStream.sourceForKafka, kafkaStream.sinkForKafka(), 1000)
    //val streamActor = kafkaStream.goStream
    val kafkaConsumerActor = kafkaStream.consumerKafkaActor(system.actorOf(Props[KafkaSubscriber]))
    kafkaConsumerActor ! "WAKE UP KAFKA CONSUMER"
  }


}
