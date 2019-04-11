name := "ray-distracer"

version := "0.1"

scalaVersion := "2.12.8"

lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "11-R16",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.4",

  "com.typesafe" % "config" % "1.3.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.3.0-alpha4",
  "org.openjfx" % "javafx-base" % "11" classifier osName,
  "org.openjfx" % "javafx-controls" % "11" classifier osName,
  "org.openjfx" % "javafx-fxml" % "11" classifier osName,
  "org.openjfx" % "javafx-graphics" % "11" classifier osName,
  "org.openjfx" % "javafx-media" % "11" classifier osName,
  "org.openjfx" % "javafx-swing" % "11" classifier osName
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)