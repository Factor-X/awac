import sbt._

// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Templemore Repository" at "http://templemore.co.uk/repo"

// Use the Play sbt plugin for Play projects

// Add PMD and Checkstyle libraries.
libraryDependencies ++= Seq(
  "com.puppycrawl.tools" % "checkstyle" % "5.5",
  "net.sourceforge.pmd" % "pmd" % "5.0.0",
  "com.google.gdata" % "core" % "1.47.1"
)

libraryDependencies += "com.google.code.maven-play-plugin.com.github.yeungda.jcoffeescript" % "jcoffeescript" % "1.0"

libraryDependencies += "de.neuland-bfi" % "jade4j" % "0.4.0"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.2"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")

// Add Jacoco plugin for code coverage
addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.6")

// Add Findbugs plugin library
addSbtPlugin("de.johoop" % "findbugs4sbt" % "1.2.2")

// add cucumber plugin
addSbtPlugin("templemore" % "sbt-cucumber-plugin" % "0.8.0")

