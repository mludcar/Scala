package Codility.TimeComplexity

/**
  * Created by miguel.ludena on 23/03/2019.
  */
/*
A small frog wants to get to the other side of the road. The frog is currently located at position X and wants to get to
a position greater than or equal to Y. The small frog always jumps a fixed distance, D.

Count the minimal number of jumps that the small frog must perform to reach its target.

Write a function:

object Solution { def solution(x: Int, y: Int, d: Int): Int }

that, given three integers X, Y and D, returns the minimal number of jumps from position X to a position equal to or
greater than Y.

For example, given:

  X = 10
  Y = 85
  D = 30
the function should return 3, because the frog will be positioned as follows:

after the first jump, at position 10 + 30 = 40
after the second jump, at position 10 + 30 + 30 = 70
after the third jump, at position 10 + 30 + 30 + 30 = 100
Write an efficient algorithm for the following assumptions:

X, Y and D are integers within the range [1..1,000,000,000];
X ≤ Y.
 */
object FrogJmp extends App{
  def solution(x: Int, y: Int, d: Int): Int = {
    def solutionAux(acc: Int, x: Int): Int = if(x < y) solutionAux(acc + 1, x + d) else acc
    solutionAux(0, x)
  }

  def solutionB(x: Int, y: Int, d: Int): Int = math.ceil((y - x).toDouble/d).toInt
  println(solutionB(10,85,30))
}
