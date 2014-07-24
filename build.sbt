name := "awac"

version := "1.0-SNAPSHOT"

ebeanEnabled := false

libraryDependencies ++= Seq(
  "org.hibernate" % "hibernate-entitymanager" % "4.2.6.Final",
  javaCore,
  javaJpa
)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.18"

libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1101-jdbc41"

libraryDependencies += "com.google.code.maven-play-plugin.com.github.yeungda.jcoffeescript" % "jcoffeescript" % "1.0"

libraryDependencies += "de.neuland-bfi" % "jade4j" % "0.4.0"

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1"

libraryDependencies += "joda-time" % "joda-time" % "2.3"

libraryDependencies += "com.liferay" % "org.apache.commons.fileupload" % "1.2.2.LIFERAY-PATCHED-1"

libraryDependencies += "net.sourceforge.jexcelapi" % "jxl" % "2.6.12" excludeAll(
    ExclusionRule(organization = "com.sun.jdmk"),
    ExclusionRule(organization = "com.sun.jmx"),
    ExclusionRule(organization = "javax.jms")
 )

libraryDependencies += "org.jadira.usertype" % "usertype.core" % "3.2.0.GA" excludeAll(
    ExclusionRule(organization = "org.hibernate")
 )

play.Project.playJavaSettings
