package Codility.Sorting

/**
  * Created by miguel.ludena on 27/03/2019.
  */
/*
A non-empty array A consisting of N integers is given. The product of triplet (P, Q, R) equates to
A[P] * A[Q] * A[R] (0 ≤ P < Q < R < N).

For example, array A such that:

  A[0] = -3
  A[1] = 1
  A[2] = 2
  A[3] = -2
  A[4] = 5
  A[5] = 6
contains the following example triplets:

(0, 1, 2), product is −3 * 1 * 2 = −6
(1, 2, 4), product is 1 * 2 * 5 = 10
(2, 4, 5), product is 2 * 5 * 6 = 60
Your goal is to find the maximal product of any triplet.

Write a function:

object Solution { def solution(a: Array[Int]): Int }

that, given a non-empty array A, returns the value of the maximal product of any triplet.

For example, given array A such that:

  A[0] = -3
  A[1] = 1
  A[2] = 2
  A[3] = -2
  A[4] = 5
  A[5] = 6
the function should return 60, as the product of triplet (2, 4, 5) is maximal.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [3..100,000];
each element of array A is an integer within the range [−1,000..1,000].
 */
object MaxProductOfThree extends App{

  def solution(a: Array[Int]): Int = {
    def aplicable(b: Array[Int]): Boolean =
      (math.abs((b(b.length - 2) + b(b.length - 1)) / 2.0) >= math.abs(b(1)) && b(1) > 0 && b(b.length - 2) < 0)

    val filtered = a.sortBy(-_)
    if(aplicable(filtered))
      (filtered(0) +: filtered.takeRight(2)).product
    else filtered.take(3).product
  }

  println(solution(Array(2,100,3,-1000))) //600
  println(solution(Array(4,7,3,2,1,-3,-5))) //105
  println(solution(Array(-5,-6,-4,-7,-10))) //-120
  println(solution(Array(-3,1,2,-2,5,6))) //60
  println(solution(Array(-5,5,-5,4))) //125
  println(solution(Array(-4,-6,3,4,5))) //120


}
