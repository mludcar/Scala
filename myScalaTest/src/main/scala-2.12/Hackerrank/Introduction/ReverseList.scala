package Hackerrank.Introduction

import scala.collection.mutable.ListBuffer

/**
  * Created by miguel.ludena on 24/08/2018.
  */
object ReverseList extends App{
  def f(arr:List[Int]):List[Int] = {
    var myList = ListBuffer[Int]()
    (arr.length to 1 by -1).foreach( i => myList += arr.apply(i-1))
    myList.toList
  }

  println(f(List.range(3,10)).mkString(" "))
}
