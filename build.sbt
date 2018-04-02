import Dependencies._

name in ThisBuild := """scala-nette-form-generator"""
organization in ThisBuild := "cvut.fit"

version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.12.5"

lazy val root = (project in file("."))
  .aggregate(server)
  .settings(
    run := {
      (run in server in Compile).evaluated
    }
  )

lazy val web = (project in file("web"))
  .settings(
    scalaJSUseMainModuleInitializer := true,
    emitSourceMaps := false,
    webpackConfigFile in fullOptJS := Some(baseDirectory.value / "prod.webpack.config.js"),
    libraryDependencies ++= Seq(
      "com.github.japgolly.scalajs-react" %%% "core" % scalajsReact,
      "com.github.japgolly.scalajs-react" %%% "extra" % scalajsReact,
      "org.scala-js" %%% "scalajs-dom" % scalajsDom,
      "io.github.cquiroz" %%% "scala-java-time" % scalaJavaTime,
    ),
    npmDependencies in Compile ++= Seq(
      "react" -> react,
      "react-dom" -> react
    )
  )
  .dependsOn(sharedJS)
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin, ScalaJSWeb)



lazy val server = (project in file("server"))
  .settings(
    name := (name in ThisBuild).value,
    WebKeys.exportedMappings in Assets := Seq(),
    libraryDependencies ++= Seq(
      guice,
      "com.github.t3hnar" %% "scala-bcrypt" % bcrypt,
      "org.scalatestplus.play" %% "scalatestplus-play" % scalatestplus % Test,
      "org.scalatest" %% "scalatest" % scalatest % Test,
      "org.scalactic" %% "scalactic" % scalactic % Test,
      "org.mockito" % "mockito-all" % mockito % Test,
    ),
    scalaJSProjects := Seq(web),
    pipelineStages in Assets := Seq(scalaJSPipeline, digest, gzip),
    routesGenerator := InjectedRoutesGenerator
  )
  .dependsOn(sharedJVM)
  .enablePlugins(PlayScala, WebScalaJSBundlerPlugin, JavaServerAppPackaging)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared"))
  .settings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "autowire" % autowire,
      "io.circe" %%% "circe-core" % circe,
      "io.circe" %%% "circe-generic" % circe,
      "io.circe" %%% "circe-parser" % circe,
      "io.circe" %% "circe-java8" % circe
    ),
    addCompilerPlugin("org.scalamacros" % "paradise" % paradise cross CrossVersion.full)
  )
  .jsConfigure(_.enablePlugins(ScalaJSPlugin))
  .jsSettings()

lazy val sharedJVM = shared.jvm

lazy val sharedJS = shared.js

