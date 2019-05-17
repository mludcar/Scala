package ScalaCourse1.Week2

/**
  * Created by miguel.ludena on 31/08/2018.
  */
object CurryingProduct extends App{
  def product(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x, y) => x * y, 1)(a,b)

  println(product(x => x * x)(3,4))

  def fact(n: Int) = product(x => x)(1, n)

  println(fact(5))

  def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
    if(a > b) zero
    else combine(f(a), mapReduce(f, combine, zero)(a + 1, b))
  }
}
