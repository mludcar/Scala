package ScalaCourse2.week1

/**
  * Created by miguel.ludena on 13/10/2018.
  */
object Collections extends App{

  //Version idealizada de Colecciones(No funcionaría, pq faltan apply's y ::)
  /*
  class MyList[+T]{

    def map[U](f: T => U): MyList[U] = this match {
      case x :: xs => f(x) :: xs.map(f)
      case Nil => Nil
    }

    def flatMap[U](f: T => MyList[U]): MyList[U] = this match {
      case x :: xs => f(x) ++ xs.flatMap(f)
      case Nil => Nil
    }

    def filter(p: T => Boolean): MyList[T] = this match {
      case x :: xs =>
        if(p(x)) x :: xs.filter(p) else xs.filter(p)
      case Nil => Nil
    }
  }
  */
  //FOR-EXPRESSIONS

  //Version 1
  /*
  (1 until n) flatMap( i =>
    (1 until i) filter (j => isPrime(i + j)) map
      (j => (i, j)))
  */
  //Version 2
  /*
  for {
    i <- 1 until n
    j <- 1 until i
    if isPrime(i + j)
  } yield (i, j)
  */

}
