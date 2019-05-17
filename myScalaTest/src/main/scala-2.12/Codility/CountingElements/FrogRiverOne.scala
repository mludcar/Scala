package Codility.CountingElements

import scala.collection.immutable.ListMap

/**
  * Created by miguel.ludena on 23/03/2019.
  */
/*
A small frog wants to get to the other side of a river. The frog is initially located on one bank of the river (position 0)
and wants to get to the opposite bank (position X+1). Leaves fall from a tree onto the surface of the river.

You are given an array A consisting of N integers representing the falling leaves. A[K] represents the position where
one leaf falls at time K, measured in seconds.

The goal is to find the earliest time when the frog can jump to the other side of the river. The frog can cross only
when leaves appear at every position across the river from 1 to X (that is, we want to find the earliest moment
when all the positions from 1 to X are covered by leaves). You may assume that the speed of the current in the river is
negligibly small, i.e. the leaves do not change their positions once they fall in the river.

For example, you are given integer X = 5 and array A such that:

  A[0] = 1
  A[1] = 3
  A[2] = 1
  A[3] = 4
  A[4] = 2
  A[5] = 3
  A[6] = 5
  A[7] = 4
In second 6, a leaf falls into position 5. This is the earliest time when leaves appear in every position across the river.

Write a function:

object Solution { def solution(x: Int, a: Array[Int]): Int }

that, given a non-empty array A consisting of N integers and integer X, returns the earliest time when the frog can
jump to the other side of the river.

If the frog is never able to jump to the other side of the river, the function should return −1.

For example, given X = 5 and array A such that:

  A[0] = 1
  A[1] = 3
  A[2] = 1
  A[3] = 4
  A[4] = 2
  A[5] = 3
  A[6] = 5
  A[7] = 4
the function should return 6, as explained above.

Write an efficient algorithm for the following assumptions:

N and X are integers within the range [1..100,000];
each element of array A is an integer within the range [1..X].
 */
object FrogRiverOne extends App{

  //For example, for the input (2, [2, 2, 2, 2, 2]) the solution returned a wrong answer (got 0 expected -1)
  def solutionBad(x: Int, a: Array[Int]): Int = if(x > a.length) -1 else a.indexOf(x)

  //For example, for the input (5, [1, 2, 3, 5, 3, 1]) the solution returned a wrong answer (got 3 expected -1).
  def solutionBadAgain(x: Int, a: Array[Int]): Int ={
    val time = a.indexOf(x)
    val conditions = time == -1 || a.indexOf(1) == -1
    if(x > a.length || conditions) -1
    else time
  }

  //For example, for the input (3, [1, 3, 1, 3, 2, 1, 3]) the solution returned a wrong answer (got 1 expected 4).
  def solutionBadAgainAndAgain(x: Int, a: Array[Int]): Int = {
    val time = a.indexOf(x)
    val conditions = time == -1 || !a.distinct.sorted.containsSlice((1 to x))
    if(x > a.length || conditions) -1
    else time
  }

  def solutionLittleBetter(x: Int, a: Array[Int]): Int ={
    val res = (a zip (0 until a.length)).groupBy(_._1)
                                        .map{case (posicion, tupla) => (posicion, tupla.sorted.head._2)}
                                        .values.max
    if (res == 0) -1 else res
  }

  def solution(x: Int, a: Array[Int]): Int ={

    val res = (a zip (0 until a.length)).groupBy(_._1)
      .map{case (posicion, tupla) => (posicion, tupla.sorted.head._2)}
      .values.max
    if (res == 0 || x > a.length || a.indexOf(x) == -1) -1 else res
  }


  println(solution(5, Array(1, 3, 1, 4, 2, 3, 5, 4))) // => 6
  println(solution(2, Array(2, 2, 2, 2, 2))) // => -1
  println(solution(5, Array(1, 2, 3, 5, 3, 1))) // => 3
  println(solution(3, Array(1, 3, 1, 3, 2, 1, 3))) // => 4

}
