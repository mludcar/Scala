package Hackerrank.Introduction

/**
  * Created by miguel.ludena on 26/08/2018.
  */
object SumOfOddsElements extends App{

  def f(arr:List[Int]):Int = {
    var i = 0
    for(item <- arr; if item%2 != 0) i += item
    i
  }

  println(f(List(1,2,3,4,5,6,7,8,9)))
}
