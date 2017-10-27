package poc.akkastream.kafka

import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

case class KafkaProducer(val message: String, val num: Int,
                         val topic: String, val host: String, val port: Int) extends KafkaConn {

  val producerSettings = ProducerSettings(system, new ByteArraySerializer, new StringSerializer)
    .withBootstrapServers(s"$host:$port")

  def produce = {
    val list = List.range(1, num).map(l => "-" + l)
    val done = Source(list)
      .map(_.concat(message))
      .map { elem =>
        new ProducerRecord[Array[Byte], String](topic, elem)
      }
      .runWith(Producer.plainSink(producerSettings))

    done.onComplete {
      case Success(value) => println(s"*** PUBLISHING KAFKA $value... ***")
      case Failure(e) => e.printStackTrace
    }
  }

}
