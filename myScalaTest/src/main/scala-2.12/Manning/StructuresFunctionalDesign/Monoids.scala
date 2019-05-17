package Manning.StructuresFunctionalDesign

import scala.sys.Prop

/**
  * Created by miguel.ludena on 11/04/2019.
  */

// MONOIDS
// A monoid consists of the following;
// - Some type A (Unit)
// - An associative binary operation, op, that takes two values of type A and combines them into one:
//   op(op(x,y), z) = op(x, op(y,z)) for any choice of x: A, y: A, z: A
// - A value zero: A, this is an indentity for that operation: op(x, zero) == x and op(zero, x) == x for Any x: A

trait Monoid[A] {
  def op(a1: A, a2: A): A // Satisfies op(op(x,y), z) = op(x, op(y,z))
  def zero: A // Satisfies op(x, zero) == x and op(zero, x) == x
}

object Monoids extends App{

  // EXAMPLE - String monoid
  val stringMonoid = new Monoid[String] {
    def op(a1: String, a2: String) = a1 + a2
    val zero = ""
  }
  println(stringMonoid.op("Hola, ","que ase?"))

  // EXAMPLE - List concatenation monoid
  def ListMonoid[A] = new Monoid[List[A]] {
    def op(a1: List[A], a2: List[A]) = a1 ++ a2
    def zero = Nil
  }
  println(ListMonoid.op(List(1,2,3), List(4,5,6)))

  // EXERCISE 10.1
  // Give Monoid instances for integer addition and multiplication as well as the Boolean operators
  // val intAddition: Monoid[Int]
  // val intMultiplication: Monoid[Int]
  // val booleanOr: Monoid[Boolean]
  // val booleanAnd: Monoid[Boolean]
  val intAddition: Monoid[Int] = new Monoid[Int] {
    override def op(a1: Int, a2: Int) = a1 + a2
    override def zero = 0
  }
  println(intAddition.op(1,2))

  val intMultiplication: Monoid[Int] = new Monoid[Int] {
    override def op(a1: Int, a2: Int) = a1 * a2
    override def zero = 1
  }
  println(intMultiplication.op(2,3))

  val booleanOr: Monoid[Boolean] = new Monoid[Boolean] {
    override def op(a1: Boolean, a2: Boolean) = if(a1) a1 else a2
    override def zero = false
  }
  println(booleanOr.op(true, true))

  val booleanAnd: Monoid[Boolean] = new Monoid[Boolean] {
    override def op(a1: Boolean, a2: Boolean) = if(!a1) a1 else a2
    override def zero = true
  }
  println(booleanAnd.op(true, false))

  // EXERCISE 10.2
  // Give a Monoid instance for combining Option values
  // def optionMonoid[A]: Monoid[Option[A]]

