val commonSettings = Seq(
  organization := "wgrm",
  version := "0.1",
  scalaVersion := "2.11.6"
)
lazy val front = Project("resolver-front", file("front"))
  .settings(BuildSettings.projectSettings: _*)
  .enablePlugins(PlayScala)
  .dependsOn(core)
