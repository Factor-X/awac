import play.Project._
import sbt.Keys._
import sbt._


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
        //javaEbean,
        //	"com.github.play2war.ext" %% "jboss7-reflections-vfs-integration-play2" % "1.0.0", // add this after your others dependencies,
        //	"com.github.play2war.ext" %% "redirect-playlogger" % "1.0.1", // add this after your others dependencies
        "org.xhtmlrenderer" % "core-renderer" % "R8",
        "net.sf.jtidy" % "jtidy" % "r938",
        "org.apache.commons" % "commons-email" % "1.3.1",
        "commons-io" % "commons-io" % "2.3",
        "org.springframework" % "spring-context" % "3.2.3.RELEASE",
        "org.springframework" % "spring-context-support" % "3.2.3.RELEASE",
        "org.springframework" % "spring-expression" % "3.2.3.RELEASE",
        "org.springframework" % "spring-orm" % "3.1.1.RELEASE",
        "org.springframework" % "spring-test" % "3.2.5.RELEASE",
        "com.googlecode.ehcache-spring-annotations" % "ehcache-spring-annotations" % "1.2.0",
        "net.sf.ehcache" % "ehcache" % "2.7.2",
        "com.google.inject" % "guice" % "3.0" % "test",
        "info.cukes" % "cucumber-guice" % "1.1.5" % "test",
        "info.cukes" % "cucumber-java" % "1.1.5" % "test",
        "info.cukes" % "cucumber-junit" % "1.1.5" % "test",
        "info.cukes" % "cucumber-scala_2.10" % "1.1.5" % "test",
        "com.google.guava" % "guava" % "14.0"
    )

    libraryDependencies += "org.apache.commons" % "commons-email" % "1.3.1"

    libraryDependencies += "com.typesafe" %% "play-plugins-util" % buildVersion

    libraryDependencies += "xalan" % "xalan" % "2.7.1"

    libraryDependencies += "org.apache.xmlgraphics" % "batik-rasterizer" % "1.7" excludeAll (
      ExclusionRule(organization = "org.apache.xalan"),
      ExclusionRule(organization = "org.apache.xerces"),
      ExclusionRule(organization = "org.xhtmlrenderer")
      )

    // libraryDependencies += "com.google.gdata" % "core" % "1.47.1"

    libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.2"

    // velocity plugin
    libraryDependencies += "jp.furyu" %% "play-velocity-plugin" % "1.2"


    lazy val angularCompileTask = TaskKey[Unit]("angular-compile", "Compile angular app")
    val angularCompileSettings = angularCompileTask := {
        new AngularCompileTask().execute()
    }

    val main = play.Project(appName, appVersion, appDependencies)
        //.settings(Play2WarPlugin.play2WarSettings: _*)
        //.settings(
        // set war plugin  for 3.0 servlet container as Servlet 3.0: Tomcat 7, JBoss 7, JBoss EAP 6, Glassfish 3, Jetty 8
        //  Play2WarKeys.servletVersion := "3.0"
        //  )
        //        .settings(
        //            // work around regarding unit testing problem on 2.1.3
        //            testOptions in Test ~= { args =>
        //                for {
        //                    arg <- args
        //                    val ta: Tests.Argument = arg.asInstanceOf[Tests.Argument]
        //                    val newArg = if (ta.framework == Some(TestFrameworks.JUnit)) ta.copy(args = List.empty[String]) else ta
        //                } yield newArg
        //            }
        //        )
        .settings(
            angularCompileSettings, resources in Compile <<= (resources in Compile).dependsOn(angularCompileTask)
        )


    //        .settings (
    //          unmanagedResourceDirectories in Test &lt;+= baseDirectory( _ / "features" )
    //        )
    // Add your own project settings here

    javaOptions ++= Seq("-Xmx512M", "-Xmx2048M", "-XX:MaxPermSize=2048M");

}
