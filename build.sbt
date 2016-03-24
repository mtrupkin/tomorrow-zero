name := """tomorrow-zero"""

lazy val math = project

lazy val console = project.in(file("console-lib")).dependsOn(math)

lazy val consoleFx = project.dependsOn(console)

lazy val app = project.dependsOn(consoleFx)
