publishTo := Some(Resolver.file("packages", file("/var/www/packages.meraki.com/maven")))

publishMavenStyle := false

publishArtifact in Test := false
