package ScalaCourse1.Week1

import scala.annotation.tailrec

/**
  * Created by miguel.ludena on 30/08/2018.
  */
object Factorial extends App{

  def factorial(n: Int): Int = {

    @tailrec
    def loop(acc: Int, n: Int): Int = {
      if (n == 0) acc
      else loop(acc * n, n - 1)
    }
    loop(1, n)
  }
  println(factorial(4))
}
