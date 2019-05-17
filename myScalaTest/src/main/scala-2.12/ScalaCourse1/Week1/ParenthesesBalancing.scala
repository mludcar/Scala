package ScalaCourse1.Week1

/**
  * Created by miguel.ludena on 30/08/2018.
  */

/*
Write a recursive function which verifies the balancing of parentheses in a string, which we represent as a List[Char]
not a String. For example, the function should return true for the following strings:

(if (zero? x) max (/ 1 x))
I told him (that it’s not (yet) done). (But he wasn’t listening)
The function should return false for the following strings:

:-)
())(
The last example shows that it’s not enough to verify that a string contains the same number of opening and closing
parentheses.

Do this exercise by implementing the balance function in Main.scala. Its signature is as follows:

def balance(chars: List[Char]): Boolean

There are three methods on List[Char] that are useful for this exercise:

chars.isEmpty: Boolean returns whether a list is empty
chars.head: Char returns the first element of the list
chars.tail: List[Char] returns the list without the first element
Hint: you can define an inner function if you need to pass extra parameters to your function.

Testing: You can use the toList method to convert from a String to aList[Char]: e.g. "(just an) example".toList.
 */
object ParenthesesBalancing extends App{

  def balance(chars: List[Char]): Boolean = {

    def balanceAux(open: Int, chars: List[Char]): Boolean = {

      if(open < 0)  false
      else if(chars.isEmpty && open == 0) true
      else if(chars.isEmpty && open != 0) false
      else if(chars.head == '(') balanceAux(open + 1, chars.tail)
      else if(chars.head == ')') balanceAux(open - 1, chars.tail)
      else balanceAux(open, chars.tail)
    }

    balanceAux(0, chars)
  }

  println(balance("())(".toList))
}
