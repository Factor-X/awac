name := "awac"

version := "1.0-SNAPSHOT"

ebeanEnabled := false

libraryDependencies ++= Seq(
  "org.springframework" % "spring-orm" % "3.1.1.RELEASE",
  "org.springframework" % "spring-test" % "3.2.5.RELEASE",
  "org.springframework" % "spring-context-support" % "3.2.3.RELEASE",
  "org.hibernate" % "hibernate-entitymanager" % "4.2.6.Final",
  "org.hibernate" % "hibernate-ehcache" % "4.2.6.Final",
  javaCore,
  javaJpa,
  javaJdbc,
  cache,
  "com.google.inject" % "guice" % "3.0" % "test",
  "info.cukes" % "cucumber-guice" % "1.1.5" % "test",
  "info.cukes" % "cucumber-java" % "1.1.5" % "test",
  "info.cukes" % "cucumber-junit" % "1.1.5" % "test",
  "net.sf.ehcache" % "ehcache" % "2.7.2",
  "de.neuland" % "jade4j" % "0.3.17",
  "org.webjars" % "underscorejs" % "1.6.0-1",
  "org.webjars" % "jquery" % "1.11.0-1",
  "org.webjars" % "bootstrap" % "3.1.1" exclude("org.webjars", "jquery"),
  "org.webjars" % "angularjs" % "1.2.14" exclude("org.webjars", "jquery"),
  "org.webjars" % "requirejs" % "2.1.11-1",
  "org.webjars" % "angular-ui-bootstrap" % "0.11.0-2",
  "org.webjars" % "d3js" % "3.4.11",
  "org.webjars" % "angular-file-upload" % "1.6.5",
  "org.webjars" %% "webjars-play" % "2.2.1-2"
)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.18"

libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1101-jdbc41"

libraryDependencies += "com.google.code.maven-play-plugin.com.github.yeungda.jcoffeescript" % "jcoffeescript" % "1.0"

libraryDependencies += "de.neuland-bfi" % "jade4j" % "0.4.0"

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1"

libraryDependencies += "joda-time" % "joda-time" % "2.3"

libraryDependencies += "com.liferay" % "org.apache.commons.fileupload" % "1.2.2.LIFERAY-PATCHED-1"

libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.8.5"

libraryDependencies += "net.sourceforge.jexcelapi" % "jxl" % "2.6.12" excludeAll(
    ExclusionRule(organization = "com.sun.jdmk"),
    ExclusionRule(organization = "com.sun.jmx"),
    ExclusionRule(organization = "javax.jms")
 )

libraryDependencies += "org.jadira.usertype" % "usertype.core" % "3.2.0.GA" excludeAll(
    ExclusionRule(organization = "org.hibernate")
 )


play.Project.playJavaSettings

unmanagedResourceDirectories in Test <+= baseDirectory( _ / "test/features" )

javaOptions in Test += "-Dconfig.file=conf/test.conf"

// Scala Compiler Options
scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.7",
  "-encoding", "UTF-8",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code"
)
