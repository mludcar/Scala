package Codility.CountingElements

import scala.util.Sorting

/**
  * Created by miguel.ludena on 25/03/2019.
  */
/*
This is a demo task.

Write a function:

object Solution { def solution(a: Array[Int]): Int }

that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.

For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.

Given A = [1, 2, 3], the function should return 4.

Given A = [−1, −3], the function should return 1.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [1..100,000];
each element of array A is an integer within the range [−1,000,000..1,000,000].
 */
object MissingInteger extends App{


  //66% Falla performance a partir de arrays 40000
  def solutionBad(a: Array[Int]): Int = {
    def solutionRec(min: Int): Int ={
      if(a.contains(min)) solutionRec(min+1)
      else min
    }
    solutionRec(1)
  }

  def solution(a: Array[Int]): Int = {

    def solutionRec(list: List[Int], min:Int): Int = {
      if(list.isEmpty || list.head != min) min
      else solutionRec(list.tail, min + 1)
    }
    solutionRec(a.toList.filter(_ > 0).distinct.sorted, 1)
  }

  println(solution(Array(1, 3, 6, 4, 1, 2))) //5
  println(solution(Array(1, 2, 3))) //4
  println(solution(Array(-1, -3))) //1
}
