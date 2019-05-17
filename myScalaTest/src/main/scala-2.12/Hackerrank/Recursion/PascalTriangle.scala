package Hackerrank.Recursion

/**
  * Created by miguel.ludena on 26/08/2018.
  */
object PascalTriangle {

  def pascal(row: Int, column: Int): Int = {
    if(column == 0 || row == column) 1
    else pascal(row -1, column -1) + pascal(row - 1, column)
  }

  def main(args: Array[String]): Unit = {
    val count = io.Source.stdin.getLines().toList.map(_.trim).map(_.toInt).head
    for (row <- 0 to count -1){
      for (col <- 0 to row) print(s" ${pascal(row, col)} ")
    println()
    }
  }


}
