package ScalaCourse1.Week1

/**
  * Created by miguel.ludena on 30/08/2018.
  */

/*
Write a recursive function that counts how many different ways you can make change for an amount, given a list of coin
denominations. For example, there are 3 ways to give change for 4 if you have coins with denomination 1 and 2: 1+1+1+1,
1+1+2, 2+2.

Do this exercise by implementing the countChange function inMain.scala. This function takes an amount to change, and a
list of unique denominations for the coins. Its signature is as follows:

def countChange(money: Int, coins: List[Int]): Int

Once again, you can make use of functions isEmpty, head and tail on the list of integers coins.

Hint: Think of the degenerate cases. How many ways can you give change for 0 CHF(swiss money)? How many ways can you
give change for >0 E, if you have no coins?
 */

object CountingChange extends App{

  def countChange(money: Int, coins: List[Int]): Int = {

    if(money == 0) 1
    else if(money > 0){
      if (coins.isEmpty) 0
      else countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
    else 0

  }

  println(countChange(5, List(1,2,5,10)))
}
