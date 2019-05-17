package MasterPragsis

//Excercise
/*
  1. Create a class called Rational with two fields
  2. Create the set of normal operations on Rationals
    - add
    - neg
    - sub
    - less
    - max
  3.(OPTIONAL) Create a Greater Common Divisor
  HINT: Make a toString function could be a good idea
 */


class Rational(x: Int, y: Int) {
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
