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

<<<<<<< HEAD
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1"
    
=======

>>>>>>> 4f171f4c271b5cf2aada47ccdfeda7ba7be081da
play.Project.playJavaSettings
