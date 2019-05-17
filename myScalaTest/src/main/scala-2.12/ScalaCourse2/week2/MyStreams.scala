package ScalaCourse2.week2

/**
  * Created by miguel.ludena on 01/11/2018.
  */
object MyStreams extends App{

  def from(n: Int): Stream[Int] = n #:: from(n + 1)

  val nats = from(0)
  val m4s = nats map (_ * 4)

  println((m4s take 100).mkString(" "))

  //Función basada en la criba de Erastotenes
  def sieve(s: Stream[Int]): Stream[Int] =
    s.head #:: sieve(s.tail filter (_ % s.head != 0))

  val primes = sieve(from(2))
  val n = 2

  println(primes.take(n).toList.apply(n - 1).toString)

  def sqrtStream(x: Double): Stream[Double] = {
    def improve(guess: Double) = (guess + x / guess) / 2
    lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
    guesses
  }

  println(sqrtStream(4).take(10).toList.mkString(" "))

  def isGoodEnough(guess: Double, x: Double) =
    math.abs((guess * guess -x) / x) < 0.0001

  println(sqrtStream(4) filter (isGoodEnough(_, 4)))
}
