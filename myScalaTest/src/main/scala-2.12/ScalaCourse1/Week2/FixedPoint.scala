package ScalaCourse1.Week2

/**
  * Created by miguel.ludena on 03/09/2018.
  */
object FixedPoint extends App{
  val tolerance = 0.0001

  def abs(x: Double): Double = if(x < 0) -x else x
  def isCloseEnough(x: Double, y: Double) = abs((x - y)/ x) / x < tolerance

  def fixedPoint(f: Double => Double)(firstGuess: Double) = {
    def iterate(guess: Double): Double = {
      val next = f(guess)
      if(isCloseEnough(guess, next)) next
      else iterate(next)
    }
    iterate(firstGuess)
  }

  println(fixedPoint(x => 1 + x/2)(1))

  def sqrt(x: Double) = fixedPoint(avgDamp(y => x / y))(1)

  def avgDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2


}
