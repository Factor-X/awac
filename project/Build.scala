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
//       .settings(
//         javascriptEntryPoints <<= baseDirectory(base =>
//           base / "app" / "assets" / "javascripts"  ** "*.js")
//       )
      .settings (
        // This tells Play to optimize this file and its dependencies
        requireJs += "main.js",
        // The main config file
        // See http://requirejs.org/docs/optimization.html#mainConfigFile
        requireJsShim := "main.js"
    )
        .settings(
        resolvers += "jade4j-releases" at "https://github.com/neuland/jade4j/raw/master/releases",
        resolvers += "webjars" at "http://webjars.github.com/m2"
        )
        .settings(
            downloadTranslationsTask
        )
//        .settings (
//          unmanagedResourceDirectories in Test &lt;+= baseDirectory( _ / "features" )
//        )
    // Add your own project settings here

  val base = new File("./target/scala-2.10/resource_managed/main/public/")
  val list = ((base / "javascripts" ** "*.js") --- (base / "scala-2.10" ** "_*")).get.sortBy(_.name)
  list.foreach(println)

}
