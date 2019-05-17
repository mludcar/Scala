package ScalaAdvanced.lectures.Part3concurrency

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
  * Created by miguel.ludena on 02/05/2019.
  */
object FuturesTrial extends App{
  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife // calculates the meaning of life on ANOTHER thread
  } // (global) which is passed by the compiler

  // Ai intentas sacar el dato del futuro sin que se haya obtenido el dato, te devolvera un None
  //println(aFuture.value) // Option[Try[Int]]

  // El onComplete (u otras funciones de callback) se llama

  //println(aFuture.value.get.get)
  //println(aFuture.isCompleted)
  aFuture.onComplete( t => t match {
    case Success(meaningOfLife) => println(s"the meaning of life is $meaningOfLife")
    case Failure(e) => println(s"I have failed with $e")
  })

  Await.ready(aFuture, Duration.Inf)
  // Si no damos tiempo a que se resuelva el hilo del futuro el onComplete no se resolvera
  // Soluciones
  // 1. Poner un sleep en el hilo principal(gitanada)
  // Thread.sleep(10000)
  // 2. Usa Await => Solucion wena



}
