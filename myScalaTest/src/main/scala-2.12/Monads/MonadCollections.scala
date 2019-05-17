package Monads

/**
  * Created by miguel.ludena on 20/03/2019.
  */
/*
Definición => Monads are containers. That means they contain some sort of elements. Instead of allowing us to operate on
     these elements directly, the container itself has certaub properties. We can work with the element within. Scala
     collections uses monads internally for this reason such as Option[T]

Propiedades:
    1. A parapetrized type -> Option[T]
    2. Unit(return) -> Option.apply
    3. FlatMap(bind) -> Option.flatMap

Unit
    Unit is defined as A => M[A]. In other words, put an A in an M. If we define Option[Int] the Unit is Int => Option[Int]

FlatMap
    FlatMap is defined as (M[A], A => M[B]) => M[B]. All we are doing is defining a container type of M[A] that can do
    some kind of operation to produce and return an M[B]
 */

object MonadCollections extends App{

  val list = List.apply(1)
  // list: List[Int] = List(1)

  val list2 = List(1)
  // list2: List[Int] = List(1)

  def makeListofDoubles(int: Int): List[Double] = {
    List(int.toDouble)
  }

  val list3 = list.flatMap(makeListofDoubles)
  // list3: List[Double] = List(1.0), we realize flatMap keeps Structure

  val list4 = list.map(makeListofDoubles)
  // list4: List[List[Double]] = List(List(1.0), List(2.0)), we realize flatMap doesn´t keep Structure

  val list5 = list.map(_.toDouble)
  // list5: List[Double] = List(1.0)

  // The simplest possible monad is a container that holds one element. In Scala, this is Option[T]. It either contains
  // one element, or it contains zero elements
  // Option has two subtypes: Some and None, which instantiate the abstract Option type. If an Option containss a value,
  // it is Some(value). Otherwise, it´s None.

  // This means you can build an Option in 3 ways
  val op = Option("hello")
  // op: Option[String] = Some(hello)

  val some = Some("hello")
  // some: Some[String] = Some(hello)

  val none = None
  // none: None.type = None

  // Nothing is a special type that is a subtype of all other types. That means that if a container can hold an Int, it
  // can hold Nothing. If it can hold a String, it can hold Nothing. Nothing can literally be used in place of any type
  // What this means is that if a val type Option[A], it can hold a value of type A or Nothing. This makes it possible
  // for None to be the empty Option for all Option types

  //Options are monads, so we can use flatMap(and therefore map) with them
  val o1 = Some("hello")

  val o2 = None

  val o3 = o1.flatMap(a => o2.map(b => a + " " + b))
  // o3: Option[String] = None

  // This is because Options are success-biased. That means, if map succesfully finds a value in the Option, it executes
  // the map function. Otherwise, it dosen´t. In fact, the code inside the map function is not executed at all,
  // including any side-effecting code like println(). This observation could be used for debugging purposes.


  val u1 = Some("hello")

  val u2 = None

  val u3 = Some("world")

  val u4 = for {
    a <- u1
    b <- u2
    c <- u3
  } yield a + " " + c
  // u4: Option[String] = None

  // Sometimes we have an Option[A] and we need an A. We can get the A by calling Option.get. However, this is unsafe.
  // If there isn´t actually an A in the Option, it´ll thow an exception.

  val p: Option[Int] = None

  p.get
  // java.util.NoSuchElementException: None.get

  // Because of this, we try to avoid using .get wherever possible. Instead, we can use Option.getOrElse. This returns
  // the contents of the Option if available, or a default value if it´s not.

  val s1 = u1.getOrElse("world")
  // s1: String = hello

  val s2 = u2.getOrElse("world")
  // s2: String = world

  // This converts the Option[String] to a String safely, given the requirement that we need to be able to come up
  // with a default value that makes sense

  // An additional way to access an Option is through pattern matching. This works with most container types, including
  // the standard collection types. Pattern matching allows us to deconstruct a container and separate out its contents
  // as a named symbol
  val t1: Option[String] = Some("hello")

  val v1 = t1 match {
    case Some(s) => s
    case None => "nothing"
  }

  // If we end up with a Some, then we can assign a name,"s" to the value contained in the Option, and either return that
  // value or do domething with it and return some other String.

  // It´s important to note that match doesn´t just convert Option[A] => A like getOrElse and get do. It can convert
  // Option[A] => B where B is any type we like. All that is required is for every case clause to individually return B

  // Besides List, there are a numer of other collections in the Scala standard library. The most commonly used of these
  // are Map, Set, Vector and Stream. Each of these collections implements its own version of Unit(i.e. apply) and
  // flatMap, and we can therefore treat them as monads, allowing us to use for-comprehensions with them

  // A Map type(mot to be confused with a map function) is a collection of key-value pairs that allows the values to be
  // accesed by providing one of the keys.
  // The important thing to remember about Map is that despite the idisosyncratic way of accessing values, the elements
  // are tuples where the first member of the tuple is the key, and the second the value. This means that operations
  // that map or flatMap over a Map will assume tuple parameters and involve the keys in the operation

  // Usually, we only want to interact with the values of a Map, and because of that Map has a special mapValues method,
  // with allows you to perform map operations on the values as though they were in a sequence by themselves but keeps
  // them associated to their keys in the resulting Map.

  val aMap = Map((1 -> "a"), (2 -> "b"))
  // aMap: scala.collection.immutable.Map[Int,String] = Map(1 -> a, 2 -> b)
  // Note: the "->" notation is syntactic sugar for defining tuples that makes it
  // obvious that you intend that they be used in a Map

  aMap.mapValues(_.toUpperCase)
  // scala.collection.immutable.Map[Int,String] = Map(1 -> A, 2 -> B)

