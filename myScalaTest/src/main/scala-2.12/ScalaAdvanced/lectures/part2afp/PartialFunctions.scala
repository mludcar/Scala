package ScalaAdvanced.lectures.part2afp

/**
  * Created by miguel.ludena on 05/04/2019.
  */
object PartialFunctions extends App{

  val aFunction = (x: Int) => x + 1 // Function1[Int, Int] === Int => Int

  // Trying to restrict the function to certain values(gypsy edition)
  val aFussyFunction = (x: Int) =>
    if(x == 1) 42
    else if(x == 2) 56
    else if(x== 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  // Si entra un dato distinto a 1,2 o 5 lanzará un match error
  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }
  // {1,2,5} => Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } // partial function Value, Both are equivalents

  println(aPartialFunction(2))

  // PF utilities
  println(aPartialFunction.isDefinedAt(67))

  //lift, devuelve un opcional de la función parcial según este definido o no
  val lifted = aPartialFunction.lift
  println(lifted(2))
  println(lifted(98))

  //Encadenado de funciones parciales
  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(45))

  // PF extend normal functions

  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOF accept partial functions as well
  val aMappedList = List(1,2,3).map {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  }
  println(aMappedList) // si cambiamos por un numero no definido da MatchError

  /*
    Note: PF can only have ONE parameter type
   */

  /*
      Exercises
      1 - construct a PF instance(anonymous class)
      2 - create a dumb chatbot as a PF
   */

  val chatBox = new PartialFunction[String, Unit]{
    def apply(a: String) = println(s"You said: ${a}")
    def isDefinedAt(a: String) = a != ""
  }

  scala.io.Source.stdin.getLines().foreach(chatBox)
}
