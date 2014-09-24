import sbt.ExclusionRule

name := "awac"

version := "1.0-SNAPSHOT"

ebeanEnabled := false

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