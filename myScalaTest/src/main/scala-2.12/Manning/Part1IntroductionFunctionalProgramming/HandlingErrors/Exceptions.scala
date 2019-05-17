package Manning.Part1IntroductionFunctionalProgramming.HandlingErrors

/**
  * Created by miguel.ludena on 19/04/2019.
  */
object Exceptions extends App{

  // POSSIBLE ALTERNATIVES TO EXCEPTIONS
  // Seq is a common interface of various linear sequence-like collections
  def mean(xs: Seq[Double]): Double = {
    if(xs.isEmpty) throw new ArithmeticException("mean of empty list")
    else xs.sum / xs.length
    // sum is defined as a method on Seq only if the elements of the sequence are numeric.
    // The standard library accomplishes this trick with implicits
  }
  // mean function is an example of what´s called a partial function: it´s not defined for some inputs

  // FIRST POSSIBILITY
  // The first possibility is to return some sort of bogus value of type Double. We could simply
  // return xs.sum / xs.length in all cases, and have it result in 0.0/0.0 when the input is empty, which is
  // Double.NaN; or we could return some other sentinel value. In other situations, we might return null instead of a
  // value of the needed type. This general class of approaches is how error handling is often done in languages without
  // exceptions
  // BAD: It allows errors to silently propagate, It’s not applicable to polymorphic code. For some output
  // types, we might not even have a sentinel value of that type even if we wanted to!
  def mean2(xs: Seq[Double]): Double = {
    if(xs.isEmpty) 0.0
    else xs.sum / xs.length
  }

  // SECOND POSSBILITY
  // The second possibility is to force the caller to supply an argument that tells us what to do in case we don’t know
  // how to handle the input
  // BAD: This makes mean into a total function, but it has drawbacks—it requires that immediate callers have direct
  // knowledge of how to handle the undefined case and limits them to returning a Double
  def mean_1(xs: IndexedSeq[Double], onEmpty: Double): Double ={
    if (xs.isEmpty) onEmpty
    else xs.sum / xs.length
  }




}
