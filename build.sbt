name := "awac"

version := "1.0-SNAPSHOT"

ebeanEnabled := false

libraryDependencies ++= Seq(
  "org.hibernate" % "hibernate-entitymanager" % "4.2.6.Final",
  javaCore,
  javaJpa
)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.18"

libraryDependencies += "com.google.code.maven-play-plugin.com.github.yeungda.jcoffeescript" % "jcoffeescript" % "1.0"

libraryDependencies += "de.neuland-bfi" % "jade4j" % "0.4.0"

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1"

libraryDependencies += "joda-time" % "joda-time" % "2.3"


play.Project.playJavaSettings
