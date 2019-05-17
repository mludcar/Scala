package ScalaCourse1.Week3

/**
  * Created by miguel.ludena on 04/09/2018.
  */
object AbstractClass extends App{
  val t1 = new NonEmpty(3,  Empty,  Empty)
  val t2 = t1 incl 4
  println(t1.toString)
  println(t2.toString)
}

abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

object Empty extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x,  Empty,  Empty)
  override def toString: String = "."
  def union(other: IntSet): IntSet = other
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean = {
    if(x < elem) left contains x
    else if(x > elem) right contains x
    else true
  }

  def incl(x: Int): IntSet = {
    if(x < elem) new NonEmpty(elem, left incl x, right)
    else if(x > elem) new NonEmpty(elem, left, right incl x)
    else this
  }

  override def toString: String = "{" + left + elem + right + "}"

  def union(other: IntSet): IntSet = {
    ((left union right) union other) incl elem
  }
}
