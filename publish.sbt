publishTo := Some(Resolver.file("packages", file("/var/www/packages.meraki.com/maven")))

publishArtifact in Test := false
