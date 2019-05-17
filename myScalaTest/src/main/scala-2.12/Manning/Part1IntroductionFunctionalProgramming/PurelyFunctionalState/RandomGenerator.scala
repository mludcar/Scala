package Manning.Part1IntroductionFunctionalProgramming.PurelyFunctionalState

/**
  * Created by miguel.ludena on 22/04/2019.
  */
trait RNG {
  def nextInt: (Int, RNG)
}

case class SimpleRNG(seed: Long) extends RNG {
  def nextInt: (Int, RNG) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL // We use the current seed to generate a new seed
    val nextRNG = SimpleRNG(newSeed) // The next state, which is an RNG instance created from the new seed
    val n = (newSeed >>> 16).toInt // >>> is right binary shift with zero fill. The value n is the new pseudo-random integer
    (n, nextRNG) // The return value is a tuple containing both a pseudo-random integer and the next RNG state
  }
}

object RandomGenerator extends App{

  def rollDie: Int = {
    val rng = new scala.util.Random
    rng.nextInt(6) // Returns a random number from 0 to 5
  }

  (1 to 6).foreach(x => println(rollDie))

  val rng = SimpleRNG(42)
  val (n1, rng1) = rng.nextInt
  val (n2, rng2) = rng1.nextInt
  println(s"Valor 1: $n1 2: $n2")

  // EXERCISE 6.1
  // Write a function that uses RNG.nextInt to generate a random integer between 0 and Int.maxValue(inclusive). Make
  // sure to handle the corner case when nextInt returns Int.MinValue, which doen´t have a non-negative counterpart.
  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (i,r) = rng.nextInt
    (if(i > 0) i else -(i+1), r)
  }

  // EXERCISE 6.2
  // Write a function to generate a Double between 0 and 1, not including 1. Note: You can use Int.MaxValue to obtain
  // the maximum positive integer value, and you can use x.toDouble to convert an x: Int to Double
  def double(rng: RNG): (Double, RNG) = {
    val (i,r) = nonNegativeInt(rng)
    ((i / (Int.MaxValue.toDouble + 1)), r)
  }

  // EXERCISE 6.3
  // Write functions to generate a (Int, Double) pair, a (Double, Int) pair, and a (Double, Double, Double) 3-tuple.
  // You should be able to reuse the functions you´ve already written.
  def intDouble(rng: RNG): ((Int,Double), RNG) = {
    val (i, r) = rng.nextInt
    val (d, r2) = double(r)
    ((i,d), r2)
  }
  def doubleInt(rng: RNG): ((Double, Int), RNG) = {
    val ((i,d), r) = intDouble(rng)
    ((d,i), r)
  }
  def double3(rng: RNG): ((Double, Double, Double), RNG) = {
    val (d1, r1) = double(rng)
    val (d2, r2) = double(r1)
    val (d3, r3) = double(r2)
    ((d1,d2,d3), r3)
  }
  println(doubleInt(SimpleRNG(3)))

  // EXERCISE 6.4
  // Write a function to generate a list of random integers.
  def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
    def loop(c: Int, r: RNG, xs: List[Int]): (List[Int], RNG) = {
      if(c == 0) (xs, r)
      else {val (x, r2) = r.nextInt; loop(c -1, r2, x :: xs)}
    }
    loop(count, rng, List[Int]())
  }

  // CLEANER VERSION
  type Rand[+A] = RNG => (A, RNG)
  val int: Rand[Int] = _.nextInt
  println(s"Solucion: ${int(SimpleRNG(2))}")
  def unit[A](a: A): Rand[A] = rng => (a, rng)
  // La función map devuelve otra función amigo
  def map[A,B](s: Rand[A])(f: A => B): Rand[B] ={
    rng => {
      val (a, rng2) = s(rng)
      (f(a), rng2)
    }
  }
  val mapToString = map(int)(_.toString)
  println(mapToString(SimpleRNG(2))._1)

  // EXERCISE 6.5
  // Use map to reimplement double
  /*def double(rng: RNG): (Double, RNG) = {
    val (i,r) = nonNegativeInt(rng)
    ((i / (Int.MaxValue.toDouble + 1)), r)
  }*/
  def double2(rng: RNG): (Double, RNG) = map(_.nextInt)(_.toDouble)(rng)
  println(double(SimpleRNG(23)))

  // EXERCISE 6.6
  // Write the implementation of map2 based on the following signature. This function takes two actions, ra and rb,
  // and a function f for comining their results, and returns a new action that combines them:
  // def map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A,B) => C): Rand[C]
  def map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A,B) => C): Rand[C] = {
    rng => {
      val (a, r1) = ra(rng)
      val (b, r2) = rb(r1)
      (f(a,b), r2)
    }
  }
  def both[A,B](ra: Rand[A], rb: Rand[B]): Rand[(A,B)] = map2(ra,rb)((_,_))

  val randIntDouble: Rand[(Int, Double)] = both(int, double)
  val randDoubleInt: Rand[(Double, Int)] = both(double, int)

  println(randIntDouble(SimpleRNG(2)))
  println(randDoubleInt(SimpleRNG(4)))

  // EXERCISE 6.7
  // Hard: If you combine two RNG transitions, you should be able to combine a whole list of them. Implement sequence
  // for combining a list of transitions into a single transition. Use it to reimplement the ints function you wrote
  // before. For the latter, you can use the standard library function List.fill(n)(x) to make a list with x repeated
  // n times

  // In sequence, the base case of the fold is a unit action that returns the empty list. At each step in the fold, we
  // accumulate in acc and f is the current element in the list.map2(f, acc)(_ :: _) results in a value of
  // type Rand[List[A]]. We map over that to prepend(cons) the element onto the accumulated list.

  // We are using foldRight. If we used foldLeft then the values in the resulting list would appear in reverse order.
  // It would be arguably better to use foldLeft followed by reverse.


  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = {
    fs.foldRight(unit(List[A]()))((f, acc) => map2(f, acc)(_ :: _))
  }

  def ints2(count: Int): Rand[List[Int]] = sequence(List.fill(count)(int))

  println(ints2(3)(SimpleRNG(3)))

}
