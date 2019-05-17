name := "myScalaTest"

version := "1.0"

scalaVersion := "2.12.6"

//fork in run := true

//javaOptions += "-Dscala.concurrent.context.maxThreads=1"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.3"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.13"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.13"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.1"



    