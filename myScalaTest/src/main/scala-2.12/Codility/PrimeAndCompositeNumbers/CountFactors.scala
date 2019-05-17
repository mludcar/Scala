package Codility.PrimeAndCompositeNumbers

/**
  * Created by miguel.ludena on 25/04/2019.
  */
/*
A positive integer D is a factor of a positive integer N if there exists an integer M such that N = D * M.

For example, 6 is a factor of 24, because M = 4 satisfies the above condition (24 = 6 * 4).

Write a function:

object Solution { def solution(n: Int): Int }

that, given a positive integer N, returns the number of its factors.

For example, given N = 24, the function should return 8, because 24 has 8 factors, namely 1, 2, 3, 4, 6, 8, 12, 24.
There are no other factors of 24.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [1..2,147,483,647].
 */
object CountFactors extends App{

  //71 %
  def solution(n: Int): Int = (1 to n).filter( n % _ == 0).length

  println(solution(2147483647))
}
