package ScalaAdvanced.lectures.part2afp

/**
  * Created by miguel.ludena on 07/04/2019.
  */
object CurryPAF extends App{

  // curried function (Function that return another function)
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(superAdder(3)(6)) //curried function

  // METHOD!
  def curriedAdder(x: Int)(y: Int): Int = x + y

  //FUNCTION VALUE
  val add4: Int => Int = curriedAdder(4)
  //lifting = ETA-EXPRESION

  // functions != methods (JVM limitation)
  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) // ETA -expansion, compiler rewrites: List(1,2,3).map(x => inc(x))

  // Partial function applications
  val add5 = curriedAdder(5) _ // Int => Int , con el "_" forzamos la ETA-expansion

  // EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedMethod(x: Int)(y: Int) = x + y

  // add7: Int => Int = y => 7 + y
  // as many implementations of add7 using above
  //DE DANIEL
  val add7 = (x: Int) => simpleAddFunction(7,x) //simplest
  val add7_2 = simpleAddFunction.curried(7)
  val add7_4 = curriedMethod(7)(_) // PAF => igual que add7CurriedMethod
  //MIAS
  val add7AddFunctionPF = simpleAddFunction(7, _: Int)
  def add7AddMethodPF = simpleAddMethod(7, _: Int) // turning method into a function value, rewrites code y => simpleAddMethod(7, y)
  val add7CurriedMethod = curriedMethod(7) _ // PAF we lift the method in a function value
  println(add7CurriedMethod(3))

  // underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, I´m ", _: String, ", how are you?") // x: String => concatenator(hello, x, how..)
  println(insertName("Miguel"))

  val fillInTheBlanks = concatenator("Hello, ", _:String, _:String) // (x,y) => concatenator("Hello", x, y)
  println(fillInTheBlanks("Miguel", " traeme tabaco"))

  // EXERCISES
  /*
    1.  Procces a list of number and return their string representations with different formats
        Use the %4.2f, %8.6f and %14.12f with a curried formatter function
        HINT => "%4.2f".format(Math.pi)
   */
  //println("%4.2f".format(math.Pi))
  //LO QUE YO HE ENTENDIDO
  def curriedMethod(list: List[Double])(formatter: String): List[String] = list.map(formatter.format(_))
  val applyFormat = curriedMethod(List(0.0,1.0,2.0)) _

  //LO QUE HA ENTENDIDO DANIEL
  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(math.Pi,math.E, 1, 9.8, 1.3e-12)

  val simpleFormat = curriedFormatter("%4.2f") _ // lift
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println(numbers.map(preciseFormat))


  /*
    2. difference between:
        - functions vs methods
        - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  /*
   calling byName and byFunction
    - int
    - method
    - parenMethod
    - lambda
    - PAF
   */

  byName(23) // ok
  byName(method) //ok
  byName(parenMethod())
  byName(parenMethod) //ok but beware ==> byName(parenMethod())
  // byName(() => 42) // not ok
  byName((() => 42)()) // ok
  byName((() => 42)())
  // byName(parenMethod _) // not ok

  // byFunction(45) // not ok
  // byFunction(method) // not ok LOS METODOS SIN PARENTESIS NO HACEN EXPANSION(p.e y => simpleAddMethod(7, y))
                        // por eso al no poder transformar un método en una function value válida NANAI
  //byFunction(parenMethod()) // compiler does ETA-EXPANSION -> por el puto parentesis
  byFunction(() => 46) // ok
  byFunction(parenMethod _)
  byFunction(parenMethod) // Estas dos últimas son iguales el _ es innecesario, el compilador lo pone por nosotros
}
