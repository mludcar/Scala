package Hackerrank.Introduction

import scala.collection.mutable.ListBuffer

/**
  * Created by miguel.ludena on 24/08/2018.
  */
object ListReplication extends App{

  def f(num:Int,arr:List[Int]):List[Int] = {
    val myList = ListBuffer[Int]()
    for(j <- arr; i <- 1 to num; if num >= 0; if num <= 100; if j >= 0; if j <= 10)
      myList += j
    myList.toList
  }

  val newList = f(2, List(1,2,3,4))
  println(newList.mkString("-"))

}
