package wgrm.resolver.db

trait RealmDb {
  //var realm:Any

  def setRealm(realm: String): Unit
  def getCurrentVersion(project: String): Option[String]
  def getCurrentVersion(game:String, project: String): Option[String]
}
