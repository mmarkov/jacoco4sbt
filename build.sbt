sbtPlugin := true

name := "jacoco4sbt"

organization := "com.quantcast.sbt"

versionMajor := "2.1.7-qc"

versionFormat := "M.B.d"

resolvers += "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"

scalaVersion := "2.10.5"

val jacocoCore = Artifact("org.jacoco.core", "jar", "jar")

val jacocoReport = Artifact("org.jacoco.report", "jar", "jar")

val jacocoVersion = "0.7.5.201505241946"

libraryDependencies ++= Seq(
  "org.jacoco"  %  "org.jacoco.core"   % jacocoVersion artifacts(jacocoCore),
  "org.jacoco"  %  "org.jacoco.report" % jacocoVersion artifacts(jacocoReport),
  "org.specs2"  %% "specs2"            % "2.3.13" % Test,
  "org.mockito" %  "mockito-all"       % "1.9.5"  % Test,
  "org.pegdown" %  "pegdown"           % "1.2.1"  % Test
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-language:_")

enablePlugins(BuildInfoPlugin)
enablePlugins(DateVersionPlugin)

publishTo := Some(Resolver.file("file", new File("./target/output")))

buildInfoKeys := Seq[BuildInfoKey](
  resourceDirectory in Test,
  "jacocoVersion" -> jacocoVersion,
  sbtVersion,
  scalaVersion
)

buildInfoPackage := "com.quantcast.sbt.jacoco4sbt.build"

test in Test <<= test in Test dependsOn publishLocal

parallelExecution in Test := false


