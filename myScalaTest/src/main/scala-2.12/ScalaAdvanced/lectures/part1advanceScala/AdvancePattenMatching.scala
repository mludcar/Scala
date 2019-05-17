package ScalaAdvanced.lectures.part1advanceScala

import ScalaAdvanced.lectures.part1advanceScala.AdvancePattenMatching.MyList
import ScalaAdvanced.lectures.part1advanceScala.Recap.MyList

/**
  * Created by miguel.ludena on 04/04/2019.
  */
object AdvancePattenMatching extends App{

  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"The only element is $head")
    case _ =>
  }

  /*
  KINDS OF PATTERN MATCHING
    - constants
    - wildcards
    - case classes
    - tuples
    - some special magic like above
   */

  // Si hacemos pattern matchin de una clase(borrando el object, no es companion Object, si le cambiamos el nombre
  // tb funcionaria)
  // Con respecto al uso del Option en el unapply se debe devolver un tipo de Dato que tenga los metodos isEmpty()
  // y get. Esto se aplica para que el compilador sepa si el match se ha producido o no. Si isEmptu devuelve true entonces
  // el match ha fallado y pasa al siguiente case
  class Person(val name: String, val age: Int)

  // (person: Person) -> Objeto a descomponer
  // Option[(String, Int)] -> results as an Option or Option(tuple), es Optio pq necesita def isEmpty y get
  object Joputa {
    def unapply(person: Person):Option[(String, Int)] = {
      if (person.age < 21) None
      else Some((person.name, person.age))
    }

    def unapply(age: Int): Option[String] = Some(if(age < 21) "bill cosby style" else "olrait")
  }

  val bob = new Person("Bob", 25)
  val greeting = bob match {
    case Joputa(n, a) => s"Hi, my name is $n and I am $a old"
    case _ => "Haz algun niño y ayuda al pais"
  }

  println(greeting)

  val legalStatus = bob.age match {
    case Joputa(status) => s"Mi consejo es $status"
  }

  println(legalStatus)

  /*
  EXERCISE:
  Create a more ellegant pattern matchin than this.
   val n: Int = 45
   val matchProperty = n match {
      case x if x < 10 => "single digit"
      case x if x % 2 == 0 => "an even number"
      case _ => "no property"
   }
   */
  val n: Int = 8

  //Esto es reducible a la expresion
/*  object even{
    def unapply(arg: Int): Option[Boolean] = if(arg % 2 == 0) Some(true) else None
  }

  object single{
    def unapply(arg: Int): Option[Boolean] = if(arg < 10) Some(true) else None
  }

  val matchProperty = n match  {
    case even(_) => "an even number"
    case single(_) => "a single digit"
    case _ => "no property"
  }

  */

  object even{
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }

  object single{
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }

  // Utilizamos "_" en vez de patron pq da igual lo que haya dentro
  val matchProperty = n match  {
    case even() => "an even number"
    case single() => "a single digit"
    case _ => "no property"
  }

  println(matchProperty)

  //infix patterns
  case class Or[A, B](a: A, b: B)
  val either = Or(2, "two")
  val humanDescription = either match {
    case number Or string => s"$number is written as $string"
  }

  println(humanDescription)

  // decomposing patterns
  val varargs = numbers match {
    case List(1, _*) => "starting with 1"
  }

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  // No se puede cambiar el nombre del método unapplySeq dado que es palabra reservada al igual que unapply
  object MyList{
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] ={
      if(list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
    }
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val decomposed = myList match {
    case MyList(1,2, _*) => "starting with 1, 2"
    case _ => "something else"
  }

  // custom return types for unapply, some features are needed(not neccessary Option[A])
  // isEmpty: Boolean, get: something

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  // Si borro cualquiera de los métodos  no funcionaría
  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String]{
      def isEmpty = false
      def get = person.name
    }
  }

  println(bob match {
    case PersonWrapper(n) => s"This person´s name is $n"
    case _ => "An alien"
  })
}
