name := "gfc-playframework"

organization := "com.gilt"

version := "git describe --tags --always --dirty".!!.trim.replaceFirst("^v", "")

scalaVersion := "2.11.7"

crossScalaVersions := Seq(scalaVersion.value, "2.10.5")

lazy val PlayVersion = "2.5.4"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % PlayVersion % Provided,
  "com.typesafe.play" %% "play-json" % PlayVersion % Provided,
  "org.scalatest" %% "scalatest" % "2.2.5" % Test,
  "org.scalacheck" %% "scalacheck" % "1.12.4" % Test,
  "org.mockito" % "mockito-core" % "1.10.19" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

publishMavenStyle := true

bintrayOrganization := Some("giltgroupe")

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

homepage := Some(url("https://github.com/gilt/gfc-playframework"))

pomExtra := <scm>
    <url>https://github.com/gilt/gfc-playframework.git</url>
    <connection>scm:git:git@github.com:gilt/gfc-playframework.git</connection>
  </scm>
  <developers>
    <developer>
      <id>grhodes</id>
      <name>Graham Rhodes</name>
      <url>https://github.com/grahamar</url>
    </developer>
  </developers>

