name := "wiki_integration"

version := "0.1"

scalaVersion := "2.13.6"

lazy val akkaVersion = "2.6.10"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scala-lang.modules" % "scala-xml_sjs0.6_2.12" % "1.2.0",
  "com.typesafe.akka" %% "akka-http-xml" % "10.2.7",
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % "10.2.6",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)
