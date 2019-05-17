package ScalaCourse1.Week2

/**
  * Created by miguel.ludena on 03/09/2018.
  */

object Rationals extends App{
  val x = new Rational(1,3)
  val y = new Rational(5,7)
  val z = new Rational(3,2)
  val w = new Rational(1)

}

class Rational(x: Int, y: Int){

  require(y != 0, "denominator must be different than zero")

  private def gcd(a: Int, b: Int): Int = if(b == 0) a else gcd(a, a % b)

  val numer = x
  val denom = y


  def this(x: Int) = this(x, 1)

  def add(that: Rational): Rational = new Rational(this.numer * that.denom + that.numer * this.denom, this.denom * that.denom)

  def toString(r: Rational) = this.numer / gcd(this.numer, this.denom) + "/" + this.denom  / gcd(this.numer, this.denom)

  def neg = new Rational(-numer, denom)

  def sub(that: Rational): Rational = add(that.neg)

  def less(that: Rational) = this.numer * that.denom < that.numer * this.denom

  def max(that: Rational) = if( this.less(that)) that else this
}
