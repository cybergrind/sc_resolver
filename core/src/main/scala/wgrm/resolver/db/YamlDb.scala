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

  def get(a: Any, what:String): Option[Any] = a match {
    case s: Some[LinkedHashMap[String, Any]] =>
      //println("We get LinkedHashMap")
      val extracted = s.get
      extracted.containsKey(what) match {
        case true => Some(extracted.get(what))
        case false => None
      }
    case s: LinkedHashMap[String, Any] =>
      //println("We get LinkedHashMap")
      s.containsKey(what) match {
        case true => Some(s.get(what))
        case false => None
      }
    case s: Any =>
      println("We get: c=".concat(s.getClass.toString))
      Some(s)
    case _ =>
      println("We get: null")
      None
  }

  def getByPath(path:String): Option[String] = {
    path.split('.').fold(this.r.asInstanceOf[Any])(
      (x:Any, y:Any) => {
        x match {
          case Some(yaml_part) =>
            this.get(yaml_part, y.asInstanceOf[String]).asInstanceOf[Any]
          case _ =>
            return None
        }
      }).asInstanceOf[Option[String]]
  }

  def getCurrentVersion(project:String):Option[String] = {
    var r = this.getByPath(s"$project.components_redefines.$project.version")
    println(r.getClass)
    return r
  }
  def getCurrentVersion(game:String, project: String):Option[String] = {
    this.getByPath(s"$game.$project.components_redefines.$project.version")
  }
}
