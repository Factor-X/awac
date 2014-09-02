import sbt.ExclusionRule

name := "awac"

version := "1.0-SNAPSHOT"

ebeanEnabled := false

libraryDependencies ++= Seq(
  "org.springframework" % "spring-orm" % "3.1.1.RELEASE",
  "org.springframework" % "spring-test" % "3.2.5.RELEASE",
  "org.springframework" % "spring-context-support" % "3.2.3.RELEASE",
  "org.springframework.security" % "spring-security-core" % "3.2.3.RELEASE",
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
  "info.cukes" % "cucumber-scala_2.10" % "1.1.5" % "test",
  "net.sf.ehcache" % "ehcache" % "2.7.2"
)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.18"

libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1101-jdbc41"

libraryDependencies += "com.google.code.maven-play-plugin.com.github.yeungda.jcoffeescript" % "jcoffeescript" % "1.0"

libraryDependencies += "de.neuland-bfi" % "jade4j" % "0.4.0"

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1"

libraryDependencies += "joda-time" % "joda-time" % "2.3"

libraryDependencies += "com.liferay" % "org.apache.commons.fileupload" % "1.2.2.LIFERAY-PATCHED-1"

libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.8.5"

libraryDependencies += "org.apache.velocity" % "velocity" % "1.7"

libraryDependencies += "net.sourceforge.jexcelapi" % "jxl" % "2.6.12" excludeAll(
  ExclusionRule(organization = "com.sun.jdmk"),
  ExclusionRule(organization = "com.sun.jmx"),
  ExclusionRule(organization = "javax.jms")
  )

libraryDependencies += "org.jfree" % "jfreechart" % "1.0.19"

libraryDependencies += "org.jadira.usertype" % "usertype.core" % "3.2.0.GA" excludeAll (
  ExclusionRule(organization = "org.hibernate")
  )

libraryDependencies += "xalan" % "xalan" % "2.7.1"

libraryDependencies += "org.apache.xmlgraphics" % "batik-rasterizer" % "1.7" excludeAll (
    ExclusionRule(organization = "org.apache.xalan"),
    ExclusionRule(organization = "org.apache.xerces"),
    ExclusionRule(organization = "org.xhtmlrenderer")
  )


play.Project.playJavaSettings

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "test/features")

javaOptions in Test += "-Dconfig.file=conf/test.conf"

cucumberSettings

cucumberFeaturesLocation := "./test/features"

cucumberStepsBasePackage := "eu.factorx.awac.functional"

cucumberJunitReport := true

cucumberHtmlReport := true

cucumberHtmlReportDir := new File("../target/cucumber")

cucumberJsonReport := true