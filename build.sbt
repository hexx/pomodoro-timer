seq(conscriptSettings :_*)

organization := "com.github.hexx"

name := "Pomodoro Timer"

version := "0.0.1"

scalaVersion := "2.9.2"

scalacOptions += "-deprecation"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-swing" % "2.9.2"
)
