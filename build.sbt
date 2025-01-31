name := """play-java-hello-world-tutorial"""
organization := "com.example"

version := "pre-release-v1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
  //.enablePlugins(PlayNettyServer).disablePlugins(PlayPekkoHttpServer) // uncomment to use the Netty backend

crossScalaVersions := Seq("2.13.15", "3.3.3")

scalaVersion := crossScalaVersions.value.head

libraryDependencies += guice
