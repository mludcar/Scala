package Hackerrank.Introduction

import scala.io.StdIn

/**
  * Created by miguel.ludena on 26/10/2018.
  * https://www.hackerrank.com/challenges/lambda-march-compute-the-area-of-a-polygon/problem
  */

object Solution {
  /*
  def area(a: IndexedSeq[Array[Double]]): Double = {
    val result = (1 until a.length).foldLeft(0.0) {
      (acc, i) => acc + a(i-1)(0)*a(i)(1) - a(i)(0)*a(i-1)(1)
    }
    math.abs(result) / 2
  }
  */
  case class Point(x: Double, y: Double)

  def main(args: Array[String]) {
    //val N = StdIn.readInt
    //val points = (1 to N).take(N)
                  //.map(_ => StdIn.readLine().split(" ").map(_.toDouble)).map{case Array(x,y) => Point(x, y)}
    //println(area(points :+ points.head))

    val inputData = io.Source.fromURL(getClass.getResource("/areaOfAPolygon.txt")).getLines().toList
    val numberOfSides = inputData.head.toInt
    val points = inputData.tail.map(_.split(" ").map(_.toDouble)).map{case Array(x,y) => Point(x, y)}.toIndexedSeq
    println(points)
    /*
    def showPoints[Array[Double]](points: List[Array[Double]]): Unit = points.head match {
      case n :: rest => {println("hola")
                showPoints(rest) }
      case _ => println("end")

    }
    */
    def area(data: IndexedSeq[Point]): Double = {
      val result = (1 until data.length).foldLeft(0.0){
        (x,y) => {
          println(s"x: $x y: $y data y: ${data(y)} data y-1: ${data(y-1)}")
          x + data(y-1).x*data(y).y - data(y).x*data(y-1).y
        }
      }
      math.abs(result) / 2
    }
    println(area(points :+ points.head))

  }
}