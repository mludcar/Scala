package Hackerrank.Introduction

/**
  * Created by miguel.ludena on 24/08/2018.
  */
object ArrayOfNElements extends App{

  def f(num:Int) : List[Int] = {
    var l = scala.collection.mutable.ListBuffer.empty[Int]
    (1 to num).foreach(i=> {
      l += i
    })
    l.toList
  }

  println(f(10).mkString(" "))
}
