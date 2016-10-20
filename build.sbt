organization := "com.example"
scalaVersion := "2.11.8"

reformatOnCompileSettings
scalafmtConfig in ThisBuild := Some(file(".scalafmt.conf"))

libraryDependencies ++= Seq(
  "com.squareup.okhttp3" % "okhttp" % "3.4.1",
  "com.github.jknack" % "handlebars" % "4.0.6",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
