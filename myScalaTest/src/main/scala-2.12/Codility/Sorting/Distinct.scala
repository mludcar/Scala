package Codility.Sorting

/**
  * Created by miguel.ludena on 27/03/2019.
  */
/*
Write a function

object Solution { def solution(a: Array[Int]): Int }

that, given an array A consisting of N integers, returns the number of distinct values in array A.

For example, given array A consisting of six elements such that:

 A[0] = 2    A[1] = 1    A[2] = 1
 A[3] = 2    A[4] = 3    A[5] = 1
the function should return 3, because there are 3 distinct values appearing in array A, namely 1, 2 and 3.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [0..100,000];
each element of array A is an integer within the range [−1,000,000..1,000,000].
 */

object Distinct extends App{

  //75 %
  def solution(a: Array[Int]): Int = {
    a.foldLeft[List[Int]](List()){
      (acc, value) => if(!acc.contains(value)) acc :+ value else acc
    }.length
  }

  //MONOID TRIAL
  trait Monoid[T] {
    def op(l: T, r: T): T
    def zero: T
  }
  println(solution(Array(2,1,1,2,3,1)))
}
