package PruebaOB

/**
  * Created by miguel.ludena on 29/04/2019.
  */

/*
  The output message should be as long as possible.

  This means that in some cases, the algorithm may need to crop away the entire message, outputting an empty string

  For example, given the text: "Codility We test coders"
  With K=14 the algorithm should output: "Codility We"

  Note that:
    - the output "Codility We te" would be incorrect, because the original message is cropped through the middle of a word
    - the output "Codility We " would be incorrect, because it ends with a space
    - the output "Codility We test coders" would be incorrect because it exceeds the K-character limit
    - the output "Codility" would be incorrect, because is shorter than the correct output

  Write a function
    object Solution{ def solution(message: String, k: Int): String }

  which, given a message and an integer K, returns the message cropped to no moder than K characters, as described above

  Examples:
  1. Given the message="Codility We test coders" and k= 14, the function should return "Codility We"
  2. Given message="Why not" and k=100, the function should return "Why not"
  3. Given message="The quick brown fox jumps over the lazy dog" and k=39, the function should return "The quick brown
     fox jumps over the lazy"

  Assume that:
    - k is an integer within the range [1..500]
    - message is a non-empty string containing at most 500 English alphabet letters and spaces. There are no spaces at
      the beginning or at the end of message; also there can´t be two or more consecutives spaces in messages.

  In your solution, focus on correctness. The performance of your solution will not be the focus of the assessment
 */
object P2MessageCrop extends App{

  def solution(message: String, k: Int): String = {

    def getBestCandidate(candidates: List[(String, Int)]): String ={
      println(s"getBest: ${candidates.mkString(",")}")
      candidates.foldLeft("")((A, B) => if (A.length >= B._2) A else B._1)
    }


    def loop(tupleWithLengths: List[(String, Int)], lastCandidate: (String, Int), candidates: List[(String, Int)]): List[(String, Int)] = {
      val partial = tupleWithLengths match {
        case h :: t => {
          val lastStr = lastCandidate._1; val lastLen = lastCandidate._2; val headStr = h._1; val headLen = h._2
          println()
          println(s"INICIO)tupleWithLengths: ${tupleWithLengths.mkString(",")} lastCandidate: $lastCandidate candidates: ${candidates.mkString(",")}")
          if ((lastLen == 0) && (headLen <= k)) {
            println("Caso que la última palabra sea igual a 0 o que la actual sea menor al tamaño")
            println(s"head => $h lastCandidate => $lastCandidate")
            println(s"Llamamos con tupleWithLengths: $t lastCandidate: $h candidates: ${(candidates:+h).mkString(",")}")
            loop(t,h,candidates:+h)
          }
          else if (lastLen == 0) {
            println("Caso que el último candidato sea la cadena vacía")
            println(s"Llamamos con tupleWithLengths: $t lastCandidate: (${""},0) candidates: $candidates")
            loop(t, ("",0),candidates)
          }
          else if ((lastLen + 1 + headLen) <= k){
            println("Caso en que todavia no se ha llegado a k")
            println(s"Parte1) Llamamos con tupleWithLengths: $t lastCandidate: ${(lastStr + " " + headStr, lastLen + 1 + headLen)} candidates: ${candidates:+(lastStr + " " + headStr, lastLen + 1 + headLen)}")
            println(s"Parte2) Llamamos con tupleWithLengths: $t lastCandidate: (${""},0) candidates: $candidates")
            loop(t, (lastStr + " " + headStr, lastLen + 1 + headLen),candidates:+(lastStr + " " + headStr, lastLen + 1 + headLen)) :::
              loop(t, ("",0),candidates)}
          else {
            println("Caso del Else")
            println(s"Llamamos con tupleWithLengths: $t lastCandidate: (${""},0) candidates: $candidates")
            loop(t , ("",0),candidates)
          }
        }
        case _ => {
          println()
          println("CASO DE PARADA")
          println(s"candidates ${candidates.mkString(",")}")
          candidates
        }
      }
      partial
    }

    val tupleWithLengths = message.split(" ").map(word => (word, word.length)) //= words
    getBestCandidate(loop(tupleWithLengths.toList, ("", 0), List()))
  }

  println(solution("Codility We test coders", 14))
}
