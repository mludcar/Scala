package Codility.Sorting

/**
  * Created by miguel.ludena on 28/03/2019.
  */
/*
We draw N discs on a plane. The discs are numbered from 0 to N − 1. An array A of N non-negative integers, specifying
the radiuses of the discs, is given. The J-th disc is drawn with its center at (J, 0) and radius A[J].

We say that the J-th disc and K-th disc intersect if J ≠ K and the J-th and K-th discs have at least one common point
(assuming that the discs contain their borders).

The figure below shows discs drawn for N = 6 and A as follows:

  A[0] = 1
  A[1] = 5
  A[2] = 2
  A[3] = 1
  A[4] = 4
  A[5] = 0

There are eleven (unordered) pairs of discs that intersect, namely:

discs 1 and 4 intersect, and both intersect with all the other discs;
disc 2 also intersects with discs 0 and 3.
Write a function:

object Solution { def solution(a: Array[Int]): Int }

that, given an array A describing N discs as explained above, returns the number of (unordered) pairs of intersecting
discs. The function should return −1 if the number of intersecting pairs exceeds 10,000,000.

Given array A shown above, the function should return 11, as explained above.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [0..100,000];
each element of array A is an integer within the range [0..2,147,483,647].
 */
object NumberOfDiscIntersections extends App{

  //43% problemas tanto con rendimiento como en caso de overflow e.g Array(1,2147483647,0)
  /*def solutionBad(a: Array[Int]): Int ={
    type LongRange = scala.collection.immutable.NumericRange.Inclusive[Long]
    def transformToRanges(c: Array[Int]) = (a.indices zip a).map{
      case (origin, radius) =>
        val origLong= origin.toLong
        val radLong = radius.toLong
        println((origLong - radLong to origLong + radLong).mkString(" - "))
        (origLong - radLong to origLong + radLong)
    }.toList

    def solutionRec(b: List[LongRange], acc: Int): Int = {
      if(b.isEmpty) acc
      else solutionRec(b.tail, acc + b.tail.filter(!b.head.intersect(_).isEmpty).length)
    }
    println(transformToRanges(a).mkString(" - "))
    solutionRec(transformToRanges(a), 0)
  }

  def solution(a: Array[Int]): Int ={
    def solutionRec(b: Array[Int]): Int = {
      2
    }
    2
  }

  println(solutionBad(Array(1,5,2,1,4,0))) //11
  println(solutionBad(Array(1,2147483647,0))) // 2*/
}
