package poc.akkastream.publisher

trait PublisherBase {
  def basicPublish(host: String, port: Int, messageToSend: String, numMaxToSend: Int)(
                   exchangeName: String, queueStr: String, routingKey: String, topic: String)
}
