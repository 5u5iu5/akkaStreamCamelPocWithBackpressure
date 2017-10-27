package poc.akkastream.publisher

import poc.akkastream.kafka.KafkaProducer

object PublisherKafkaMain {
  def apply: PublisherKafkaMain = new PublisherKafkaMain()
}

class PublisherKafkaMain extends PublisherBase {

  override def basicPublish(host: String, port: Int, messageToSend: String, numMaxToSend: Int)
                           (exchangeName: String, queueStr: String, routingKey: String, topic: String) = {

    val kafkaProducer: KafkaProducer = KafkaProducer(messageToSend, numMaxToSend, topic, host, port)
    kafkaProducer.produce

  }
}
