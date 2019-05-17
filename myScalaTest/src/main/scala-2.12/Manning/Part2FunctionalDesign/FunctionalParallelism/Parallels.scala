package Manning.Part2FunctionalDesign.FunctionalParallelism

/**
  * Created by miguel.ludena on 23/04/2019.
  */

trait Par[A] {
  def unit[A](a: => A): Par[A]
  def get[A](a: Par[A]): A
  def map2[A,B,C](a: Par[A], b: Par[B])(f: (A,B) => C): Par[C]
}

object Parallels extends App{

  // SUMMING A LIST USING A DIVIDE-AND-CONQUER ALGORITHM
  // IndexedSeq is a superclass of random-access sequences like Vector in the Standard library. Unlike lists, the
  // sequences provide an efficient splitAt method for dividing them into two parts at a particular index
  def sum(ints: IndexedSeq[Int]): Int = {
    if(ints.size <= 1)
      ints.headOption.getOrElse(0) // headOption is a method defined on all collections in Scala.
    else {
      val (l, r) = ints.splitAt(ints.length/2) // Divides the sequence in half using the splitAt function
      sum(l) + sum(r)  // Recursevely sums both halves and adds the results together
    }
  }
  // Unlike the foldLeft-based implementation, this implementation can be parallelized- the two halves can be summed
  // in parallel

  // UPDATING SUM WITH OUR CUSTOM DATA TYPE
  /*def sum2(ints: IndexedSeq[Int]): Int = {
    if(ints.size <= 1)
      ints.headOption.getOrElse(0)
    else {
      val (l,r) = ints.splitAt(ints.length/2)
      val sumL: Par[Int] = Par.unit(sum(l)) // Computes the left half in parallel
      val sumR: Par[Int] = Par.unit(sum(r)) // Computes the right half in parallel
      Par.get(sumL) + Par.get(sumR) // Extracts both results and sums them
    }
  }*/

  // PARALLEL COMPUTATIONS
  /*def sum3(ints: IndexedSeq[Int]): Int = {
    if(ints.size <= 1)
      Par.unit(ints.headOption.getOrElse(0))
    else {
      val (l,r) = ints.splitAt(ints.length/2)
      Par.map2(sum(l), sum(r))(_ + _)
    }
  }*/

  // EXERCISE 7.1
  // Par.map2 is a new higher-order function for combining the result of two parallel computations. What is its
  // signature? Give the most general signature possible (don´t assume it works only for Int)
  // def map2[A,B,C](a: Par[A], b: Par[B])(f: (A,B) => C): Par[C]



}
