package wgrm.resolver.db

import java.util.{ LinkedHashMap }

import org.yaml.snakeyaml.{ Yaml }

import wgrm.resolver.Const


class YamlDb extends RealmDb {
  var data: LinkedHashMap[String, Any] = null

  def load_file(fname: String): String = {
    val src = scala.io.Source.fromFile(fname)
    try src.mkString
      .replaceAll("!!python/\\w+", "")
      .replaceAll("!string!join", "")
    finally src.close
  }

  def setRealm(realm: String) = {
    val parser = new Yaml
    val data = parser.load(this.load_file(realm.concat(".yaml")))
      .asInstanceOf[LinkedHashMap[String, Any]]
    this.data = data
  }

  case class LHM(value: LinkedHashMap[String, Any])

  def get(a: Any, what: String): Option[Any] = a match {
    case Some(LHM(s)) =>
      s.containsKey(what) match {
        case true => Some(s.get(what))
        case false => None
      }
    case LHM(s) =>
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

  def getByPath(path: String): Option[String] = {
    path.split('.').fold(this.data.asInstanceOf[Any])(
      (x: Any, y: Any) => {
        x match {
          case Some(yaml_part) =>
            this.get(yaml_part, y.asInstanceOf[String]).asInstanceOf[Any]
          case _ =>
            return None
        }
      }
    ).asInstanceOf[Option[String]]
  }

  def getCurrentVersion(project: String): Option[String] = {
    val r = this.getByPath(s"$project.components_redefines.$project.version")
    println(r.getClass)
    return r
  }

  def getCurrentVersion(game: String, project: String): Option[String] = {
    this.getByPath(s"$game.$project.components_redefines.$project.version")
  }

  def findByMask(mask:String): List[String] = {
    /* "*.component_redefines" => List[ProjectsStrings] */
    var ret: List[String] = List()
    var path_position: Option[String] = None
    val splitted = mask.split(".")
    splitted.map(part => {
      part match {
        case "*" =>
          path_position
      }
    })
    return ret
  }

  def findAllProjects(): List[String] = {
    var ret: List[String] = List()
    ret = this.findByMask("*.components_redefines")
    Const.GAMES.map(x => {
      ret ++= this.findByMask(s"$x.*.components_redefines")
    })
    return ret
  }
}
