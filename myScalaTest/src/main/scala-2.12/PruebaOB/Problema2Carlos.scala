package PruebaOB

/**
  * Created by miguel.ludena on 25/04/2019.
  */
object Problema2Carlos extends App{

  val message = "The quick brown fox jumps over the lazy dog"

  def solution(message: String, k: Int): String = {

    def getBestCandidate(candidates: List[(String, Int)]): String =
      candidates.foldLeft("")((A, B) => if (A.length >= B._2) A else B._1)

    def loop(tupleWithLengths: List[(String, Int)], lastCandidate: (String, Int), candidates: List[(String, Int)]): List[(String, Int)] = {
      val partial = tupleWithLengths match {
        case h :: t => {
          val lastStr = lastCandidate._1; val lastLen = lastCandidate._2; val headStr = h._1; val headLen = h._2
          if ((lastLen == 0) && (headLen <= k)) loop(t,h,candidates:+h)
          else if (lastLen == 0) loop(t, ("",0),candidates)
          else if ((lastLen + 1 + headLen) <= k)
            loop(t, (lastStr + " " + headStr, lastLen + 1 + headLen),candidates:+(lastStr + " " + headStr, lastLen + 1 + headLen)) :::
              loop(t, ("",0),candidates)
          else loop(t , ("",0),candidates)
        }
        case _ => candidates
      }
      partial
    }

    val tupleWithLengths = message.split(" ").map(word => (word, word.length)) //= words
    getBestCandidate(loop(tupleWithLengths.toList, ("", 0), List()))
  }

  println("1"+solution(message, 39)+"1")

}
