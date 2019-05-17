package Hackerrank.Recursion

import scala.collection.mutable.ArrayBuffer

/**
  * Created by miguel.ludena on 26/08/2018.
  */
object StringPermute extends App{

  var lines = io.Source.stdin.getLines.toSeq.map(_.trim)
  var arrStrings = ArrayBuffer[String]()
  arrStrings += "abcdpqrs"
  arrStrings += "az"

  def permute(arr: ArrayBuffer[String], num: Int): Unit ={

    for (i <- 0 to num - 1){
      for(j <- 0 to arr(i).length; if(j <= arr(i).length - 2)){
        print(s"${arr(i)(j+1)}${arr(i)(j)}")
      }
      println("")
    }

  }

  permute(arrStrings, 2)

}
