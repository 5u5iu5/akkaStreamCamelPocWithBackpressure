package poc.akkastream

import akka.actor.ActorRef
import akka.stream.actor.ActorPublisher
import akka.stream.actor.ActorPublisherMessage.Cancel

class AsyncMessageConsumer(camelActor:ActorRef) extends ActorPublisher[String] {

  override def receive = {
    case msg:String => camelActor ! msg
    case Cancel =>
      context.stop(self)

  }
}
