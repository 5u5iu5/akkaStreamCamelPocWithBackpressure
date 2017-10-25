package poc.akkastream.kafka

import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class KafkaProducer extends KafkaConn {

  def produce = {
    val list = List.range(1, 500).map(l => "Kafka - " + l)
    val done = Source(list)
      .map(_.concat(". fuck yeah!"))
      .map { elem =>
        new ProducerRecord[Array[Byte], String]("topic1", elem)
      }
      .runWith(Producer.plainSink(producerSettings))

    done.onComplete {
      case Success(value) => println(s"Got the callback, meaning = $value")
      case Failure(e) => e.printStackTrace
    }
  }

}
