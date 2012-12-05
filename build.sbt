seq(githubRepoSettings: _*)

seq(conscriptSettings:_*)

organization := "com.github.hexx"

name := "Pomodoro Timer"

version := "0.0.1"

scalaVersion := "2.9.2"

scalacOptions += "-deprecation"

localRepo := Path.userHome / "github" / "maven"

githubRepo := "git@github.com:hexx/maven.git"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-swing" % "2.9.2"
)
