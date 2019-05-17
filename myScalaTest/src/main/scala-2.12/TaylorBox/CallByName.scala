package TaylorBox

/**
  * Created by miguel.ludena on 04/04/2019.
  */
object CallByName extends App{

  //EJEMPLO 1
  def timer[A](blockOfCode: => A) = {
    val startTime = System.nanoTime()
    val result = blockOfCode
    val stopTime = System.nanoTime()
    val delta = stopTime - startTime
    (result, delta/1000000d)
  }

  val (result, time) = timer(println("Hello"))
  println(s"Result: $result Time: $time")

  //EJEMPLO 2
  def test[A](codeBlock: => A) = {
    println("before 1st codeBlock")
    val a = codeBlock
    println(a)
    Thread.sleep(10)

    println("before 1st codeBlock")
    val b = codeBlock
    println(b)
    Thread.sleep(10)

    println("before 1st codeBlock")
    val c = codeBlock
    println(c)
    Thread.sleep(10)
  }

  test(System.currentTimeMillis())

  //EJEMPLO 3
  def f(x: => Int) = x * x
  var y = 0
  val z = f { y +=1; y}
  println(s"El resultado es: $z")
}
