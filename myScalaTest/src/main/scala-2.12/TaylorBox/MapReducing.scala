package TaylorBox

/**
  * Created by miguel.ludena on 06/09/2018.
  */
object WordCount extends App{

  val myList = List('a', 'b', 'a')
  print(myList(0))
  abstract class CodeTree
  case class Fork(left: CodeTree, right: CodeTree, chars: List[Char], weight: Int) extends CodeTree
  case class Leaf(char: Char, weight: Int) extends CodeTree

  def times(chars: List[Char]): List[(Char, Int)] = {
    val rawPair = chars.map(x => (x, chars.count(_ == x))).distinct
    rawPair
  }

  def makeOrderedLeafList(freqs: List[(Char, Int)]): List[Leaf] = freqs.map(x => new Leaf(x._1, x._2))
    .sortWith(_.weight < _.weight)

  /*
  def makeOrderedLeafList(freqs: List[(Char, Int)]): List[Leaf] = {

    val leafList = freqs.map(x => new Leaf(x._1, x._2)).sortWith(_.weight < _.weight)

    def ordered(myList: List[Leaf]): List[Leaf] = myList match {
      case List() => List[Leaf]()
      case y :: ys => if(y.weight < ys.head.weight) y :: ordered(ys) else ys.head :: ordered(y :: ys.tail)
    }
    ordered(leafList)
  }
  */

  println(makeOrderedLeafList(times(myList)))
  /*
  def times(chars: List[Char]): List[(Char, Int)] = {
	  def incr(acc: Map[Char, Int], c: Char) = {
	    val count = (acc get c).getOrElse(0) + 1
	    acc + ((c, count))
	  }

	  (Map[Char, Int]() /: chars)(incr).iterator.toList
	}
	*/
}
