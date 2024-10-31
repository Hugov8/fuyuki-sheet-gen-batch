
scalaVersion := "2.13.14"

name := "fuyuki-gen-sheet-batch"
organization := "com.hugov.fuyuki"
version := "1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"

// Json library
libraryDependencies += "org.playframework" %% "play-json" % "3.0.4"

//requests dependency
libraryDependencies += "com.lihaoyi" %% "requests" % "0.8.0"
libraryDependencies += "io.lemonlabs" %% "scala-uri" % "4.0.3"

//Google sheet dependencies
libraryDependencies += "com.google.api-client" % "google-api-client" % "2.0.0"
libraryDependencies += "com.google.oauth-client" % "google-oauth-client-jetty" % "1.34.1"
libraryDependencies += "com.google.http-client" % "google-http-client-jackson2" % "1.43.0"
libraryDependencies += "com.google.apis" % "google-api-services-sheets" % "v4-rev20220927-2.0.0"
libraryDependencies += "com.google.auth" % "google-auth-library-credentials" % "1.11.0"
libraryDependencies += "com.google.auth" % "google-auth-library-oauth2-http" % "1.11.0"

//Google drive dependencies
libraryDependencies += "com.google.apis" % "google-api-services-drive" % "v3-rev20220815-2.0.0"

//Spring dependency
libraryDependencies += "org.springframework.boot" % "spring-boot-starter-batch" % "3.3.5" // Starter pour Spring Batch
libraryDependencies += "org.springframework.boot" % "spring-boot-starter-jdbc" % "3.3.5" // Starter JDBC pour gérer les connexions DB
libraryDependencies += "org.springframework.boot" % "spring-boot-starter" % "3.3.5"
libraryDependencies += "org.springframework" % "spring-jdbc" % "6.1.14"
libraryDependencies += "org.springframework" % "spring-tx" % "6.1.14"
libraryDependencies +=  "org.springframework" % "spring-context" % "6.1.14"
libraryDependencies += "org.springframework.batch" % "spring-batch-core" % "5.1.2"

//BDD dependency
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.28.0"


