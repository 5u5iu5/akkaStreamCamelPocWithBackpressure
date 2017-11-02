
lazy val akkastreamcamelpocwithbackpressure = project in file(".") enablePlugins (Cinnamon)

// Add the Monitoring Agent for run and test
cinnamon in run := true
cinnamon in test := false

// Set the Monitoring Agent log level
cinnamonLogLevel := "INFO"

version := "1.0"

scalaVersion := "2.12.1"

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

// Cinnamon
libraryDependencies += Cinnamon.library.cinnamonCHMetricsJvmMetrics
libraryDependencies += Cinnamon.library.cinnamonCHMetricsStatsDReporter
libraryDependencies += Cinnamon.library.cinnamonCHMetrics
libraryDependencies += Cinnamon.library.cinnamonCHMetricsElasticsearchReporter
libraryDependencies += Cinnamon.library.cinnamonOpenTracingZipkin
libraryDependencies += Cinnamon.library.cinnamonOpenTracingZipkinKafka

// Use Akka instrumentation
libraryDependencies += Cinnamon.library.cinnamonAkka
