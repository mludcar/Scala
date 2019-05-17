package WebServices
import javax.script.ScriptEngineManager

/**
  * Created by miguel.ludena on 11/09/2018.
  */
object JavaScriptInvoker extends App{
  val myResource = io.Source.fromURL(getClass.getResource("/script.txt"))
  val lines = try myResource.mkString finally myResource.close()

  println(lines)

  val engine = new ScriptEngineManager().getEngineByMimeType("text/javascript")
  engine.eval(lines)


}
