package Hackerrank.Introduction

import scala.collection.mutable.ListBuffer

/**
  * Created by miguel.ludena on 24/08/2018.
  */
object ListUpdate extends App{



  def f(arr:List[Int]):List[Int] = {
    var myList = ListBuffer[Int]()
    arr.foreach{ i =>
      if(i < 0) myList += i * -1
      else myList += i
    }
    myList.toList
  }

}
