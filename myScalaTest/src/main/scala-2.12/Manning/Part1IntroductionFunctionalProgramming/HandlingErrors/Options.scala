package Manning.Part1IntroductionFunctionalProgramming.HandlingErrors

/**
  * Created by miguel.ludena on 19/04/2019.
  */

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B]  = this match { // Apply f if the Option is not None
    case None => None
    case Some(a) => Some(f(a))
  }
  def flatMap[B](f: A => Option[B]): Option[B] =  { // Apply f, which may fail, to the Option if not None
    map(f) getOrElse None
  }
  def getOrElse[B >: A](default: => B): B = this match { // The B >: A says that B type parameter must be a supertype of A
    case None => default
    case Some(a) => a
  }

  def orElse[B >: A](ob: => Option[B]): Option[B] = this match { // Don´t evaluate ob unless needed
    case None => ob
    case _ => this
  }
  def filter(f: A => Boolean): Option[A] = { // Convert Some to None if the value doesn´t satisfy f
    flatMap(a => if(f(a)) Some(a) else None)
  }
}

// EXERCISE 4.1
// Implement all of the preceding functions on Option. As you implement each function, try to think about what it means
// and in what situations you’d use it. We’ll explore when to use each of these functions next. Here are a few hints for
// solving this exercise:
// * It’s fine to use pattern matching, though you should be able to implement all the functions besides map and
// getOrElse without resorting to pattern matching.
// * For map and flatMap, the type signature should be enough to determine the implementation.
// * getOrElse returns the result inside the Some case of the Option, or if the Option is None, returns the given
//   default value.
// * orElse returns the first Option if it’s defined; otherwise, it returns the second Option.
case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object Options extends App{

  def mean(xs: Seq[Double]): Option[Double] = {
    if(xs.isEmpty) None
    else Some(xs.sum / xs.length)
  }

  // EXERCISE 4.2
  // Implement the variance function in terms of flatMap. if the mean of a sequence is m, the variance is the mean of
  // math.pow(x-m, 2) for each element x in the sequence.
  def variance(xs: Seq[Double]): Option[Double] = mean(xs).flatMap(m => mean(xs.map(x => math.pow(x-m,2))))
  println(variance(Seq(1,2,3,4,5)))

  // OPTION COMPOSITION, LIFTING AND WRAPPING EXCEPTION-ORIENTED APIs
  // It may be easy to jump to the conclusion that once we start using Option, it infects our entire code base. One can
  // imagine how any callers of methods that take or return Option will have to be modified to handle either Some or
  // None. But this doesn’t happen, and the reason is that we can lift ordinary functions to become functions that
  // operate on Option.
  // For example, the map function lets us operate on values of type Option[A] using a function of type A => B,
  // returning Option[B]. Another way of looking at this is that map turns a function f of type A => B into a function
  // of type Option[A] => Option[B]. Let’s make this explicit:
  def lift[A,B](f: A => B): Option[A] => Option[B] = _ map f
  val liftedAbs = lift(math.abs)
  println(liftedAbs(Some(32)))

  // USING OPTION
  def insuranceRateQuote(age: Int, tickets: Int): Double = math.Pi * tickets + age

  // BAD Type mismatch
  /*def parseInsuranceRateQuote(age: String, nummerOfSpeedingTickets : String) : Option[Double] = {
    val optAge: Option[Int] = Try(age.toInt) // The toInt method is available on any String
    val optTickets: Option[Int] = Try(nummerOfSpeedingTickets.toInt)
    insuranceRateQuote(optAge, optTickets) // Doesn´t type check!
  }*/

  // We accept the A argument non-strictly,so we can catch any exceptions that occur while evaluating a and convert
  // them to None.
  def Try[A](a: => A): Option[A] = {
    try Some(a)
    catch {case e: Exception => None} // This discards information about the error.
  }

  // EXERCISE 4.3
  // Write a generic function map2 that combines two Option values using a binary function. If either Option value is
  // None, then the return value is too.
  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A,B) => C): Option[C] =
    for {
      aa <- a
      bb <- b
    } yield f(aa, bb)

  def parseInsuranceRateQuote(age: String, nummerOfSpeedingTickets : String) : Option[Double] = {
    val optAge: Option[Int] = Try(age.toInt)
    val optTickets: Option[Int] = Try(nummerOfSpeedingTickets.toInt)
    map2(optAge, optTickets)(insuranceRateQuote)
  }

  println(parseInsuranceRateQuote("34", "32"))

  // EXERCISE 4.4
  // Write a function sequence that combines a list of Options into one Option containing a list of all the Some values
  // in the original list. If the original list contains None even once, the result of the function should be None;
  // otherwise the result should be Some with a list of all the values.
  def sequence[A](a: List[Option[A]]): Option[List[A]] = a match {
    case Nil => Some(Nil)
    case x :: xs => x.flatMap(head => sequence(xs).map(tail => head :: tail))
  }
  val b = sequence(List(Some("a")))
  println(b)

  // Sometimes we’ll want to map over a list using a function that might fail, returning None if applying it to any
  // element of the list returns None. For example, what if we have a whole list of String values that we wish to parse
  // to Option[Int]? In that case, we can simply sequence the results of the map:
  def parseInts(a: List[String]): Option[List[Int]] = sequence(a.map(i => Try(i.toInt)))

  // Unfortunately, this is inefficient, since it traverses the list twice, first to convert each String to an
  // Option[Int], and a second pass to combine these Option[Int] values into an Option[List[Int]]. Wanting to sequence
  // the results of a map this way is a common enough occurrence to warrant a new generic function, traverse:

  // EXERCISE 4.5
  // Implement this function. It´s straightforward to do using map and sequence, but try for a more efficient
  // implementation that only looks at the list once. In fact, implement sequence in terms of traverse
  def traverse[A,B](a: List[A])(f: A => Option[B]): Option[List[B]] = a.foldRight[Option[List[B]]](Some(Nil))((head, tail) => map2(f(head), tail)(_ :: _))
  def sequenceViaTraverse[A](a: List[Option[A]]): Option[List[A]] = traverse(a)(x => x)
  println(sequenceViaTraverse(List(Some("a"))))

  // EITHER DATA TYPE
  // One thing you may have noticed with Option is that it doesn’t tell us anything about what went wrong in the case
  // of an exceptional condition. All it can do is give us None, indicating that there’s no value to be had. But
  // sometimes we want to know more. For example, we might want a String that gives more information, or if an exception
  // was raised, we might want to know what that error actually was.
  // We can craft a data type that encodes whatever information we want about failures. Sometimes just knowing whether
  // a failure occurred is sufficient, in which case we can use Option; other times we want more information
  //sealed trait Either[+E, +A]
  case class Left[+E](value: E) extends Either[E, Nothing]
  case class Right[+A](value: A) extends Either[Nothing, A]

  // Either has only two cases, just like Option. The essential difference is that both cases carry a value. The Either
  // data type represents, in a very general way, values that can be one of two things. We can say that it’s a disjoint
  // union of two types. When we use it to indicate success or failure, by convention the Right constructor is reserved
  // for thesuccess case (a pun on “right,” meaning correct), and Left is used for failure. We’ve given the left type
  // parameter the suggestive name E (for error).

  def safeMean(xs: IndexedSeq[Double]): Either[String, Double] = {
    if(xs.isEmpty) Left("mean of empty list")
    else Right(xs.sum / xs.length)
  }

  def safeDiv(x: Int, y: Int): Either[Exception, Int] = {
    try Right(x/y)
    catch { case e: Exception => Left(e)}
  }

  def safeTry[A](a: => A): Either[Exception, A] = {
    try Right(a)
    catch { case e: Exception => Left(e)}
  }

  // EXERCISE 4.6
  // Implement versions of map, flatMap, orElse and map2 on Either that operate Right value
  trait Either[+E, +A] {
    def map[B](f: A => B): Either[E, B] = this match {
      case Right(a) => Right(f(a))
      case Left(e) => Left(e)
    }
    def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = this match {
      case Right(a) => f(a)
      case Left(e) => Left(e)
    }

    def orElse[EE >: E,B >: A](b: => Either[EE, B]): Either[EE, B] = this match {
      case Left(e) => b
      case _ => this
    }

    def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] = for { a <- this; b1 <- b} yield f(a,b1)
  }

  def safeParseInsuranceRateQuote(age: String, numberOfSpeedingTickets: String): Either[Exception,Double] =
    for {
      a <- safeTry { age.toInt }
      tickets <- safeTry { numberOfSpeedingTickets.toInt }
    } yield insuranceRateQuote(a, tickets)

  println(safeParseInsuranceRateQuote("calvario", "15"))

  // EXERCISE 4.7
  // Implement sequence and traverse for Either. These should return the first error that´s encountered, if there´s one
  def sequence[E, A](es: List[Either[E, A]]): Either[E, List[A]] = safeTraverse(es)(identity _)

  def safeTraverse[E, A, B](as: List[A])(f: A => Either[E, B]): Either[E, List[B]] = {
    as.foldRight[Either[E,List[B]]](Right(Nil))((data, acc) => f(data).map2(acc)(_ :: _))
  }
}
