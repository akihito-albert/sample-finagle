name := "FinagleSample"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.6",
  "com.twitter" %% "finagle-http" % "6.31.0"
)
