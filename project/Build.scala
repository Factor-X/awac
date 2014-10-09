import play.Project._
import sbt.Keys._
import sbt.{ExclusionRule, _}


object ApplicationBuild extends Build {

    lazy val buildVersion = "2.2.0"
    lazy val playVersion = "2.2.0"

    val appName = "awac"
    val appVersion = "1.0-SNAPSHOT"

    val appDependencies = Seq(
        // Add your project dependencies here,
        javaCore,
        javaJdbc,
        javaJpa,
        cache,
        "org.xhtmlrenderer" % "core-renderer" % "R8",
        "net.sf.jtidy" % "jtidy" % "r938",
        "org.apache.commons" % "commons-email" % "1.3.1",
        "commons-io" % "commons-io" % "2.3",
        "org.springframework" % "spring-context" % "3.2.3.RELEASE",
        "org.springframework" % "spring-context-support" % "3.2.3.RELEASE",
        "org.springframework" % "spring-expression" % "3.2.3.RELEASE",
        "org.springframework" % "spring-orm" % "3.1.1.RELEASE",
        "org.springframework" % "spring-test" % "3.2.5.RELEASE",
        "org.springframework.security" % "spring-security-core" % "3.2.3.RELEASE",
        "org.hibernate" % "hibernate-entitymanager" % "4.2.6.Final",
        "org.hibernate" % "hibernate-ehcache" % "4.2.6.Final",
        "com.googlecode.ehcache-spring-annotations" % "ehcache-spring-annotations" % "1.2.0",
        "net.sf.ehcache" % "ehcache" % "2.7.2",
        "com.google.inject" % "guice" % "3.0" % "test",
        "info.cukes" % "cucumber-guice" % "1.1.5" % "test",
        "info.cukes" % "cucumber-java" % "1.1.5" % "test",
        "info.cukes" % "cucumber-junit" % "1.1.5" % "test",
        "info.cukes" % "cucumber-scala_2.10" % "1.1.5" % "test",
        "com.google.guava" % "guava" % "14.0",
        "de.neuland-bfi" % "jade4j" % "0.4.0",
        "org.apache.commons" % "commons-lang3" % "3.1",
        "joda-time" % "joda-time" % "2.3",
        "com.liferay" % "org.apache.commons.fileupload" % "1.2.2.LIFERAY-PATCHED-1",
        "com.amazonaws" % "aws-java-sdk" % "1.8.5",
        "org.apache.velocity" % "velocity" % "1.7",
        "com.google.code.maven-play-plugin.com.github.yeungda.jcoffeescript" % "jcoffeescript" % "1.0",
        "org.postgresql" % "postgresql" % "9.3-1101-jdbc41",
        "org.apache.commons" % "commons-email" % "1.3.1",
        "com.typesafe" %% "play-plugins-util" % buildVersion,
        "xalan" % "xalan" % "2.7.1",
        "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.2",
        "net.sourceforge.jexcelapi" % "jxl" % "2.6.12" excludeAll(
            ExclusionRule(organization = "com.sun.jdmk"),
            ExclusionRule(organization = "com.sun.jmx"),
            ExclusionRule(organization = "javax.jms")),
        "org.apache.xmlgraphics" % "batik-rasterizer" % "1.7" excludeAll(
            ExclusionRule(organization = "org.apache.xalan"),
            ExclusionRule(organization = "org.apache.xerces"),
            ExclusionRule(organization = "org.xhtmlrenderer")
            ),
        "org.jfree" % "jfreechart" % "1.0.19",
        "org.jadira.usertype" % "usertype.core" % "3.2.0.GA" excludeAll ExclusionRule(organization = "org.hibernate"),
        "xalan" % "xalan" % "2.7.1",
        "xalan" % "xalan" % "2.7.1" % "test",
        "org.apache.xmlgraphics" % "batik-rasterizer" % "1.7" excludeAll(
            ExclusionRule(organization = "org.apache.xalan"),
            ExclusionRule(organization = "org.apache.xerces"),
            ExclusionRule(organization = "org.xhtmlrenderer")
            ),
        "org.apache.xmlgraphics" % "batik-codec" % "1.7"
    )


    lazy val angularCompileTask = TaskKey[Unit]("angular-compile", "Compile angular app")
    val angularCompileSettings = angularCompileTask := {
        new AngularCompileTask().execute()
    }

    val main = sbt.Project(id = appName, base = file("."))
        .settings(
            version := appVersion,
            libraryDependencies ++= appDependencies,
            resolvers += "JBoss repository" at "https://repository.jboss.org/nexus/content/repositories/",
            resolvers += "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots",
            doc in Compile <<= target.map(_ / "none")
        )
        .settings(
            angularCompileSettings, resources in Compile <<= (resources in Compile).dependsOn(angularCompileTask)
        )
    // Add your own project settings here

    javaOptions ++= Seq("-Xmx512M", "-Xmx2048M", "-XX:MaxPermSize=2048M");

}
