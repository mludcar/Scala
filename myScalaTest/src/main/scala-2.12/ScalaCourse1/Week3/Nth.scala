package ScalaCourse1.Week3

/**
  * Created by miguel.ludena on 05/09/2018.
  */
object Nth extends App{

  def nth[T](n: Int, xs: List[T]): T = {
    if(xs.isEmpty) throw new IndexOutOfBoundsException
    if(n == 0) xs.head
    else nth(n - 1, xs.tail)
  }

  val list = new Cons(1, new Cons(2, new Cons(3, new Nil)))

  println(nth(2, list))
  println(nth(6, list))
}
