package ScalaAdvanced.exercises

/**
  * Created by miguel.ludena on 06/04/2019.
  */
trait MySet[A] extends (A => Boolean){
  /*
  Exercise - a functional set
   */
  def apply(elem: A): Boolean = contains(elem)
  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit
  def isEmpty: Boolean

  /*
  Exercise
  - removing an element
  - intersection with another set
  - difference with another set
   */
  def -(elem: A): MySet[A]
  def &(anotherSet: MySet[A]): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A]

  /*
  Exercise - implement a unary_! = NEGATION of a set
   */
  def unary_! : MySet[A]
}

class EmptySet[A] extends MySet[A] {
  def contains(elem: A): Boolean = false
  def +(elem: A): MySet[A] = new NonEmptySet(elem, this)
  def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  def map[B](f: A => B): MySet[B] = new EmptySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
  def filter(predicate: A => Boolean): MySet[A] = this
  def foreach(f: A => Unit): Unit = ()
  def isEmpty = true

  def -(elem: A): MySet[A] = new EmptySet[A]
  def &(anotherSet: MySet[A]): MySet[A] = new EmptySet[A]
  def --(anotherSet: MySet[A]): MySet[A] = new EmptySet[A]

  def unary_! : MySet[A] = new MySet[A] {override def flatMap[B](f: (A) => MySet[B]) = ???

    override def filter(predicate: (A) => Boolean) = ???

    override def unary_! = ???

    override def &(anotherSet: MySet[A]) = ???

    override def --(anotherSet: MySet[A]) = ???

    override def +(elem: A) = ???

    override def contains(elem: A) = ???

    override def -(elem: A) = ???

    override def isEmpty = ???

    override def ++(anotherSet: MySet[A]) = ???

    override def foreach(f: (A) => Unit) = ???

    override def map[B](f: (A) => B) = ???
  }
}

class AllInclusiveSet[A] extends MySet[A] {
  override def flatMap[B](f: (A) => MySet[B]) = ???
  override def filter(predicate: (A) => Boolean) = ???
  override def unary_! = ???
  override def &(anotherSet: MySet[A]) = ???
  override def --(anotherSet: MySet[A]) = ???
  override def +(elem: A) = this
  override def contains(elem: A) = true

  override def -(elem: A) = ???

  override def isEmpty = false

  override def ++(anotherSet: MySet[A]) = ???

  override def foreach(f: (A) => Unit) = ???

  override def map[B](f: (A) => B) = ???
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A]  {
  def contains(elem: A): Boolean = if(head == elem) true else tail.contains(elem)
  def +(elem: A): MySet[A] = if(this.contains(elem)) this else new NonEmptySet[A](elem, this)
  def ++(anotherSet: MySet[A]): MySet[A] = tail ++ anotherSet + head

  def map[B](f: A => B): MySet[B] = tail.map[B](f) + f(head)
  def flatMap[B](f: A => MySet[B]): MySet[B] = f(head) ++ tail.flatMap(f)
  def filter(predicate: A => Boolean): MySet[A] = if(predicate(head)) tail.filter(predicate) + head else tail.filter(predicate)
  def foreach(f: A => Unit): Unit = {f(head); tail.foreach(f)}
  def isEmpty: Boolean = false

  def -(elem: A): MySet[A] = if(!this.contains(elem)) this else this.filter(elem != _)
  // Version extendida -> def &(anotherSet: MySet[A]): MySet[A] = filter(x => anotherSet.contains(x))
  // Como la función apply de MySet es un contains
  // def &(anotherSet: MySet[A]): MySet[A] = filter( x => anotherSet.contains(x))
  // Entonces:
  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  override def unary_! = ???

}

object MySet {
  def apply[A](values: A*): MySet[A] = {
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
      if(valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)

    buildSet(values.toSeq, new EmptySet[A])
  }
}

object MySetPlayground extends App {
  val s = MySet(1,2,3,4)
  val r = MySet(1,2)
  //s.foreach(println(_))
  //s + 5 foreach(println(_))
  //s.-(4).foreach(println(_))
  //println(s.contains(9))
  //s.filter(_ % 2 == 0).foreach(println(_))
  s.--(r).foreach(println(_))
}
