package ScalaCourse1.Week2

/**
  * Created by miguel.ludena on 30/08/2018.
  */
object Factorialv2 extends App{
  def sum(f: Int => Int, a: Int, b: Int): Int ={
    def loop(a: Int, acc: Int): Int ={
      if(a > b) acc
      else loop(a + 1, acc + f(a))
    }
    loop(a, 0)
  }

  println(sum(x => x * x, 3, 5))

}
