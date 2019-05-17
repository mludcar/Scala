package TaylorBox

/**
  * Created by miguel.ludena on 06/04/2019.
  */
object PartiallyAppliedVsPartialFunctions extends App{

  // 1. Partially Applied Functions
  // En ocasiones cuando tenemos una función dada no tenemos todos los argumentos para ejecutar la función lo que si
  // podemos hacer es generar una nueva función usando los parametros que ya tenemos y luego invocar esta nueva función
  // con los parametros que faltan

  val sum = (a: Int, b: Int, c: Int) => a + b + c
  // sum: (Int, Int, Int) => Int = <function3> función que implementa el trait function3

  val f = sum(1,2,_: Int)
  // f: Int => Int = <function1> función que implementa el trait function1

  println(f(3))

  // Ejemplo con def
  def mul(a: Int, b: Int, c:Int) = a * b * c
  val g = mul(2,3, _:Int)
  println(g(4))

  // 2. Partial Functions
  // Son funciones que no preoveen de respuesta para cada valor que se les pase. Se provee de respuesta solo para un
  // subconjunto de posibles datos y define los datos que puede manejar. En Scala tb se puede utilizar para determinar
  // si se puede manejar un valor en particular.

  // val divide = (x: Int) => 42 / x; divide(0) -> si hiciesemos eso tendríamos excepción, pero si utilizamos Partial Functions

  // 2.1 Creación de Partial Function(I)
  // Se debe crear con:
  // a. def apply -> implementación normal de la función
  // b. isDefinedAt -> impone el subconjunto de datos que puede aceptar la función

  val divide = new PartialFunction[Int, Int] {
    def apply(x: Int) = 42 / x
    def isDefinedAt(x: Int) = x != 0
  }
  divide.isDefinedAt(1) // True
  if(divide.isDefinedAt(1)) println(divide(1))
  divide.isDefinedAt(0) // False

  // 2.2 Creación de Partial Function(II)
  // Tb se puede crear con case, aunque ahora no creamos el método isDefinedAt, funciona exactamente igual que el
  // anterior ejemplo

  val divide2: PartialFunction[Int, Int] = {
    case d: Int if d != 0 => 42 / d
  }

  divide2.isDefinedAt(1) // True
  if(divide2.isDefinedAt(1)) println(divide2(1))
  divide2.isDefinedAt(0) // False

  //Ejemplo con una transformación
  val convertLowNumToString = new PartialFunction[Int, String] {
    val nums = Array("one", "two", "three", "four", "five")
    def apply(i: Int) = nums(i-1)
    def isDefinedAt(i: Int) = i > 0 && i < 6
  }

  // OrElse and andThen
  // Una caracteristica de las Partial Functions es que se pueden unir o encadenar. P.e. se puede unir una Partial
  // Function que actúe sobre los numeros pares y otra sobre los impares. Dando asi solución al conjunto entero

  val convert1to5 = new PartialFunction[Int, String] {
    val nums = Array("one", "two", "three", "four", "five")
    def apply(i: Int) = nums(i-1)
    def isDefinedAt(i: Int) = i > 0 && i < 6
  }

  val convert6to10 = new PartialFunction[Int, String] {
    val nums = Array("six", "seven", "eight", "nine", "ten")
    def apply(i: Int) = nums(i-6)
    def isDefinedAt(i: Int) = i > 5 && i < 11
  }

  //El método OrElse proviene del trait PartialFunction que tb incluye el método andThen
  val handle1to10 = convert1to5 orElse convert6to10

  // AQUI EMPIEZA LA LOCURA -> MÉTODO COLLECT EN COLECIIONES
  // El método collect toma una función parcial como parámetro, de hecho ScalaDoc describe collect como "Construye una
  // nueva colleción aplicando una función parcial a todos los elementos de la lista en los cuales la función este
  // definida"

  val divideCollect = new PartialFunction[Int, Int] {
    def apply(x: Int) = 42 / x
    def isDefinedAt(x: Int) = x != 0
  }

  println(s"El resultado es ${handle1to10(8)}")

  // List(0,1,2) map { divide} -> Daria un MatchError
  List(0,1,2) collect { divide } // Esto si que funciona

  // Esto sucede debido en que el método collect esta implementado de manera que utiliza el método isDefinedAt para
  // cada elemento. Como resultado no ejecuta el método divide cuando el input sea 0

  // Otro ejemplo es pasar una lista que contiene un mix de tipos de dato y pasar una función que solo admite Int
  List(42, "pedo") collect { case i: Int => i + 1} // res0: List[Int] = List(43)

  // Esto sucede pq veridicando el método isDefinedAt por debajo, collect puede manejar el hecho de que la
  // función anonima no maneja Strings como input

  // Otra funcionalidad es que se puede utilizar un map con la uníon de dos funciones parciales
  val sample = 1 to 5
  val isEven: PartialFunction[Int, String] = {
    case x if x % 2 == 0 => x + " is even"
  }
  val evenNumbers = sample collect isEven
  // evenNumbers: scala.collection.immutable.IndexedSeq[String] = Vector(2 is even, 4 is even)

  val isOdd: PartialFunction[Int, String] = {
    case x if x % 2 == 1 => x + " is odd"
  }

  val numbers = sample map { isEven orElse isOdd}
  //scala.collection.immutable.IndexedSeq[String] =Vector(1 is odd, 2 is even, 3 is odd, 4 is even, 5 is odd)

}
