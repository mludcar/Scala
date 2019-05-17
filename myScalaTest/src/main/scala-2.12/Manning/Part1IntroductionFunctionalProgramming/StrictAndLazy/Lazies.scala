package Manning.Part1IntroductionFunctionalProgramming.StrictAndLazy

/**
  * Created by miguel.ludena on 20/04/2019.
  */

// STREAM
sealed trait Stream[+A] {
  def toList: List[A] = {
    this match {
      case Empty => List[A]()
      case Cons(h, t) => h() :: t().toList
    }
  }
  def take(n: Int): Stream[A] = {
    this match {
      case Empty => Empty
      case Cons(h, t) if n > 1 => Stream.cons(h(), t().take(n-1))
      case Cons(h, _) if n == 1 => Stream.cons(h(), Stream.empty)
    }
  }
  def drop(n: Int): Stream[A] = {
    this match {
      case Cons(_,t) if n > 0 => t().drop(n-1)
      case _ => this
    }
  }
  def takeWhile(p: A => Boolean): Stream[A] = this match {
    case Cons(h,t) if p(h()) => Stream.cons(h(), t().takeWhile(p))
    case Cons(h,t) if !p(h()) => t().takeWhile(p)
    case _ => this
  }
  def exists(p: A => Boolean): Boolean = this match {
    case Cons(h, t) => p(h()) || t().exists(p)
    case _ => false
  }
  // The arrow => in front of the argument type B means that the function f takes its second argument by name and my
  // choose not to evaluate it
  def foldRight[B](z: => B)(f: (A, =>B) => B): B = this match {
    // if f doesn´t evaluate its second argument, the recursion never occurs
    case Cons(h,t) => f(h(), t().foldRight(z)(f))
    case _ => z
  }

  def exists2(p: A => Boolean): Boolean = foldRight(false)((a,b) => p(a) || b)

  def forAll(p: A => Boolean): Boolean = foldRight(true)((a,b) => p(a) && b)

  //def takeWhile2(p: A => Boolean): Stream[A] = foldRight(Stream.empty)((h, t) => if(p(h)) Cons(h, t) else Stream.empty)


}
case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]
// A nonempty stream consists of a head and a tail, which are both non-strict.

object Stream {
  // Smart constructor for creating a nonempty Stream
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd // We cache the head and tail as values to avoid repeated evaluation
    lazy val tail = tl
    Cons(() => head, () => tail)
  }
  // Smart constructor for creating an empty stream of a particular type
  def empty[A]: Stream[A] = Empty
  // Convenient variable-argument method for constructing a Stream for multiple elements
  def apply[A](as: A*): Stream[A] = {
    if(as.isEmpty) empty else cons(as.head, apply(as.tail: _*))
  }
}
object Lazies extends App{


  // EXERCISE 5.1
  // Write a function to convert a Stream to a List, which will force its evaluation and let you look at in the REPL.
  // You can convert to the regular List type in the standard library. You can place this and other functions that
  // operate on a Stream inside the Stream trait
  /* ESTA EN EL TRAIT
  def toList: List[A] = {
    this match {
      case Empty => List[A]()
      case Cons(h, t) => h() :: t().toList
    }
  }
   */
  println((Stream(1,2,3,4).toList))

  // EXERCISE 5.2
  // Write a function take(n) for returning the first n elements of a Stream, and drop(n) for skipping the first n
  // elements of a Stream.

  println(Stream(1,2,3,4).take(2).toList)
  println(Stream(1,2,3,4).drop(2).toList)

  // EXERCISE 5.3
  // Write a function takeWhile for returning all starting elements of a Stream that match the given predicate.
  println(Stream(1,2,3,4).takeWhile(_ % 2 == 0).toList)

  println(Stream(1,2,3,4).foldRight(0)(_ + _))
  println(Stream(1,2,3,4).exists2(_ % 2 == 0))

  // EXERCISE 5.4
  // Implement forAll, which checks that all elements in the Stream match a given predicate. Your implementation should
  // terminate the traversal as soon as it encounters a nonmatching value.
  println(Stream(1,2,3,4).forAll(_ % 2 == 0))

  // EXERCISE 5.5
  // Use foldRight to implement takeWhile
  //println(Stream(1,2,3,4).takeWhile2(_ % 2 == 0).toList)
}
