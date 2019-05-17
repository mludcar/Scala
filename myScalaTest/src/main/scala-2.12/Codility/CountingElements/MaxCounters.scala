package Codility.CountingElements

/**
  * Created by miguel.ludena on 24/03/2019.
  */

/*
You are given N counters, initially set to 0, and you have two possible operations on them:

increase(X) − counter X is increased by 1,
max counter − all counters are set to the maximum value of any counter.
A non-empty array A of M integers is given. This array represents consecutive operations:

if A[K] = X, such that 1 ≤ X ≤ N, then operation K is increase(X),
if A[K] = N + 1 then operation K is max counter.
For example, given integer N = 5 and array A such that:

    A[0] = 3
    A[1] = 4
    A[2] = 4
    A[3] = 6
    A[4] = 1
    A[5] = 4
    A[6] = 4
    (3,4,4,6,1,4,4)
the values of the counters after each consecutive operation will be:

    (0, 0, 1, 0, 0)
    (0, 0, 1, 1, 0)
    (0, 0, 1, 2, 0)
    (2, 2, 2, 2, 2)
    (3, 2, 2, 2, 2)
    (3, 2, 2, 3, 2)
    (3, 2, 2, 4, 2)
The goal is to calculate the value of every counter after all operations.

Write a function:

object Solution { def solution(n: Int, a: Array[Int]): Array[Int] }

that, given an integer N and a non-empty array A consisting of M integers, returns a sequence of integers representing
the values of the counters.

Result array should be returned as an array of integers.

For example, given:

    A[0] = 3
    A[1] = 4
    A[2] = 4
    A[3] = 6
    A[4] = 1
    A[5] = 4
    A[6] = 4
the function should return [3, 2, 2, 4, 2], as explained above.

Write an efficient algorithm for the following assumptions:

N and M are integers within the range [1..100,000];
each element of array A is an integer within the range [1..N + 1].
 */

object MaxCounters extends App{

  //44% Mala respuesta, funciona, tiene timeouts
  def solutionBad(n: Int, a: Array[Int]): Array[Int] ={
    val initialArray = new Array[Int](n)
    (0 until n).foreach(initialArray(_) = 0)
    println(s"Valor de initial Array: ${initialArray.mkString(" - ")}")

    def increase(b: Array[Int], index: Int): Array[Int] = b.updated(index, b(index) + 1)

    def maxCounter(b: Array[Int]): Array[Int] = {
      val newArray = new Array[Int](b.length)
      (0 until b.length).foreach{x => newArray(x) = b.max}
      newArray
    }

    def operationSelector(b: Array[Int], index: Int): Array[Int] = {
      println(s"Indice: $index Valor: ${a(index)} n: $n Valor actual de la matrix: ${b.mkString("-")}")
      a(index) match {
        case x if (x <= n && x >= 1) => increase(b, a(index)-1)
        case y if (y == n + 1) => maxCounter(b)
        case _ => b
      }
    }

    def solutionRec(b: Array[Int], index: Int): Array[Int] = {
      if(index < a.length) solutionRec(operationSelector(b, index), index + 1)
      else b
    }
    solutionRec(initialArray, 0)
  }

  //88% falla la optimizacion para bumeros superiores a 10000
  def solution(n: Int, a: Array[Int]): Array[Int] = {
    val counters: Array[Int] = Array.ofDim(n)

    def increase(index: Int) = counters(index-1) = counters(index-1)+1

    def maxCounter() = {
      val max = counters.max
      (0 until n).foreach{x => counters(x) = max}
    }

    def getCounter(list: List[Int]): Array[Int] = {
      if(list.isEmpty) counters
      else {
        list.head match {
          case x: Int if x >= 1 && x <= n => increase(x)
          case x: Int if x > n => maxCounter()
        }
        getCounter(list.tail)
      }
    }
    getCounter(a.toList)
  }



  val myArray =  Array(3,4,4,6,1,4,4)
  println(solution(5, myArray).mkString(" - "))
  //println(operationSelector(myArray, 5, 1).mkString)
  //println(b)

}
