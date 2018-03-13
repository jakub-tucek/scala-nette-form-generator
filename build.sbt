name := """scala-nette-form-generator"""
organization := "cvut.fit"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "cvut.fit.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "cvut.fit.binders._"
