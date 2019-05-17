package Codility.PrimeAndCompositeNumbers

/**
  * Created by miguel.ludena on 25/04/2019.
  */
/*
An integer N is given, representing the area of some rectangle.

The area of a rectangle whose sides are of length A and B is A * B, and the perimeter is 2 * (A + B).

The goal is to find the minimal perimeter of any rectangle whose area equals N. The sides of this rectangle should be
only integers.

For example, given integer N = 30, rectangles of area 30 are:

(1, 30), with a perimeter of 62,
(2, 15), with a perimeter of 34,
(3, 10), with a perimeter of 26,
(5, 6), with a perimeter of 22.
Write a function:

object Solution { def solution(n: Int): Int }

that, given an integer N, returns the minimal perimeter of any rectangle whose area is exactly equal to N.

For example, given an integer N = 30, the function should return 22, as explained above.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [1..1,000,000,000].

N=101 => 204
 */
object MinPerimeterRectangle extends App{

  // 31%
  def solutionBad(n: Int): Int = {
    def perimeter(a: Int, b: Int): Int = 2 * (a+b)
    def loop(trial: Int, minimum: (Int, Int)): Int ={
      val newCandidateA = trial
      val newCandidateB = n/newCandidateA
      println(s"newCandidateA: $newCandidateA newCandidateB: $newCandidateB")
      if(!(newCandidateB % 1 == 0)) loop(newCandidateA + 1, minimum)
      else if((newCandidateA + newCandidateB) < (minimum._1 + minimum._2)) loop(newCandidateA + 1, (newCandidateA, newCandidateB))
      else perimeter(minimum._1,minimum._2)
    }
    loop(1, (n,n))
  }

  //70%
  def solution(n:Int): Int = {
    def perimeter(a: Int): Int = 2 * (n/a + a)
    def loop(trial: Int, minimum: Int): Int = {
      println(s"trial: $trial minimum: $minimum")
      if((trial * trial) >= n) minimum
      else if(n % trial == 0) loop(trial + 1, minimum min perimeter(trial))
      else loop(trial + 1, minimum)
    }
    loop(1, Int.MaxValue)
  }


  println(solution(101))

}
