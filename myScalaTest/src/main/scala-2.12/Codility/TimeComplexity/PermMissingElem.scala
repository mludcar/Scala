package Codility.TimeComplexity

/**
  * Created by miguel.ludena on 23/03/2019.
  */
/*
An array A consisting of N different integers is given. The array contains integers in the range [1..(N + 1)], which
means that exactly one element is missing.

Your goal is to find that missing element.

Write a function:

object Solution { def solution(a: Array[Int]): Int }

that, given an array A, returns the value of the missing element.

For example, given array A such that:

  A[0] = 2
  A[1] = 3
  A[2] = 1
  A[3] = 5
the function should return 4, as it is the missing element.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [0..100,000];
the elements of A are all distinct;
each element of array A is an integer within the range [1..(N + 1)].
 */
object PermMissingElem extends App{

  def solution(a: Array[Int]): Int = ((1 to a.length + 1).sum) - a.sum

  val myArray = new Array[Int](4)
  myArray(0) = 2
  myArray(1) = 3
  myArray(2) = 1
  myArray(3) = 5

  println((solution(myArray)))
  //println(solution(myArray))
}
