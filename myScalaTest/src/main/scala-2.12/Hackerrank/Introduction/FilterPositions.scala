package Hackerrank.Introduction

import scala.collection.mutable.ListBuffer

/**
  * Created by miguel.ludena on 24/08/2018.
  */
object FilterPositions extends App{

  def f(arr:List[Int]):List[Int] = {
    var myList = ListBuffer[Int]()
    var i = 1
    for(j <- arr){
      if(i % 2 == 0) myList +=j
      i = i+1
    }
    myList.toList
  }

  println(f(List(1,2,3,4,5,6)).mkString("-"))
}
