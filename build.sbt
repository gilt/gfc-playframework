name := "gfc-playframework"

organization := "com.gilt"

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

releaseCrossBuild := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses := Seq("Apache-style" -> url("https://raw.githubusercontent.com/gilt/gfc-playframework/master/LICENSE"))

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

