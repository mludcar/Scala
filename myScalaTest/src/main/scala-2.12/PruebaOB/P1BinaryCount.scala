package PruebaOB

/**
  * Created by miguel.ludena on 29/04/2019.
  */
/*
  Write a function:

    object Solution { def solution(a: Int, b: Int): Int }

  that, given two negatives integers A and B, returns the number of bits set to 1 in the binary representation of the
  number A * B.

  For example, given A = 3 and B = 7 the function should return 3, because the binary representation of A*B=3*7=21
  is 10101 and it contains three bits set to 1.

  Assume that:
    - A and B are integers within the range [0.. 100,000,000]

  In your solution, focus on correctness. The performance of your solution will not be the focus of the assessment.
 */
object P1BinaryCount {

  //50%
  def solution(a: Int, b: Int): Int = (a*b).toBinaryString.count(_ ==1)

}
