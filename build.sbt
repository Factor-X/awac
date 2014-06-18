name := "awac"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.18"

play.Project.playJavaSettings
