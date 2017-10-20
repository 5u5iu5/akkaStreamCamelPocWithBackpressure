libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.6",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.6" % Test,
  "com.typesafe.akka" %% "akka-http" % "10.0.10",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.10" % Test,
  "com.typesafe.akka" %% "akka-stream" % "2.5.6",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.6" % Test,
  "com.typesafe.akka" %% "akka-distributed-data" % "2.5.6",
  "com.typesafe.akka" %% "akka-persistence" % "2.5.6",
  "com.typesafe.akka" % "akka-camel_2.12" % "2.5.6",
  "org.apache.camel" % "camel-rabbitmq" % "2.17.7"
)
