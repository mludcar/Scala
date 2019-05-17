package TaylorBox

/**
  * Created by miguel.ludena on 14/10/2018.
  */

/*
  * Que siginicia el [A] en:
  * def sortList[A](xs: List[A]): List[A] = {...}
 */
object TypeBracketsFunctionDefinition extends App{

  val list = 10 :: 30 :: List(20)
  val stringList = 10 :: 30 :: List("20")
  def addList[A](xs: List[A]): List[A] = xs

  def addListInt = addList[Int] _

  println(addListInt(list))
  //println(addListInt(stringList)) ERROR DE COMPILACION -> TypeMisMatch encuentra Any y necesita Int
  println(addList(stringList))

}
