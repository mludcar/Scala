package FuturesTrial

import java.util.concurrent.Executors

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
//import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by miguel.ludena on 17/12/2018.
  */
object AsynchronousComputation2 {

  //Creación de executor propio
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))

  def startTask(number: Int): Future[Unit] = Future {
    debug(s"Starting task#$number")
    Thread.sleep(2000)
    debug(s"Finished task#$number")
  }

  def main(args: Array[String]): Unit = {
    debug("Starting Main")
    val tasks = Future.traverse(1 to 20)(startTask)
    debug("Continuing Main...")
    Await.result(tasks, Duration.Inf)
  }

  def debug(message: String): Unit = {
    val now = java.time.format.DateTimeFormatter.ISO_INSTANT
      .format(java.time.Instant.now()).substring(11,23) //keep only time component
    val thread = Thread.currentThread().getName
    println(s"$now [$thread] $message")
  }
}
