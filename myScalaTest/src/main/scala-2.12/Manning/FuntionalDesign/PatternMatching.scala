package Manning.FuntionalDesign

/**
  * Created by miguel.ludena on 10/04/2019.
  */

sealed trait List[+A] //List data type, parameterized on a type, A
case object Nil extends List[Nothing] // A List data constructor representing the empty List
case class Cons[+A](head: A, tail: List[A]) extends List[A] // Another data constructor, representing nonempty lists.
                                                            // Note that tail is another List[A] which may be Nil or another Cons

//List companion object. Contains functions for creating and working with lists
object List {

  // A function that uses pattern matching to add up a list of integers
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0 // The sum of this empty list is 0
    case Cons(x, xs) => x + sum(xs) // The sum of a list starting with x plus the sum of the rest of the list
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] = //Variadic function syntax
    if(as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}

// NOTES
// toSting method
// Scala generates a default def toString: String method dor any case class or case object, which can be convenient for
// debugging purposes. But note that the generated toString will be naively recursive and will cause stack overflow when
// printing long lists, so you may wish to provide a different implementation.

// Variadic functions
// The function apply in the object List is a variadic function, meaning it accepts zero or more arguments of type A:

//    def apply[A](as: A*): List[A] =
//      if(as.isEmpty) Nil
//      else Cons(as.head, apply(as.tail: _*))

// For data types, it´s a commmon idiom to have a variadic apply method in the companion object to conveniently
// construct instances of the data type. By calling this function apply and placing it in the companion object, we can
// invoke it with syntax like List(1,2,3,4) or List("hi", "bye"), with as many values as we want separated by commas( we
// sometimes call this the list literal or just literal syntax).

// Variadic functions are just providing a little syntactic sugar for creating and passing a Seq of elements explicitly.
// Seq is the interface in Scala´s collection library implemented by sequence-like data structures such as lists, queues
// and vectors. The argument as will be bound to a Seq[A], which has the functions head(return of the first element) and
// tail (returns all elements but the first). The special _* type notation allows us to pass a Seq to a variadic method.

object PatternMatching extends App{

  // Examples
  val ex1: List[Double] = Nil
  val ex2: List[Int] = Cons(1, Nil)
  val ex3: List[String] = Cons("a", Cons("b", Nil))

  // Pattern Matching - examples
  val pm1 = List(1,2,3) match { case _ => 42} // 42
  val pm2 = List(1,2,3) match { case Cons(h, _) => h} // 1
  val pm3 = List(1,2,3) match { case Cons(_, t) => t} // List(2,3)
  // val pm4 = List(1,2,3) match { case Nil => 42} // MatchError at runtime

  println(ex1.toString)

  // EXERCISE 3.1
  // What will be the result of the following match expression
  val x = List(1,2,3,4,5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + List.sum(t)
    case _ => 101
  }
  // x = 3

  // EXERCISE 3.2
  // Implement the funtion tail for removing the first element of a List. Note that the function takes constant time.
  // Note that the function takes constant time. What are different choices you could make in your implementation if
  // the List is Nil?
  def tail[A](list: List[A]): List[A] = list match {
    case Nil => Nil
    case Cons(x, xs) => xs
  }
  println(tail(List(1,2,3)))

  // EXERCISE 3.3
  // Using the same idea, implement the function setHead for replacing the first element of a List with a different value
  def replace[A](list: List[A], first: A): List[A] = list match {
    case Nil => Nil
    case Cons(x, xs) => Cons(first, xs)
  }
  println(replace(List(1,2,3), 7))

  // EXERCISE 3.4
  // Generalize tail to the function drop, which removes the first n elements from a list. Note that this function takes
  // time proportional only to the number of elements being dropped- we don´t need to make a copy of the entire List
  def drop[A](l: List[A], n: Int): List[A] = n match {
    case n if n == 0 => l
    case n if n > 0 => drop(tail(l), n - 1)
  }
  println(drop(List(1,2,3), 1))

  // EXERCISE 3.5
  // Implement dropWhile, which removes elements from the List prefix as long as they match a predicate
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nil => Nil
    case Cons(x, xs) => if(f(x)) dropWhile(tail(l), f) else Cons(x, dropWhile(tail(l), f))
  }
  println(dropWhile(List(1,2,3,4), (x:Int) => x % 2 == 0))

  // Higher-Order functions like dropWhile will often be passed anonymous functions.
  // def dropWhile[A](l: List[A], f: A => Boolean): List[A]
  // When we call it with an anonymous function for f, we have to specify the type of its argument, here named x:

  val xs: List[Int] = List(1,2,3,4,5)
  val ex4 = dropWhile(xs, (x: Int) => x < 4)
  println(ex4)

  // The value of ex4 is List(4,5)
  // It´s a little unfortunate that we need to state that the type of x is Int. The first argument to dropWhile is a
  // List[Int], so the function in the second argument must accept an Int. Scala can infer this fact if we group
  // dropWhile into two arguments lists:

  def dropWhileCurried[A](as: List[A])(f: A => Boolean): List[A] = as match {
    case Cons(h,t) if f(h) => dropWhileCurried(t)(f)
    case _ => as
  }

  val xs1: List[Int] = List(1,2,3,4,5)
  val ex5 = dropWhileCurried(xs)(x => x < 4) // tb válido => val ex5 = dropWhileCurried(xs)(_ < 4)
  println(ex5)

  // The main reason for grouping the arguments this way is to assist with type inference.
  // More generally, when a function definition contains multiple arguments groups, type information flows from left to
  // right across these arguments groups. Here, the first argument group fixes the type parameter A of dropWhile to Int,
  // so the annotation on x => x < 4 is not required.
  // We´ll often group and order our function arguments into multiple argument lists to maximize type inference.

  // EXERCISE 3.6
  // Not everything works out so nicely. Implement a function, init, that returns a List consisting of all but the last
  // element of a List. So, given List(1,2,3,4), init will return List(1,2,3). Why can´t this function be implemented
  // in constant time like tail?
  def init[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(x, Cons(y, Nil)) => Cons(x, Nil)
    case Cons(x, xs) => Cons(x, init(tail(l)))
  }
  println(init(List(1,2,3,4)))
}
