package Codility.PrefixSums

/**
  * Created by miguel.ludena on 27/03/2019.
  */
/*
Write a function:

object Solution { def solution(a: Int, b: Int, k: Int): Int }

that, given three integers A, B and K, returns the number of integers within the range [A..B] that are divisible by K,
i.e.:
{ i : A ≤ i ≤ B, i mod K = 0 }

For example, for A = 6, B = 11 and K = 2, your function should return 3, because there are three numbers divisible by 2
within the range [6..11], namely 6, 8 and 10.

Write an efficient algorithm for the following assumptions:

A and B are integers within the range [0..2,000,000,000];
K is an integer within the range [1..2,000,000,000];
A ≤ B.
 */
object CountDiv extends App{

  // 50% Very Bad Performance O(B-A)
  def solution(a: Int, b: Int, k: Int): Int = {
    (a to b).foldLeft(0){
      (acc, value) => if(value % k == 0) acc + 1 else acc
    }
  }

  println(solution(6, 11, 2))

  //MONOID TRIAL

}