  // Notice that we have a choice in how we implement op. We can compose the options in either order. Both of those
  // implementations. Both of those implementations satisfy the monoid laws, but they are not equivalent. This is true
  // in general-that is, every monoid has a dual where the op combines things in the oposite order. Monoids like ç
  // booleanOr and intAddition are equivalent to their duals because their op es commutative as well as associative

  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]]{
    def op(a1: Option[A], a2: Option[A]) = a1 orElse a2
    def zero = None
  }
  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    def op(x: A, y: A): A = m.op(y, x)
    val zero = m.zero
  }

  def firstOptionMonoid[A]: Monoid[Option[A]] = optionMonoid[A]
  def lastOptionMonoid[A]: Monoid[Option[A]] = dual(firstOptionMonoid)

  // Aquí se ve la diferencia, dado que las leyes imponen función asociativa pero no conmutativa, en el caso de intSum
  // el dual sería igual dado que la suma de enteros es conmutativa

  println(firstOptionMonoid.op(Some(5), Some(4)))
  println(lastOptionMonoid.op(Some(5), Some(4)))

  // EXERCISE 10.3
  // A function having the same arguments and return type is sometimes called an endofunction. Write a monoid for
  // endofunctions: def endoMonoid[A]: Monoid[A => A]
  def endoMonoid[A]: Monoid[A => A] = new Monoid[A => A]{
    def op(f: A => A, g: A => A) = f andThen g
    def zero = identity _
  }

  // EXERCISE 10.5
  // Implement foldMap
  def foldMap[A,B](as: List[A], m: Monoid[B])(f: A => B): B = as.foldRight(m.zero)((data, acc) => m.op(acc, f(data)))

  println(foldMap(List(1,2,3), intAddition)(x => x + 1))

  // EXERCISE 10.6
  // Hard: The foldMap function can be implemented using either foldLeft or foldRight. But you can also write foldLeft
  // and foldRight using foldMap!
  def foldRight[A, B](as: List[A])(z: B)(f: (A, B) => B): B = foldMap(as, endoMonoid[B])(f.curried)(z)

  // Intercambiamos el orden de los parametros con dual(endoMonoid[B]) y a => b => f(b, a)
  def foldLeft[A, B](as: List[A])(z: B)(f: (B, A) => B): B = foldMap(as, dual(endoMonoid[B]))(a => b => f(b, a))(z)

  val doubleFunction: (Int, Int) => Int = (x,y) => x + y
  val curriedFunction = (doubleFunction.curried)(3)
  println(curriedFunction(5))

  println(foldRight(List(1,2,3))("")((a, b) => a.toString + b))

  // EXERCISE 10.7
  // Implement a foldMap for IndexedSeq. Your implementation should use the strategy splitting the sequence in two,
  // recursevely processing each half, and then adding the answer together with the monoid.
  def foldMapV[A,B](v: IndexedSeq[A], m: Monoid[B])(f: A => B): B = {
    if (v.length == 0) m.zero
    else if (v.length == 1) f(v(0))
    else {
      val (l, r) = v.splitAt(v.length/2)
      m.op(foldMapV(l,m)(f), foldMapV(r,m)(f))
    }
  }
  println(foldMapV(IndexedSeq(1,2,3,4), intAddition)(identity))
  println(foldMapV(IndexedSeq(1,2,3,4), stringMonoid)((a) => a.toString))
  // *Recall that IndexedSeq is the interface for inmutable data strustures supporting efficient random access.
  // It also has efficient splitAt and length methods.

  // EXERCISE 10.9
  // Hard: Use foldMap to detect whether a given IndexedSeq[Int] is ordered. You´ll need to come up with a creative
  // monoid

  def ordered(ints: IndexedSeq[Int]): Boolean = {
    // Our monoid tracks the minimum and maximum element seen so far as well as whether the elements are so far ordered
    val ascMon = new Monoid[Option[(Int, Int, Boolean)]] {
      def op(o1: Option[(Int, Int, Boolean)], o2: Option[(Int, Int, Boolean)]) = {
        (o1, o2) match {
          // The ranges should not overlap if the sequence is ordered
          case (Some((x1, y1, p)), Some(( x2, y2, q))) => Some(x1 min x2, y1 max y2, p && q && y1 <= x2)
          case (x, None) => x
          case (None, x) => x
        }
      }
      val zero = None
    }
    val descMon = new Monoid[Option[(Int,Int,Boolean)]] {
      def op(o1: Option[(Int, Int, Boolean)], o2: Option[(Int, Int, Boolean)]) = {
        (o1,o2) match  {
          case (Some((x1, y1, p)), Some(( x2, y2, q))) => Some(x1 min x2, y1 max y2, p && q && y1 >= x2)
          case (x, None) => x
          case (None, x) => x
        }
      }
      val zero = None
    }
    val ascending = foldMapV(ints, ascMon)(i => Some(i,i, true)).map(_._3).getOrElse(true)
    val descending = foldMapV(ints, descMon)(i => Some(i,i, true)).map(_._3).getOrElse(true)
    ascending || descending
  }

  println(ordered(IndexedSeq(5,4,3)))

  // PARALLEL PARSING

  sealed trait WC
  case class Stub(chars: String) extends WC
  case class Part(lStub: String, words: Int, rStub: String) extends WC

  // EXERCISE 10.10
  // Write a monoid instance for WC and make sure it meets the monoid Laws
  // val wcMonoid: Monoid[WC]
  val wcMonoid: Monoid[WC] = new Monoid[WC] {
    // The empty result, where we haven´t seen any character yet
    def zero = Stub("")

    def op(a: WC, b: WC) = (a,b) match {
      case (Stub(c), Stub(d)) => Stub(c + d)
      case (Stub(c), Part(l,w,r)) => Part(c + l, w, r)
      case (Part(l,w,r), Stub(c)) => Part(l, w, r + c)
      case (Part(l1,w1,r1), Part(l2,w2,r2)) => Part(l1, w1 + (if((r1 + l2).isEmpty) 0 else 1 + w2), r2)
    }
  }

  // EXERCISE 10.11
  // Use the WC monoid to implement a function that counts words in a String by recursevely splitting it into substrings
  // and counting the words in those substrings

  def count(s: String): Int = {
    def wc(c: Char): WC = if(c.isWhitespace) Part("", 0, "") else Stub(c.toString)
    def unstub(s: String) = s.length min 1
    foldMapV(s.toIndexedSeq, wcMonoid)(wc) match {
      case Stub(s) => unstub(s)
      case Part(l,w,r) => unstub(l) + w + unstub(r)
    }
  }

  println(count("Me echaron droja en el monoide"))

  // FOLDABLE DATA STRUCTURES

  // F => Type Constructor
  /*trait Foldable[F[_]] {
    def foldRight[A,B](as: F[A])(z: B)(f: (A, B) => B): B
    def foldLeft[A,B](as: F[A])(z: B)(f: (B, A) => B): B
    def foldMap[A,B](as: F[A])(f: A => B)(mb: Monoid[B]): B
    def concatenate[A](as: F[A])(mb: Monoid[A]): A = foldLeft(as)(m.zero)(m.op)
  }*/

  // EXERCISE 10.12
  // Implement Foldable[List], Foldable[IndexedSeq] and Foldable[Stream]. Remember that foldRight, foldLeft and foldMap
  // can all be implemented in terms of each other, but that might not be the most efficient implementation.
  trait Foldable[F[_]] {
    def foldRight[A,B](as: F[A])(z: B)(f: (A, B) => B): B = foldMap(as)(f.curried)(endoMonoid[B])(z)
    def foldLeft[A,B](as: F[A])(z: B)(f: (B, A) => B): B = foldMap(as)(a => (b: B) => f(b,a))(dual(endoMonoid[B]))(z)
    def foldMap[A,B](as: F[A])(f: A => B)(mb: Monoid[B]): B = foldRight(as)(mb.zero)((a,b) => mb.op(f(a),b))
    def concatenate[A](as: F[A])(mb: Monoid[A]): A = foldLeft(as)(mb.zero)(mb.op)
    def toList[A](fa: F[A]): List[A] = foldRight(fa)(List[A]())(_ :: _)
  }

  object ListFoldable extends Foldable[List] {
    override def foldRight[A, B](as: List[A])(z: B)(f: (A, B) => B) = as.foldRight(z)(f)
    override def foldLeft[A, B](as: List[A])(z: B)(f: (B, A) => B) = as.foldLeft(z)(f)
    override def foldMap[A, B](as: List[A])(f: A => B)(mb: Monoid[B]): B = foldLeft(as)(mb.zero)((b, a) => mb.op(b, f(a)))
  }

  object IndexedSeqFoldable extends Foldable[IndexedSeq] {
    override def foldRight[A, B](as: IndexedSeq[A])(z: B)(f: (A, B) => B) = as.foldRight(z)(f)
    override def foldLeft[A, B](as: IndexedSeq[A])(z: B)(f: (B, A) => B) = as.foldLeft(z)(f)
    override def foldMap[A, B](as: IndexedSeq[A])(f: A => B)(mb: Monoid[B]): B = foldMapV(as, mb)(f)
  }

  object StreamFoldable extends Foldable[Stream] {
    override def foldRight[A, B](as: Stream[A])(z: B)(f: (A, B) => B) = as.foldRight(z)(f)
    override def foldLeft[A, B](as: Stream[A])(z: B)(f: (B, A) => B) = as.foldLeft(z)(f)
  }

  // EXERCISE 10.13
  // Recall the binary Tree data type. Implement a Foldable instance for it
  sealed trait Tree[+A]
  case class Leaf[A](value: A) extends Tree[A]
  case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  object TreeFoldable extends  Foldable[Tree] {
    override def foldRight[A, B](as: Tree[A])(z: B)(f: (A, B) => B): B = as match {
      case Leaf(a) => f(a,z)
      case Branch(l,r) => foldRight(l)(foldRight(r)(z)(f))(f)
    }

    override def foldLeft[A, B](as: Tree[A])(z: B)(f: (B, A) => B): B = as match  {
      case Leaf(a) => f(z,a)
      case Branch(l,r) => foldLeft(r)(foldLeft(r)(z)(f))(f)
    }

    override def foldMap[A, B](as: Tree[A])(f: (A) => B)(mb: Monoid[B]): B = as match {
      case Leaf(a) => f(a)
      case Branch(l,r) => mb.op(foldMap(l)(f)(mb), foldMap(r)(f)(mb))
    }
  }

  println(TreeFoldable.foldRight(Branch(Branch(Leaf(1), Leaf(2)), Leaf(3)))("")((a,b) => a.toString + b))

  // EXERCISE 10.14
  // Write a Foldable[Option] instance
  object OptionFoldable extends Foldable[Option] {
    override def foldLeft[A, B](as: Option[A])(z: B)(f: (B, A) => B): B = as match {
      case None => z
      case Some(a) => f(z,a)
    }

    override def foldRight[A, B](as: Option[A])(z: B)(f: (A, B) => B): B = as match {
      case None => z
      case Some(a) => f(a,z)
    }

    override def foldMap[A, B](as: Option[A])(f: (A) => B)(mb: Monoid[B]): B = as match {
      case None => mb.zero
      case Some(a) => f(a)
    }
  }

  println(OptionFoldable.foldLeft(Some(1))("")((b, a) => a.toString + b))

  // EXERCISE 10.15
  // Any Foldable structure can be turned into a List. Write this conversion in a generic way:
  // def toList[A](fa: F[A]): List[A]

  //def toList[F[_],A](fa: F[A]): List[A] = foldRight(fa)(List[A]())(_ :: _) => Esto debe ir en el trait Foldable
  println(TreeFoldable.toList(Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))))


  // EXERCISE 10.16
  // Prove it. Notice that your implementation of op is abviosuly associative so long as A.op and B.op are both
  // associative
  def productMonoid[A,B](A: Monoid[A], B: Monoid[B]): Monoid[(A, B)] =
  new Monoid[(A, B)] {
    def op(x: (A, B), y: (A, B)) =
      (A.op(x._1, y._1), B.op(x._2, y._2))
    val zero = (A.zero, B.zero)
  }

  val newMonoid = productMonoid(intAddition, stringMonoid)
  println(foldMapV(IndexedSeq((1,"a"),(2,"b"),(3,"c"),(4,"d")), newMonoid)(identity))

  // MERGING KEY-VALUE MAPS
  def mapMergeMonoid[K,V](V: Monoid[V]): Monoid[Map[K,V]] = new Monoid[Map[K, V]] {
    def zero = Map[K,V]()
    def op(a: Map[K, V], b: Map[K, V]) = {
      (a.keySet ++ b.keySet).foldLeft(zero) {
        (acc, k) => acc.updated(k, V.op(a.getOrElse(k, V.zero), b.getOrElse(k, V.zero)))
      }
    }
  }

  val mergingMonoid: Monoid[Map[String, Map[String, Int]]] = mapMergeMonoid(mapMergeMonoid(intAddition))
  val m1 = Map("o1" -> Map("i1" -> 1, "i2" -> 2))
  val m2 = Map("o1" -> Map("i2" -> 3))
  val m3 = mergingMonoid.op(m1, m2)
  println(m3)

  // EXERCISE 10.17
  // Write a monoid instance for functions whose results are monoids
  // def functionMonoid[A,B](B: Monoid[B]): Monoid[A => B]
  def functionMonoid[A,B](B: Monoid[B]): Monoid[A => B] = new Monoid[A => B]{
    def op(f: A => B, g: A => B) = a => B.op(f(a), g(a))
    def zero: A => B = a => B.zero
  }

  // EXERCISE 10.18
  // A bag is like a set, except that it´s represented by a map that contains one entry per element with that element
  // as the key, and the value under that key is the number of times the element appears un the bag. For example:
  // scala> bag(Vector("a", "rose", "is", "a", "rose"))
  // res0: Map[String,Int] = Map(a -> 2, rose -> 2, is -> 1)
  // Use monoids to compute a “bag” from an IndexedSeq.
  // def bag[A](as: IndexedSeq[A]): Map[A, Int]
  def bag[A](as: IndexedSeq[A]): Map[A, Int] = foldMapV(as, mapMergeMonoid[A,Int](intAddition))((a: A) => Map(a -> 1))
  println(bag(Vector("a", "rose", "is", "a", "rose")))
}
