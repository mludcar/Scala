package Hackerrank.Introduction

import scala.collection.mutable.ListBuffer

/**
  * Created by miguel.ludena on 24/08/2018.
  */
object FilterArray extends App{

  def f(delim:Int,arr:List[Int]):List[Int] = {
    val myList = ListBuffer[Int]()
    for(j <- arr; if j >= -100; if j <= 100; if j < delim)
      myList += j
    myList.toList
  }
}
