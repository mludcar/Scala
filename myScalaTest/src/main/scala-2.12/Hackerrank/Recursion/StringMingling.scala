package Hackerrank.Recursion

/**
  * Created by miguel.ludena on 26/08/2018.
  */
object StringMingling extends App{

  var lines = io.Source.stdin.getLines.toSeq.map(_.trim)
  val str1 = lines(0)
  val str2 = lines(1)

  val charArr1 = str1.toCharArray
  val charArr2 = str2.toCharArray

  mingler(charArr1, charArr2)

  def mingler(input1: Array[Char], input2: Array[Char]): Unit = {
    if(input1.length == 1) print(s"${input1.head}${input2.head}")
    else {
      print(s"${input1.head}${input2.head}")
      mingler(input1.tail, input2.tail)
    }
  }

}
