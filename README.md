# Akka Streams with AyncMessage from Kafka and Rabbitmq

### Up Rabbit instance from Docker

```bash
 docker pull rabbitmq:latest
 ```

 ```bash
docker run -d --hostname my-rabbit --name some-rabbit -p 8080:15672 -p 8081:5672 rabbitmq
```

to enable management console from `localhost:8080`

 ```bash
 docker exec some-rabbit rabbitmq-plugins enable rabbitmq_management
 ```

- Create exchange with name __consumerExchange__ with type __direct__
- Binding with exchange a new queue with name __cola1__

### Up Kafka instance from Docker

Go to docker dir where is docker-compose-yml and run docker compose command

```bash
 docker-compose up
 ```

When docker container is running you need create a cluser and topic in kafka

Yoy can connect to management console on: `localhost/9000`

1. Create cluster with the name of your choice
2. Put cluster zookeeper host. **kafkaserver**
3. Select Kafka Version 0.10.1.0
4. Create some topic

In case of Windows SO, we need one more thing.

In order to connect to Kafka in Docker from the Windows host, we need to map Kafka container hostname to the localhost, by editing hosts file:

```
C:\Windows\System32\drivers\etc\hosts
```

Append this line:
__127.0.0.1 kafkaserver__

## Testing Apache Kafka on Windows
Download Apache Kafka Distribution **https://kafka.apache.org/downloads**
and choose the 0.10.1.0 distribution in our case

### Kafka Consumer script

```bash
.\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic topic1
```

### Kafka Producer script

```bash
.\kafka-console-producer.bat --broker-list localhost:9092 --topic topic1
c
```