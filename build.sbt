name := """hs-deck-hub"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean, SbtWeb)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "bootstrap" % "3.1.1-2" exclude("org.webjars", "jquery"),
  "org.webjars" % "jquery" % "2.2.0",
  "org.webjars" % "angularjs" % "1.5.0",
  "org.webjars" % "angular-chart.js" % "0.8.8",
  "org.webjars" % "angular-ui-bootstrap" % "1.2.1",
  "org.webjars" % "requirejs" % "2.1.22",
  "com.googlecode.json-simple" % "json-simple" % "1.1",
  "org.mindrot" % "jbcrypt" % "0.3m"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

fork in run := false
