package Codility.TimeComplexity

/**
  * Created by miguel.ludena on 23/03/2019.
  */
/*
A non-empty array A consisting of N integers is given. Array A represents numbers on a tape.

Any integer P, such that 0 < P < N, splits this tape into two non-empty parts:
A[0], A[1], ..., A[P − 1] and A[P], A[P + 1], ..., A[N − 1].

The difference between the two parts is the value of:
|(A[0] + A[1] + ... + A[P − 1]) − (A[P] + A[P + 1] + ... + A[N − 1])|

In other words, it is the absolute difference between the sum of the first part and the sum of the second part.

For example, consider array A such that:

  A[0] = 3
  A[1] = 1
  A[2] = 2
  A[3] = 4
  A[4] = 3
We can split this tape in four places:

P = 1, difference = |3 − 10| = 7
P = 2, difference = |4 − 9| = 5
P = 3, difference = |6 − 7| = 1
P = 4, difference = |10 − 3| = 7
Write a function:

object Solution { def solution(a: Array[Int]): Int }

that, given a non-empty array A of N integers, returns the minimal difference that can be achieved.

For example, given:

  A[0] = 3
  A[1] = 1
  A[2] = 2
  A[3] = 4
  A[4] = 3
the function should return 1, as explained above.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [2..100,000];
each element of array A is an integer within the range [−1,000..1,000].
 */
object TapeEquilibrium extends App{
  val a = List(1)
  def solutionBad(a: Array[Int]): Int = {
    def difference(pair: (Array[Int], Array[Int])): Int = math.abs(pair._1.sum - pair._2.sum)
    def solutionRec(acc: Int, iter: Int): Int = {
      val pairedArray = a.splitAt(iter)
      if(iter < a.length) solutionRec(math.min(acc, difference(pairedArray)), iter +1)
      else acc
    }
    solutionRec(Int.MaxValue, 0)
  }
  def solution(a: Array[Int]): Int = {
    val sum = a.sum

    def difference(sumLeft: Int, sumRight: Int): Int = Math.abs(sumLeft - sumRight)

    def solutionRec(iter: Int, acc: Int, sumLeft: Int): Int =
      if (iter == a.length) acc
      else solutionRec(iter + 1, math.min(acc, difference(sumLeft, sum - sumLeft)), sumLeft + a(iter - 1))

    solutionRec(2, difference(a(0), sum - a(0)), a(0))
  }

  val myArray = new Array[Int](5)
  myArray(0) = 3
  myArray(1) = 1
  myArray(2) = 2
  myArray(3) = 4
  myArray(4) = 3

  println(solution(myArray))
  //(1 until myArray.length).foreach(x => println(s"Indice: $x ArrayA:${myArray.splitAt(x)._1.mkString("-")} ArrayB:${myArray.splitAt(x)._2.mkString("-")}"))
}
