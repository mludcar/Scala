package ScalaCourse1.Week4

/**
  * Created by miguel.ludena on 05/09/2018.
  */
abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def successor: Nat = new Succ(this)
  def + (that: Nat): Nat
  def - (that: Nat): Nat
}

object Zero extends Nat {
  def isZero: Boolean = true
  def predecessor: Nat = this
  def + (that: Nat): Nat = that
  def - (that: Nat): Nat = if(that.isZero) this else throw new Error("negative")
}

class Succ(n: Nat) extends Nat {
  def isZero = false
  def predecessor = n
  def + (that: Nat) = new Succ(n + that)
  def - (that: Nat) = if(that.isZero) this else new Succ(n - that.predecessor)
}
