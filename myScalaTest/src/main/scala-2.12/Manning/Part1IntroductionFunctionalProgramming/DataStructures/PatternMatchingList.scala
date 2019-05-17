package Manning.Part1IntroductionFunctionalProgramming.DataStructures

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

object PatternMatchingList extends App{

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

  // Again, placing f in its own argument group after as anz z let´s type inference determine that input types to f
  def foldRight[A,B](as: List[A], z: B)(f: (A,B) => B): B = as match {
    case Nil => z
    case Cons(x, xs) => f(x, foldRight(xs, z)(f))
  }

  def sum2(ns: List[Int]) = foldRight(ns, 0)((x, y) => x + y)
  def product2(ns: List[Double]) = foldRight(ns, 1.0)(_ * _)

  // EXERCISE 3.9
  // Compute the length of a list using foldRight
  def length[A](as: List[A]): Int = foldRight(as, 0)((_, y) => y + 1)
  println(length(List(1,2,3,4)))

  // EXERCISE 3.10
  // Our implementation of foldRight is not tail-recursive and will result in a StackOverflowError for large lists
  // (we say it´s not stack-safe). Convince yourself that this is the case, and then write another general
  // list-recursion function, foldLeft, that is tail-recursive, using the techniques we discussed in the previous chapter.
  def foldLeft[A,B](as: List[A], z: B)(f: (B, A) => B): B = as match {
    case Nil => z
    case Cons(x, xs) => foldLeft(xs, f(z, x))(f)
  }

  // EXERCISE 3.11
  // Write sum, product, and a function to compute the length of a list using foldLeft
  def sum3(ns: List[Int]) = foldLeft(ns, 0)(_ + _)
  def product3(ns: List[Double]) = foldLeft(ns, 1.0)(_ * _)
  def length2[A](as: List[A]) = foldLeft(as, 0)((x, _) => x + 1)
  println(sum3(List(1,2,3)))
  println(product3(List(1,2,3,4)))
  println(length2(List(1,2,3,4)))

  // EXERCISE 3.12
  // Write a function that returns the reverse of a list(given List) it returns List(3,2,1). See if you can write it
  // using a fold
  def reverse[A](ns: List[A]): List[A] = foldLeft(ns, List[A]())((acc, data) => Cons(data, acc))
  println(reverse(List(1,2,3)))

  // EXERCISE 3.13
  // Hard: Can you write foldLeft in terms of foldRight? How about the other way around? Implementing foldRight via
  // foldLeft is useful because it lets us implement foldRight tail-recursively, which means it works even for large
  // lists without overflowing the stack
  def foldRightViaFoldLeft[A,B](as: List[A], z: B)(f: (B,A) => B): B = foldLeft(reverse(as), z)(f)

  def foldLeftViaFoldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B = foldRight(reverse(as), z)(f)

  println(foldRightViaFoldLeft(List(1,2,3), 0)((acc, data) => {
    println(s"foldRightViaFoldLeft) En este momento data: $data acc: $acc")
    acc + data
  }))

  println(foldLeftViaFoldRight(List(1,2,3), 0)((data, acc) => {
    println(s"foldRightViaFoldLeft) En este momento data: $data acc: $acc")
    acc + data
  }))

  // EXERCISE 3.14
  // Implement append in terms of either foldLeft or foldRight
  // def foldLeft[A,B](as: List[A], z: B)(f: (B, A) => B): B
  // def foldRight[A,B](as: List[A], z: B)(f: (A,B) => B): B
  def appendViaFoldRight[A](l: List[A], r: List[A]): List[A] = foldRight(l, r)((data, acc) => Cons(data, acc))
  def appendViaFoldLeft[A](l: List[A], r: List[A]): List[A] = foldLeft(reverse(l), r)((acc, data) => Cons(data, acc))

  println(appendViaFoldLeft(List(1,2,3), List(4,5,6)))

  // EXERCISE 3.15
  // Hard: Write a function that concatenates a list of lists into a single list. Its runtime should be linear in the
  // total length of all lists. Try to use functions we have already defined.
  def concatenate[A](list: List[List[A]]): List[A] = foldRight(list, List[A]())(appendViaFoldRight)

  println(concatenate(List(List(1,2,3), List(4,5,6))))

