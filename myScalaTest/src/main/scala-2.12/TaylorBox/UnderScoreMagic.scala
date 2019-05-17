package TaylorBox

/**
  * Created by miguel.ludena on 14/10/2018.
  */
object UnderScoreMagic extends App{

  //Uso 1: Pattern Matching
  def matchTest(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => "anything other than one and two"
  }
  println("Uso 1: Pattern Matching")
  println(matchTest(1))
  println(matchTest(2))
  println(matchTest(5))
  println()

  /*
  expr match {
    case List(1,_,_) => " a list with three element and the first element is 1"
    case List(_*)  => " a list with zero or more elements "
    case Map[_,_] => " matches a map with any key type and any value type "
    case _ => CASO POR DEFECTO
  }
   */

  //Uso 2: Anonymous Functions

  //Scala represents anonymous functions with a elegant syntax.
  // The _ acts as a placeholder for parameters in the anonymous function.
  // The _ should be used only once, But we can use two or more underscores to refer different parameters.

  println("Uso 2: Anonymous Functions(Un parámetro)")
  List(1,2,3,4,5).foreach(a => print(a))
  println()
  List(1,2,3,4,5).foreach(print(_))

  //Here the _ refers to the parameter. The first one is a short form of the second one.
  // Lets look at another example which take two parameters.

  val sum1 = List(1,2,3,4,5).reduceLeft(_ + _)
  val sum2 = List(1,2,3,4,5).reduceLeft((a, b) => a + b)
  println("\n")

  println("Uso 2: Anonymous Functions(Dos parámetros)")
  println(sum1)
  println(sum2)
  println()

  //Uso 3: Properties

  //In scala, a getter and setter will be implicitly defined for all non-private var in a object.
  // The getter name is same as the variable name and _= is added for setter name.
  // We can define our own getters and setters.
  // This is looking similar to Ruby getters and setters.
  // Ok lets see an example which uses the getter and setters.
  // Si quitamos el def age_=(n: Int) da error de compilación al no poder acceder a private var a

  class Test {
    private var a = 0
    def age = a
    def age_=(n:Int) = {
      require(n>0)
      a = n
    }
  }

  println("Uso 3: Properties")
  val t = new Test
  t.age = 5
  println(t.age)
  println()

  //Uso 4: Functions

  //Scala is a functional language. So we can treat function as a normal variable.
  // If you try to assign a function to a new variable, the function will be invoked and the result will be
  // assigned to the variable. This confusion occurs due to the optional braces for method invocation.
  // We should use _ after the function name to assign it to another variable.

  val list = 10 :: 30 :: List(20)
  def addList[A](xs: List[A]): List[A] = xs
  def addListInt = addList[Int] _

  println("Uso 4: Functions")
  println(addListInt(list))

}
