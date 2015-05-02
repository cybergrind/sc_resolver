package wgrm.resolver.db

import java.util.{LinkedHashMap}

import org.yaml.snakeyaml.{Yaml}


class YamlDb extends RealmDb {
  var r:LinkedHashMap[String, Any] = null

  def load_file(fname:String):String = {
    val src = scala.io.Source.fromFile(fname)
    try src.mkString
      .replaceAll("!!python/\\w+", "")
      .replaceAll("!string!join", "")
    finally src.close
  }

  def setRealm(realm:String) = {
    val parser = new Yaml
    var r = parser.load(this.load_file(realm.concat(".yaml")))
      .asInstanceOf[LinkedHashMap[String, Any]]
    this.r = r
  }

  def get(a: Any, what:String): Any = a match {
    case s: LinkedHashMap[String, Any] =>
      //println("We get LinkedHashMap")
      s.get(what)
    case s: Any =>
      println("We get: c=".concat(s.getClass.toString))
      s
    case null =>
      println("We get: null")
      null.asInstanceOf[Any]
  }

  def getByPath(path:String): String = {
    path.split('.').fold(this.r.asInstanceOf[Any])(
      (x:Any, y:Any) => {
        this.get(x, y.asInstanceOf[String]).asInstanceOf[Any]
      }).asInstanceOf[String]
  }

  def getCurrentVersion(project:String):Option[String] = {
    var r = Some(this.getByPath(s"$project.components_redefines.$project.version"))
    println(r.getClass)
    return r
  }
  def getCurrentVersion(game:String, project: String):Option[String] = {
    Some(this.getByPath(s"$game.$project.components_redefines.$project.version"))
  }
}
