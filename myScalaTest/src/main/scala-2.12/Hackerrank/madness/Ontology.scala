package Hackerrank.madness

/**
  * Created by miguel.ludena on 10/10/2018.
  */
object Ontology extends App{

  val lines = s"""6
                |Animals ( Reptiles Birds ( Eagles Pigeons Crows ) )
                |5
                |Reptiles: Why are many reptiles green?
                |Birds: How do birds fly?
                |Eagles: How endangered are eagles?
                |Pigeons: Where in the world are pigeons most densely populated?
                |Eagles: Where do most eagles live?
                |4
                |Eagles How en
                |Birds Where
                |Reptiles Why do
                |Animals Wh""".stripMargin.split("\n")

  println(lines.mkString("\n"))
  println(lines(1))
}
