package Hackerrank.Introduction

import scala.collection.mutable.ListBuffer

/**
  * Created by miguel.ludena on 18/09/2018.
  */
/*
You are given the cartesian coordinates of a set of points in a 2D plane. When traversed sequentially, these points form a
 Polygon P, , which is not self-intersecting in nature. Can you compute the perimeter of polygon ?

Input Format

The first line contains an integer, , denoting the number of points.
The  subsequent lines each contain  space-separated integers denoting the respective  and  coordinates of a point.

Constraints

No  points are coincident, and polygon  is obtained by traversing the points in a clockwise direction.
Output Format

For each test case, print the perimeter of  (correct to a scale of one decimal place).

Note: Do not add any leading/trailing spaces or units.

Sample Input

4
0 0
0 1
1 1
1 0
Sample Output

4
 */
object PerimeterOfPolygon extends App{

  //val inputData = io.Source.stdin.getLines().toList
  val inputData = io.Source.fromURL(getClass.getResource("/perimeterOfPolygon.txt")).getLines().toList
  val numberOfSides = inputData(0)
  val rawData = inputData.tail.map(x => (x.split(" ")(0).toInt, x.split(" ")(1).toInt))

  type Point = (Int, Int)


  def square(x: Double) = Math.pow(x, 2)
  def distance(x: Point, y: Point): Double = Math.sqrt(square(Math.abs(x._1 - y._1) + Math.abs(x._2 - y._2)))
  /*
  def calculatePerimeter(x : List[Product with Serializable]): Double = {
    if(x.length > 1) {println(s"Punto a: ${x.head.toString()} Punto b: ${x.tail.head.toString()} Hipotenusa: ${hipotenouse(x.head, x.tail.head)}");hipotenouse(x.head, x.tail.head) + calculatePerimeter(x.tail)}
    else 0
  }
  */

  def calcPerimeter(points: List[(Int, Int)]) = {
    def calcPerimeter_(points: List[(Int, Int)], acc: Double): Double = {
      points match {
        case Nil => acc
        case head :: Nil => acc
        case head :: tail => calcPerimeter_(tail, acc + distance(head, tail.head))
      }
    }
    calcPerimeter_(points, 0) + distance(points.head, points.last).toInt
  }


  println(calcPerimeter(rawData))



}
