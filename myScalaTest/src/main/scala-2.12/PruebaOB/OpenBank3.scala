package PruebaOB

/**
  * Created by miguel.ludena on 25/04/2019.
  */
object OpenBank3 extends App{

  /*def lier(a: Int, b: Int, c: Int): Boolean = if((a > b && b > c) || (a < b && b < c)) true else false

  def solution(a: Array[Int]): Int = {

    def findLier(a: Array[Int], acc: List[Int]): Array[Int] = {

      for(i <- 0 until a.length - 3) {
        if(lier(a(i), a(i+1), a(i+2))) findLier(a.tail, a(i) :: acc)
        else findLier(a)
      }

    }
  }*/

  val array = new Array[Int](8)
  array(0) = 0
  array(1) = 3
  array(2) = 3
  array(3) = 7
  array(4) = 5
  array(5) = 3
  array(6) = 11
  array(7) = 1

 /* for(i <- 1 to array.length){
    val subArray = array.take(i).takeRight(0)
    val u = array(0)
    val w = array(i)
    if(subArray.filter(x => ((u > x) && (x > w)) ||((u < x) && (x < w))).length > 1) // return

  }

  def solution(a: Array[Int]): Int = {

    for(i <- 1 )
  }*/
 def solution(a: Array[Int]): Int = {

   def getMinimumDistance(adyacentIndexes: List[(Int,Int)]):Int = {
     if (adyacentIndexes == null) -1
     else adyacentIndexes.foldLeft(Int.MaxValue)((A,B)=>if (math.abs(B._1 - B._2) < A) math.abs(B._1 - B._2) else A)
   }

   def getAdyacentIndexes(a: Array[Int]): List[(Int, Int)] = {
     for (i <- 0 until a.length - 1) yield
       for (j <- i + 1 until a.length) yield {
         val min = a(i)
         val max = a(j)
         var adyacent = true
         if (min == max) adyacent = false
         else
           for (k <- 0 until a.length) {
             if (k != i && k != j)
               if ((a(k) < min && a(k) > max) || (a(k) > min && a(k) < max))
               // Not adyacent
                 adyacent = false
           }
         if (adyacent) (i, j)
         else null
       }
   }.flatten.toList.filter(x => x != null)

   getMinimumDistance(getAdyacentIndexes(a))
 }

  println(solution(array))





}
