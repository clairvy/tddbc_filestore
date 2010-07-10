import sbt._

class MyProject(info: ProjectInfo) extends DefaultProject(info) {
//  val specs = "org.scala-tools.testing" % "specs_2.8.0.Beta1" % "1.6.4" % "test"
  val snapshots = "Snapshots" at "http://scala-tools.org/repo-snapshots/"
  val specs = "org.scala-tools.testing" % "specs_2.8.0-SNAPSHOT" % "1.6.5-SNAPSHOT" % "test"
}
