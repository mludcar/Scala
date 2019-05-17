package ScalaCourse2.week2

/**
  * Created by miguel.ludena on 01/11/2018.
  */
object PouringWaterTest extends App{

  val problem = new Pouring(Vector(4, 9))
  problem.moves

  println(problem.pathSets.take(3).toList)

  problem.solutions(6)

}
