package Collections

/**
  * Created by miguel.ludena on 11/09/2018.
  */

/** Converts the word into its character occurrence list.
  *
  *  Note: the uppercase and lowercase version of the character are treated as the
  *  same character, and are represented as a lowercase character in the occurrence list.
  *
  *  Note: you must use `groupBy` to implement this method!
  */

object AnagramTest extends App{
  type Word = String
  type Sentence = List[Word]
  type Occurrences = List[(Char, Int)]

  val myWord: Word = "Abanibiaboebe"
  val yourWord: Word = "aboebe"
  val mySentence: Sentence = List(myWord, yourWord)


  val myList = List("Every", "student", "likes", "Scala").groupBy(_.length)

  def wordOccurrences(w: Word): Occurrences = {
    w.toList.groupBy(c => c.toLower).map{case (k, v) =>(k, v.length)}.toList.sorted
  }
  println(wordOccurrences(myWord))

  def sentenceOccurrences(s: Sentence): Occurrences = {
    if(s.isEmpty) Nil
    else s.flatMap(wordOccurrences(_)).groupBy(_._1).map(x => (x._1, x._2.map(_._2).sum)).toList.sorted
  }

  val a = mySentence.flatMap(wordOccurrences(_)).groupBy(_._1).map(x => (x._1, x._2.map(_._2).sum))



  println(mySentence.flatMap(wordOccurrences(_)).groupBy(_._1).map(x => (x._1, x._2)))

  //println(sentenceOccurrences(mySentence))
}
