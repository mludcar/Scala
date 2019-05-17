package ScalaCourse2.week1

/**
  * Created by miguel.ludena on 13/10/2018.
  */
object PartialMatches extends App{

  val f: String => String = {case "ping" => "pong"}
  val g: PartialFunction[String, String] = {case "ojete" => "moreno"}

  println(f("ping"))
  //f.isDefined("ping") --> NO FUNCIONARIA
  println(g("ojete"))
  println(g.isDefinedAt("ojete"))// AL INICIARLA COMO PARTIAL FUNCTION IMPLEMENTA isDefinedAt

  //la función is defined solo mirará en caso de haber anidamiento al pattern matching primario
  //en el caso de abajo solo mirará eñ x :: rest y al ver q matchea dará un true
  val h: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: rest =>
      rest match {
        case Nil => "two"
      }
  }

  println(h.isDefinedAt(List(1,2,3)))

}
