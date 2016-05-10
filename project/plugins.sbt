resolvers += "Artifact SBT Plugins" at "http://webbuild01.sfoffice.qc/artifactory/sbtplugins-releases-local"

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.5.0")

addSbtPlugin("com.quantcast.sbt" % "sbt-date-version" % "0+")