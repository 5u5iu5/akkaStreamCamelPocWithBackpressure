addSbtPlugin("com.lightbend.cinnamon" % "sbt-cinnamon" % "2.4.3")

credentials += Credentials(Path.userHome / ".lightbend" / "commercial.credentials")

resolvers += Resolver.url("lightbend-commercial",
  url("https://repo.lightbend.com/commercial-releases"))(Resolver.ivyStylePatterns)

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
  "org.apache.camel" % "camel-rabbitmq" % "2.17.7",
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.17",
)
