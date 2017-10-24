package poc.akkastream.publisher

trait PublisherBase {
  def basicPublish(host: String, port: Int, messageToSend: String)(
                   exchangeName: String, queueStr: String, routingKey: String, numMaxToSend: Int)
}
