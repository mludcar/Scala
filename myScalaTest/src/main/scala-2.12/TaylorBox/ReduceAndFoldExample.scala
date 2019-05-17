package TaylorBox

/**
  * Created by miguel.ludena on 07/11/2018.
  */
object ReduceAndFoldExample extends App{

  val a = Array(12, 6, 15, 2, 20, 9)
  val resultReduceLeft = a.reduceLeft(_ + _)
  // returns the max of the two elements
  val findMax = (x: Int, y: Int) => {
    val winner = x max y
    println(s"compared $x to $y, $winner was larger")
    winner
  }

  a.reduceLeft(findMax)
  println(resultReduceLeft)
}
