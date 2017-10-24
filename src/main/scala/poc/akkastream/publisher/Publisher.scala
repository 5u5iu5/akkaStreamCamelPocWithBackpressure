package poc.akkastream.publisher

import com.rabbitmq.client.{Channel, MessageProperties}

import scala.util.Random

object Publisher{
  def apply: Publisher = new Publisher()
}

protected class Publisher extends PublisherBase {

  def basicPublish(host: String, port: Int, messageToSend: String, exchangeName: String,
                   queueStr: String, routingKey: String, numMaxToSend: Int) = {

    def config = {
      import com.rabbitmq.client.ConnectionFactory

      val factory = new ConnectionFactory
      factory.setHost(host)
      factory.setPort(port)
      val connection = factory.newConnection
      val channel = connection.createChannel

      val message = messageToSend + ":" + Random.nextInt()
      channel.exchangeDeclare(exchangeName, "direct", true)
      channel.queueDeclare(queueStr, true, false, false, null)
      channel.queueBind(queueStr, exchangeName, routingKey)
      (channel, message)
    }

    val configResult: (Channel, String) = config
    val channel: Channel = configResult._1
    var message: String = configResult._2


    def sendNMessages(numMax: Int) = {
      for (i <- 0 to numMax) {
        message = message + i
        channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes())
      }
    }

    sendNMessages(numMaxToSend)
  }
}
