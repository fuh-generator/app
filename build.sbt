val circeVersion  = "0.14.1"
val http4sVersion = "0.23.18"

ThisBuild / scalaVersion := "2.13.10"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / organization := "lgbt.princess"

lazy val sharedSettings = Seq(
  scalacOptions ++= Seq("-Xlint:-byname-implicit,_"),
  libraryDependencies ++= Seq(
    "org.typelevel" %%% "cats-core"            % "2.9.0",
    "org.typelevel" %%% "case-insensitive"     % "1.3.0",
    "io.circe"      %%% "circe-core"           % circeVersion,
    "io.circe"      %%% "circe-generic"        % circeVersion,
    "io.circe"      %%% "circe-generic-extras" % circeVersion,
    "org.http4s"     %% "http4s-circe"         % http4sVersion,
    "org.http4s"    %%% "http4s-core"          % http4sVersion,
  ),
)

lazy val core =
  crossProject(JSPlatform, JVMPlatform)
    .in(file("core"))
    .settings(sharedSettings)
    .settings(
      name           := "fuh-core",
      publish / skip := true,
    )

lazy val site =
  crossProject(JSPlatform)
    .in(file("site"))
    .dependsOn(core)
    .settings(sharedSettings)
    .settings(
      name := "fuh-site",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "2.1.0",
        "com.lihaoyi"  %%% "scalatags"   % "0.11.1",
      ),
      publish / skip := true,
    )
    .jsSettings(
      scalaJSUseMainModuleInitializer := true,
    )
    .js

lazy val fuh =
  project
    .in(file("."))
    .aggregate(
      core.js,
      core.jvm,
      site,
    )
    .settings(
      publish / skip := true,
    )

Global / onChangedBuildSource := ReloadOnSourceChanges
