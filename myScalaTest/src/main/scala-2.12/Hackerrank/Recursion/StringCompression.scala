package Hackerrank.Recursion

import scala.annotation.tailrec

/**
  * Created by miguel.ludena on 27/08/2018.
  */
object StringCompression extends App{

  val strudel = io.Source.stdin.getLines().mkString("")

  def compressor(str: String): Unit = {

    compressorAux(str, 1)

    def compressorAux(str: String, acc: Int): Unit = {
      if(str.length == 1 && acc == 1) print(str.head)
      else if(str.length == 1 && acc != 1) print(acc)
      else if(str.head == str.tail.head && acc == 1){print(str.head);compressorAux(str.tail, acc + 1)}
      else if(str.head == str.tail.head)compressorAux(str.tail, acc + 1)
      else if (str.head != str.tail.head && acc > 1) {print(acc); compressorAux(str.tail, 1)}
      else{print(str.head);compressorAux(str.tail, 1)}
    }
  }

  compressor(strudel)

}
