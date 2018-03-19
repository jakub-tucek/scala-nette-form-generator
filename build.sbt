import Dependencies._

name in ThisBuild := """scala-nette-form-generator"""
organization in ThisBuild := "cvut.fit"

version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.12.4"

lazy val web = (project in file("web"))
  .settings(
    scalaJSUseMainModuleInitializer := true,
    emitSourceMaps := false,
    webpackConfigFile in fullOptJS := Some(baseDirectory.value / "prod.webpack.config.js"),
    libraryDependencies ++= Seq(
      "com.github.japgolly.scalajs-react" %%% "core" % scalajsReact,
      "com.github.japgolly.scalajs-react" %%% "extra" % scalajsReact,
      "org.scala-js" %%% "scalajs-dom" % scalajsDom,
      "io.github.cquiroz" %%% "scala-java-time" % scalaJavaTime
    ),
    npmDependencies in Compile ++= Seq(
      "react" -> react,
      "react-dom" -> react
    )
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin, ScalaJSWeb)


lazy val server = (project in file("server"))
  .settings(
    name := (name in ThisBuild).value,
    WebKeys.exportedMappings in Assets := Seq(),
    libraryDependencies ++= Seq(
      guice,
      "com.github.t3hnar" %% "scala-bcrypt" % bcrypt,
      "org.scalactic" %% "scalactic" % scalactic,
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    ),
    scalaJSProjects := Seq(web),
    pipelineStages in Assets := Seq(scalaJSPipeline, digest, gzip),
    routesGenerator := InjectedRoutesGenerator
  )
  .enablePlugins(PlayScala, WebScalaJSBundlerPlugin, JavaServerAppPackaging)

