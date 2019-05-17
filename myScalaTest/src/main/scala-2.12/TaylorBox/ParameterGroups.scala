package TaylorBox

/**
  * Created by miguel.ludena on 04/04/2019.
  */
object ParameterGroups extends App{

  //EJEMPLO 1
  def add(a: Int, b: Int, c: Int) = a + b + c
  def sum(a: Int)(b: Int)(c: Int) = a + b + c

  val first = sum(1)(2)(3)
  println(s"Resultado: ${first}")

}
