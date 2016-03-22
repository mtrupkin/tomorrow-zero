name := """tomorrow-zero"""

version := "1.0"

scalaVersion := "2.11.8"

resolvers += Resolver.url("me.mtrupkin ivy repo", url("http://dl.bintray.com/mtrupkin/ivy/"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.4.0-M2",
  "org.scalafx" %% "scalafx" % "8.0.60-R9",
  "org.mtrupkin" %% "math-lib" % "1.0"
)
