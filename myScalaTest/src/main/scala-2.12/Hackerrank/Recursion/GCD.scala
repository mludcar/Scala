package Hackerrank.Recursion

/**
  * Created by miguel.ludena on 26/08/2018.
  */
object GCD extends App{

  def gcd(x: Int, y: Int): Int = {

    if(x > y) gcd(y, x)

    if (y % x == 0) x
    else gcd(y % x, x)
  }

  println(gcd(2, 3))

}
