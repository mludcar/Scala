package ScalaAdvanced.lectures.part1advanceScala

import scala.annotation.tailrec

/**
  * Created by miguel.ludena on 04/04/2019.
  */
object Recap extends App{

  val aCondition: Boolean = false
  val aConditionedVal = if (aCondition) 42 else 65
  //instruction vs expresions

  //compiler infers types for us
  val aCodeBlock = {
    if(aCondition) 54
    56
  }

  //Unit = void
  val theUnit = println("hello, Scala")

  //funtions
  def aFunction(x: Int): Int = x + 1

  //recursion: stack and tail
  @tailrec def factorial(n: Int, accumulator: Int): Int =
    if(n <= 0) accumulator
    else factorial(n - 1, n * accumulator)

  //object-oriendet programming
  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog // subtyping polimorphism

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("crunch!!")
  }

  //method notations
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog

  //Anonymaous Classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("roar")
  }

  //Generics
  abstract class MyList[+A] //variance and variance problems in this course

  //Singletons and companions
  object MyList

  //case classes, generate companion objects
  case class Person(name: String, age: Int)

  //exceptions and try/cath/finally
  val throwsException= throw new RuntimeException //Nothing
  val aPontentialFailure = try {
    throw new RuntimeException
  }catch{
    case e: Exception => "I caught an exception"
  } finally {
    println("some logs")
  }

  //functional Programming
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  incrementer(1)

  val anonymousIncrementer = (x: Int) => x + 1
  List(1,2,3).map(anonymousIncrementer) //HOF

  //map, flatMap, filter

  //for comprehension
  val pairs = for {
    num <- List(1,2,3)
    char <- List('a', 'b', 'c')
  } yield num + "-" + char

  //Scala collections: Seqs, Arrays, Lists, Vectors, Maps, Tuples
  val aMap = Map(
    "Daniel" -> 789,
    "Jess" -> 555
  )

  //"collections": Options, Try
  val anOption = Some(2)

  //pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => x + "th"
  }

  val bob = Person("Bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"Hi, my name is $n"
  }
}
