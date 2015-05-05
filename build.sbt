
resolvers += "Sonatype Release" at "http://oss.sonatype.org/content/repositories/releases"
resolvers += "JBoss" at "http://repository.jboss.org/nexus/content/groups/public/"
resolvers += "Akka" at "http://repo.akka.io/repository/"
resolvers += "Sonatype Public" at "http://oss.sonatype.org/content/groups/public/"
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
resolvers += "Typesafe maven" at "http://repo.typesafe.com/typesafe/maven-releases/"

// Deps
val scalatest = "org.scalatest" % "scalatest_2.11" % "2.2.4" % Test
val snakeyaml = "org.yaml" % "snakeyaml" % "1.15"





val commonSettings = Seq(
  organization := "wgrm",
  version := "0.1",
  scalaVersion := "2.11.6",
  fork in test := true,
  libraryDependencies += scalatest
)

lazy val root = Project("root", file("."))
  .settings(commonSettings: _*)
  .dependsOn(core)

lazy val core = Project("core", file("core"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(snakeyaml))

lazy val front = Project("front", file("front"))
  .settings(commonSettings: _*)
  .enablePlugins(PlayScala)
  .dependsOn(core)