  // A set is simply an unordered collection containing only unique values. Attempting to add an element to a Set that
  // already contains it will return the original Set. The order of elements in a Set is never guaranteed, although it
  // may appear to be at first glance

  val aSet = Set(1, 3, 3, 5)
  // aSet: scala.collection.immutable.Set[Int] = Set(1, 3, 5)

  aSet + 4
  // scala.collection.immutable.Set[Int] = Set(1, 3, 5, 4)

  aSet + 5
  // scala.collection.immutable.Set[Int] = Set(1, 3, 5)

  // Use of Lists is very common in Scala, but it can sometimes be inefficient for random access because the time
  // complexity of accessing an element is O(n). Scala provides an alternative collection, Vector, that is optimized for
  // random access by storing its elements in a tree structure that has little memory overhead. All operations on a
  // Vector happen in effectively constant tume, which means that for large collections it can be significatly more
  // efficient than List

  // A Stream is essentially a List with an undetermined number of elements, which are computed lazily. A "lazy" value
  // is one that is only evaluated at the time it´s required.
  // We´ll demostrate that with an example below. Because Stream elements are lazy, a Stream can have an infinite size,
  // because we don´t need to inmediately evaluate an infinite number of elements, just define how we´d evaluate them if
  // we need to.

  def fib(a: Int, b: Int): Stream[Int] = a #:: fib(b, a+b)
  // (a: Int, b: Int)Stream[Int]

  val fibStream = fib(1, 1).take(8)
  // fibStream: scala.collection.immutable.Stream[Int] = Stream(1, ?)

  fibStream.toList
  // List[Int] = List(1, 1, 2, 3, 5, 8, 13, 21)

  fibStream
  // scala.collection.immutable.Stream[Int] = Stream(1, 1, 2, 3, 5, 8, 13, 21)

  // Note that the "#::" operator is just a Stream constructor that ensures that the elements to the right are appended
  // lazily. In this case "a" is the head of the Stream, and fib(b, a+b) is the recursively remainder.
  // If we were to just call fib(1,1) it would result in an infinitely long Stream, since it would equal the entire
  // Fibonacci sequence. By using the take method, we can grab only the first few elements. However, note that this
  // doesn´t actually cause those elements to be evaluated, because despite knowing how many elements there will be,
  // we don´t need to know what they are until we convert the Stream to a List. So our first result is Stream(1, ?)
  // meaning that we know the head, but don´t know the rest of the Stream because it hasn´t evaluated yet.
  // Once we evaluate the Stream elements, they stay evaluated. When we look at the Stream after deriving the List
  // from it, we can see that it contains all the expected elements, because they had to be evaluated in order to
  // generate the List. Note that this difference in the way the Stream is displayed does not mean that the Stream is
  // mutable. Its contents never changed. We just didn´t know what they were.

  // Fold is a way to reduce a multi-element collection down to a single element by applying a combining function.
  // A simple example of how fold works is to think of a list of numbers and a sum function. The function is applied to
  // each number in the list until all of them are added up.

  val numbers = List(1, 3, 5)
  // numbers: List[Int] = List(1, 3, 5)

  numbers.fold(0)(_ + _)
  // Int = 9

  // Note that fold takes two parameter lists. The first is a "neutral element", meaning that we can perform the fold
  // operation any number of times with this element and it won´t change the final answer. For addition, this is 0. The
  // second parameter list contains the fold function, which is a binary operation that itself takes two parameters,
  // the elements being added together. Here we´re using the underscore notation to simplify the syntax and pass an
  // anonymous function, but we could pass in any function that takes two parameters of similar type and returns a
  // result of that type, like so:

  def add(a: Int, b: Int): Int = a + b
  // (a: Int, b: Int)Int

  numbers.fold(0)(add)
  // Int = 9

  // It´s important to be aware that fold performs its operation on the collection elements in an arbitrary order, and
  // not necessarily the order in which the elements appear in the collection. Fold is apropiate if the operation is
  // commutative, e.g, multiplication and addition such as 1 x 2 = 2 x 1
  // Now we can´t say the same thing about subtraction and division as these operations are not commutative,
  // e.g 1-2 != 2-1 . For operations that are not commutative, mantaining a specific order of terms is important. When
  // the order is important, we can use the foldLeft and foldRight methods instead.
  // These carry out fold in a definitive order: foldLeft starts with the first(i.e. leftmost) element and works to the
  // last, while foldRight starts with the last element and works to the first. Both take a neutral element to start
  // this process with. Interestingly, foldLeft and foldRight do not need to end up with the same type as the collection
  // originally contained.

  def addString(a: String, b: Int): String = a + b
  // (a: String, b: Int)String

  numbers.foldLeft("")(addString)
  // String = 135

  // There is another method, reduce, which is best understood as a special case of fold that does not require a neutral
  // value. It just operates on the collection elements. However, if the collection is empty, it will throw an exception.

  // The groupBy method partitions a collection into subsets based on some discriminator, and is somewhat similar to the
  // way GROUP BY works in SQL. It generates a Map where the key is the discriminator, and the value is a collection
  // that matches that discriminator

  val fruit = List("apple", "apricot", "blueberry", "orange")
  // fruit: List[String] = List(apple, apricot, blueberry, orange)

  val fruitMap = fruit.groupBy(_.take(1))
  // fruitMap: Map(b -> List(blueberry), a -> List(apple, apricot), o -> List(orange))

  fruitMap("a")
  // List[String] = List(apple, apricot)

  // The map is made by grouping all elements that return the same result from the discriminator. In this case, the
  // result from the discriminator. In this case, the result is the first letter of each string. The derived map is
  // keyed on those letters, so that accessing a letter in the keyset will return the list of strings beginning with
  // that letter.
}
