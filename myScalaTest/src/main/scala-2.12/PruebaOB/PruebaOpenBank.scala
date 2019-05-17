package PruebaOB

/**
  * Created by miguel.ludena on 25/04/2019.
  */
object PruebaOpenBank extends App{
  val n = 3
  val c = 7

  val mul = n*c

  // Sol 1
  val a = mul.toBinaryString.count(_ ==1)
  a
  //println(mul.toBinaryString.count(_ == '1'))

  val msg = "Codility We test coders"

  val lengthMsg = msg.length


    def solution(message: String, k: Int): String = {
      val arrayMsg = message.split(" ")
      println(arrayMsg.mkString(","))
      val candidate = msg.take(14)
      var trial = ""
      var first = ""
      var before = ""
      if(message.length < k) message
      else if(msg.take(k).split(" ").last == candidate.split(" ").last) candidate.split(" ").mkString(" ")
      else candidate.split(" ")
      ""
    }

  /*def solution(message: String, k: Int): String = {
    val tupleWithLengths = message.split(" ").map(word => (word,word.length)) //= words
    getBetterCandidate(auxRec(tupleWithLengths.toList,k,("",0),List()[(String,Int)]))
  }

  def auxRec(tupleWithLengths:List[(String,Int)], maxLength: Int, lastCandidate: (String,Int), candidates: List[(String,Int)]) : List[(String,Int)] = {
    tupleWithLengths match {
      val candidateLength = lastCandidate._2
      case h::t =>{
        if (candidateLength + 1 + h._2 <= maxLength)
          auxRec(t,maxLength,lastCandidate + " " + h._1, candidates :: )
      }
      case _ => {

      }
    }

  }

  def getBetterCandidate(candidates: List[(String, Int)]) : String = {
    ""
  }*/

  /*def solution(message: String, k: Int): String = {
    val rawMessage = message.split(" ")
    val littlerawMessage = message.take(k).split(" ")
    if(rawMessage.contains(littlerawMessage.last)) littlerawMessage.mkString(" ")
    else littlerawMessage
  }

  def getBestCandidate(candidates: List[(String, Int)]): String = {
    candidates.foldLeft("")((A, B) => if (A.length > B._2) A else B._1)
  }*/


}
