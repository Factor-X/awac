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
        javaEbean,
        //	"com.github.play2war.ext" %% "jboss7-reflections-vfs-integration-play2" % "1.0.0", // add this after your others dependencies,
        //	"com.github.play2war.ext" %% "redirect-playlogger" % "1.0.1", // add this after your others dependencies
        "org.xhtmlrenderer" % "core-renderer" % "R8",
        "net.sf.jtidy" % "jtidy" % "r938",
        "org.apache.commons" % "commons-email" % "1.3.1",
        "commons-io" % "commons-io" % "2.3",
        "org.springframework" % "spring-context" % "3.2.3.RELEASE",
        "org.springframework" % "spring-expression" % "3.2.3.RELEASE"
    )

    libraryDependencies += "org.apache.commons" % "commons-email" % "1.3.1"

    libraryDependencies += "com.typesafe" %% "play-plugins-util" % buildVersion

    libraryDependencies += "com.google.gdata" % "core" % "1.47.1"

    lazy val downloadTranslations = TaskKey[Unit]("download-translations", "Download translations from Google Spreadsheet")
    val downloadTranslationsTask = downloadTranslations := {
        new DownloadTranslationsTask().execute()
    }


    val main = play.Project(appName, appVersion, appDependencies)
        //.settings(Play2WarPlugin.play2WarSettings: _*)
        //.settings(
        // set war plugin  for 3.0 servlet container as Servlet 3.0: Tomcat 7, JBoss 7, JBoss EAP 6, Glassfish 3, Jetty 8
        //  Play2WarKeys.servletVersion := "3.0"
        //  )
        .settings(
            // work around regarding unit testing problem on 2.1.3
            testOptions in Test ~= { args =>
                for {
                    arg <- args
                    val ta: Tests.Argument = arg.asInstanceOf[Tests.Argument]
                    val newArg = if (ta.framework == Some(TestFrameworks.JUnit)) ta.copy(args = List.empty[String]) else ta
                } yield newArg
            }
        )
        .settings(
            downloadTranslationsTask
        )
    // Add your own project settings here





}
