package FunctionalStructures

/**
  * Created by miguel.ludena on 01/04/2019.
  */
object MyFirstMonoid extends App{

  trait Monoid[T] {
    def op(l: T, r: T): T
    def zero: T
  }

  val intAddition: Monoid[Int] = new Monoid[Int] {
    val zero: Int = 0
    override def op(l: Int, r: Int): Int = l + r
  }

  val intMultiplication: Monoid[Int] = new Monoid[Int] {
    val zero: Int = 1
    override def op(l: Int, r: Int): Int = l * r
  }

  val stringConcatenation: Monoid[String] = new Monoid[String] {
    val zero: String = ""
    override def op(l: String, r: String): String = l + r
  }

  val strings = List("Mi monoide\n", "me lo robaron\n", "ayer, cuando dormia\n")
  val numbers = Array(1,2,3,4)

  println(s"LeftFolded: \n ${strings.foldLeft(stringConcatenation.zero)(stringConcatenation.op)}")
  println(s"RightFolded: \n ${strings.foldRight(stringConcatenation.zero)(stringConcatenation.op)}")

  println(s"6! is: ${numbers.foldLeft(intMultiplication.zero)(intMultiplication.op)}")

  val sentence = List("Achilipu,\n", "Apu\n", "Achili\n")
  println(s"${sentence.foldLeft("")(_ + _)}")

  val prices = List(1.5,2.0,3.5)
  val sum = prices.foldRight(0.0){(acc, ind) => println(s"El dato es $acc"); acc + ind}
  println(s"El resultado es: $sum")

  val concat = strings.foldRight(""){(acc, ind) => println(s"El dato es $ind"); acc + ind }
  println(s"El resultado es: $concat")

  val concat2 = strings.foldRight(""){(ind, acc) => println(s"El dato es $ind"); acc + ind}
  println(s"El resultado es: $concat2")

  object MonoidOperations {
    def fold[T](list: List[T], m: Monoid[T]): T = foldMap(list, m)(identity)
    def foldMap[T, Y](list: List[T], m: Monoid[Y])(f: T => Y): Y =
      list.foldLeft(m.zero){
        case (t, y) => m.op(t, f(y))
      }

    //Not parallel, Better Performance as before
    def balancedFold[T, Y](list: IndexedSeq[T], m: Monoid[Y])(f: T => Y): Y =
      if(list.length == 0) m.zero
      else if(list.length == 1) f(list(0))
      else {
        val (left, right) = list.splitAt(list.length / 2)
        m.op(balancedFold(left, m)(f), balancedFold(right, m)(f))
      }

    def foldPar[T](list: List[T], m: Monoid[T]): T = foldMapPar(list, m)(identity)
    def foldMapPar[T, Y](list: List[T], m: Monoid[Y])(f: T => Y): Y =
      list.par.foldLeft(m.zero) {
        case (t, y) => m.op(t, f(y))
      }
  }

  println(s"4! is: ${MonoidOperations.balancedFold(numbers, intMultiplication)(identity)}")

  println(s"Folded:\n ${MonoidOperations.foldPar(strings, stringConcatenation)}")


  //MONOIDS AND COMPOSITION

  def compose[T, Y](a: Monoid[T], b: Monoid[Y]): Monoid[(T, Y)] =
    new Monoid[(T,Y)] {
      val zero: (T,Y) = (a.zero, b.zero)
      override def op(l: (T, Y), r: (T, Y)): (T,Y) = (a.op(l._1, r._1), b.op(l._2, r._2))
    }

  val sumAndProduct = compose(intAddition, intMultiplication)
  println(s"The sum and Produc is: ${MonoidOperations.balancedFold(numbers, sumAndProduct)(i => (i,i))}")

  def mapMerge[K, V](a: Monoid[V]): Monoid[Map[K,V]] = {
    new Monoid[Map[K,V]] {
      override def zero: Map[K,V] = Map()
      override def op(l: Map[K,V], r: Map[K,V]): Map[K,V] =
        (l.keySet ++ r.keySet).foldLeft(zero) {
          case (res, key) => res.updated(key, a.op(l.getOrElse(key, a.zero), r.getOrElse(key, a.zero)))
        }
    }
  }

  val features = Array("hello", "features", "for", "ml", "features", "for", "hello")
  val counterMonoid: Monoid[Map[String, Int]] = mapMerge(intAddition)
  println(s"The features are: ${MonoidOperations.balancedFold(features, counterMonoid)(i => Map(i -> 1))}")
}
