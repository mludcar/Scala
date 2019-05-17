package Hackerrank.Recursion

/**
  * Created by miguel.ludena on 26/08/2018.
  */
object FibonacciNumbers extends App{

  def fibonacci(x:Int):Int = {
    // Fill Up this function body
    // You can add another function as well, if required
    if(x == 1) 0
    else if(x == 2) 1
    else fibonacci(x-1) + fibonacci(x-2)
  }

  println(fibonacci(3))
  println(fibonacci(4))
  println(fibonacci(5))
}