  // EXERCISE 3.16
  // Write a function that transforms a list of integers by adding 1 to each element.
  // (Reminder: this should be a pure function that returns a new list)
  def adder(list: List[Int]): List[Int] = foldRight(list, List[Int]())((data, acc) => Cons(data + 1, acc))

  println(adder(List(1,2,3)))

  // EXERCISE 3.17
  // Write a function that turns each value in a List[Double] into a String. You can use the expression d.toString to
  // convert some d: Double to a String
  def doublesToStrings(list: List[Double]): List[String] = foldRight(list, List[String]())((data, acc) => Cons(data.toString, acc))

  println(doublesToStrings(List(1.0,2.0,3.0)))

  // EXERCISE 3.18
  // Write a function map that generalizes modifying each element in a list while maintaining the structure of the list.
  def map[A,B](as: List[A])(f: A => B): List[B] = foldRight(as, List[B]())((data, acc) => Cons(f(data), acc))

  println(map(List("hola", "que", "tal", "soy", "el", "chico", "de", "las", "poesías"))(_.toUpperCase))

  // EXERCISE 3.19
  // Write a function filter that removes elements from a list unless they satisfy a given predicate. Use it to remove
  // all odd numbers from a List[Int]
  def filter[A](as: List[A])(f: A => Boolean): List[A] = foldRight(as, List[A]())((data, acc) => data match {
    case x if f(x) == true => Cons(data, acc)
    case _ => acc
  } )
  println(filter(List(1,2,3,4))(x => x % 2 == 0))

  // EXERCISE 3.20
  // Write a function flatMap that works like map except that the function given will return a list instead of a single
  // result, and that list should be inserted into the final resulting list.
  // For instance, flatMap(List(1,2,3))(i => List(i,i)) should result in List(1,1,2,2,3,3)
  def flatMap[A,B](as: List[A])(f: A => List[B]): List[B] = foldRight(as, List[B]())((data, acc) => appendViaFoldRight(f(data), acc))
  println(flatMap(List(1,2,3))(i => List(i,i)))

  // EXERCISE 3.21
  // Use flatMap to implement filter
  def filterByflatMap[A](as: List[A])(f: A => Boolean): List[A] = flatMap(as)(a => if(f(a)) List(a) else Nil)
  println(filterByflatMap(List(1,2,3, 4, 5))(x => x % 2 == 0))

  // EXERCISE 3.22
  // Write a function that accepts two lists and constructs a new list by adding corresponding elements. For example,
  // List(1,2,3) and List(4,5,6) become List(5,7,9)
  def correspondingElements(as: List[Int] , ns: List[Int]): List[Int] = (as, ns) match {
    case (Nil, _) => Nil
    case (_, Nil) => Nil
    case (Cons(x, xs), Cons(y, ys)) => Cons(x + y, correspondingElements(xs, ys))
  }
  println(correspondingElements(List(1,2,3), List(4,5,6)))

  // EXERCISE 3.23
  // Generalize the function you just wrote so that it´s not specific to integers or addition. Name your generalized
  // function zipWith
  def zipWith[A, B](as: List[A], ns: List[A])(f: (A,A) => B): List[B] = (as, ns) match {
    case (Nil , _) => Nil
    case (_ , Nil) => Nil
    case (Cons(x, xs), Cons(y, ys)) => Cons(f(x,y), zipWith(xs, ys)(f))
  }
  println(zipWith(List(1,2,3), List(4,5,6))(_ + _))

  // EXERCISE 3.24
  // Hard: As an example, implement hasSubsequence for checking whether a List contains another List as a subsequence.
  // For instance, List(1,2,3,4) would have List(1,2), List(2,3), and List(4) as subsequences, among others. You may
  // have some difficulty finding a concise purely functional implementation that is also efficient. That’s okay.
  // Implement the function however comes most naturally. We’ll return to this implementation in chapter 5 and
  // hopefully improve on it. Note: Any two values x and y can be compared for equality in Scala using the
  // expression x == y.
  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = (sup, sub) match {
    case (_, Nil) => true
    case (Nil, _) => false
    case (Cons(supH, supT), Cons(subH, subT)) => if (supH == subH) hasSubsequence(supT, subT) else hasSubsequence(supT, sub)
  }
  println(hasSubsequence(List(1,2,3,4), List(1,2)))



}
