import sbt._
import Keys._

// https://github.com/typesafehub/webwords/blob/master/project/Build.scala
// https://github.com/sbt/sbt-start-script


object BuildSettings {
  import Dependencies._
  import Resolvers._

  val buildOrganization = "wgrm"
  val buildVersion = "0.1"
  val buildScalaVersion = "2.11.6"

  val globalSettings = Seq(
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    scalacOptions += "",
    fork in test := true,
    resolvers := Seq(jbossRepo, akkaRepo, sonatypeRepo))
  //val mainClass = Some("wgrm.resolver.Main")

  val projectSettings = Defaults.defaultSettings ++ globalSettings
}

object Resolvers {
  val sonatypeRepo = "Sonatype Release" at "http://oss.sonatype.org/content/repositories/releases"
  val jbossRepo = "JBoss" at "http://repository.jboss.org/nexus/content/groups/public/"
  val akkaRepo = "Akka" at "http://repo.akka.io/repository/"
}


object Dependencies {
}

object ResolverBuild extends Build {
  import BuildSettings._
  import Dependencies._
  import Resolvers._

  override lazy val settings = super.settings ++ globalSettings

  lazy val root = Project("resolver-root",
    file("."),
    settings = projectSettings) aggregate(core)

  lazy val core = Project("resolver-core",
    file("core"),
    settings = projectSettings)
}
