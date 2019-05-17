package Hackerrank.Introduction

/**
  * Created by miguel.ludena on 24/08/2018.
  */
object ListLength extends App{

  def f(arr:List[Int]):Int = {
    var i = 0
    arr.foreach{_ => i = i + 1}
    return i
  }

}
