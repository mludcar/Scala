package Codility.Leader

/**
  * Created by miguel.ludena on 24/04/2019.
  */

/*
A non-empty array A consisting of N integers is given.

The leader of this array is the value that occurs in more than half of the elements of A.

An equi leader is an index S such that 0 ≤ S < N − 1 and two sequences A[0], A[1], ..., A[S] and
A[S + 1], A[S + 2], ..., A[N − 1] have leaders of the same value.

For example, given array A such that:

    A[0] = 4
    A[1] = 3
    A[2] = 4
    A[3] = 4
    A[4] = 4
    A[5] = 2
we can find two equi leaders:

0, because sequences: (4) and (3, 4, 4, 4, 2) have the same leader, whose value is 4.
2, because sequences: (4, 3, 4) and (4, 4, 2) have the same leader, whose value is 4.
The goal is to count the number of equi leaders.

Write a function:

object Solution { def solution(a: Array[Int]): Int }

that, given a non-empty array A consisting of N integers, returns the number of equi leaders.

For example, given:

    A[0] = 4
    A[1] = 3
    A[2] = 4
    A[3] = 4
    A[4] = 4
    A[5] = 2
the function should return 2, as explained above.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [1..100,000];
each element of array A is an integer within the range [−1,000,000,000..1,000,000,000].
 */
object EquiLeader extends App{

  val array = Array(4,3,4,4,4,2)
  val otherArray=Array(1,2,3,4,5)

  def solution(a: Array[Int]): Int = {
    def findLeader(a: Array[Int]): Int = {
      val ordered = a.sorted
      val length = a.length
      var count = 1
      var num = Int.MaxValue

      if(length == 1) return a(0)

      for(i <- 0 until length - 1 if(i - count < ordered.length/2)) {
        if(ordered(i) == ordered(i + 1)) count += 1 else count = 1
        if(count > length/2) num = ordered(i)
      }
      num
    }
    def findEquileader(a: Array[Int], b: Array[Int]): Boolean = {
      println(s"Leader izqui: ${findLeader(a)} Leader Derecha: ${findLeader((b))}")
      if(findLeader(a) == findLeader(b) && findLeader(a) != Int.MaxValue) true else false
    }
    var count = 0

    for(i <- 1 until a.length) {
      val (l,r) = a.splitAt(i)
      println(s"Izquierda: (${l.mkString(",")}) Derecha: (${r.mkString(",")})}")
      if(findEquileader(l,r)){
        count += 1
        println("Encontrado equiLeader")
      }else {
        println("No encontrado")
      }
    }
    count
  }

  println(solution(array))


}
