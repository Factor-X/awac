import sbt._

// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")

libraryDependencies += "com.google.gdata" % "core" % "1.47.1"

resolvers += "JMParsons Releases" at "http://jmparsons.github.io/releases/"

addSbtPlugin("com.jmparsons" % "play-lessc" % "0.0.8")
