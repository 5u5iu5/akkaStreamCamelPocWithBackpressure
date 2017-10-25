# Akka Streams with AyncMessage from Kafka and Rabbitmq

### Up Rabbit instance from Docker

* ```docker pull rabbitmq:latest```

* ```docker run -d --hostname my-rabbit --name some-rabbit -p 8080:15672 -p 8081:5671 rabbitmq```

to enable management console from `localhost:8080`

* ```docker exec some-rabbit rabbitmq-plugins enable rabbitmq_management```

- Create exchange with name __consumerExchange__ with type __direct__
- Binding with exchange a new queue with name __cola1__

### Up Kafka instance from Docker

```docker pull spotify/kafka```

```docker run -p 2181:2181 -p 9092:9092 spotify/kafka```
