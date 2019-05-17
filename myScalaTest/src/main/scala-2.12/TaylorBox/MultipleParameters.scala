package TaylorBox

import scala.collection.immutable.ListMap

/**
  * Created by miguel.ludena on 24/03/2019.
  */
object MultipleParameters extends App{

  val grades = Map("Kim" -> 90,"Al" -> 85,"Melissa" -> 95,"Emily" -> 91,"Hannah" -> 92)

  //The sortBy method returns a Seq[(String, Int)], i.e., a sequence of tuples:
  val x = grades.toSeq.sortBy(_._1)
  //x: Seq[(String, Int)] = ArrayBuffer((Al,85), (Emily,91), (Hannah,92), (Kim,90), (Melissa,95))

  // The _* portion of the code takes a little getting used to. It’s used to convert the data so it will be passed as
  // multiple parameters to the ListMap or LinkedHashMap.

  // You can’t directly construct a ListMap with a sequence of tuples, but because the apply method in the ListMap
  // companion object accepts a Tuple2 varargs parameter, you can adapt x to work with it, i.e., giving it what it wants:
  val y = ListMap(x: _*)
  // scala.collection.immutable.ListMap[String,Int] = Map(Al -> 85, Emily -> 91, Hannah -> 92, Kim -> 90, Melissa -> 95)

  //Attempting to create the ListMap without using this approach results in an error:
  //val error = ListMap(x)
  /*<console>:16: error: type mismatch;
    found   : Seq[(String, Int)]
    required: (?, ?)
    ListMap(x)*/

  // Another way to see how _* works is to define your own method that takes a varargs parameter. The following printAll
  // method takes one parameter, a varargs field of type String:
  def printAll(strings: String*) {
    strings.foreach(println)
  }

  //If you then create a List like this:
  val fruits = List("apple", "banana", "cherry")
  //you won’t be able to pass that `List` into printAll; it will fail like the previous example:
  //printAll(fruits) Error
  /*<console>:20: error: type mismatch;
    found   : List[String]
    required: String
    printAll(fruits)*/

  //But you can use _* to adapt the List to work with printAll, like this:
  // this works
  printAll(fruits: _*)

  // If you come from a Unix background, it may be helpful to think of _* as a “splat” operator. This operator tells the
  // compiler to pass each element of the sequence to printAll as a separate argument, instead of passing fruits as a
  // single List argument.
}
