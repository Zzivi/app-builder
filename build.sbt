name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.eclipse.jgit" % "org.eclipse.jgit" % "4.4.0.201606070830-r",
  // https://adrianhurt.github.io/play-bootstrap/
  "com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3"
)


fork in run := true