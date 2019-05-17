package FuturesTrial

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by miguel.ludena on 17/12/2018.
  */
object AsynchronousComputation{

  def taskA(): Future[Unit] = Future {
    debug("Starting taskA")
    Thread.sleep(1000) // wait 1 sec
    debug("Finished taskA")
  }

  def taskB(): Future[Unit] = Future {
    debug("Starting taskB")
    Thread.sleep(2000) // wait 2 sec
    debug("Finished taskB")
  }

  def main(args: Array[String]): Unit = {
    debug("Starting Main")
    val futureA = taskA()
    val futureB = taskB()
    debug("Continuing Main...")
    //wait for both future to complete before exiting
    Await.result(futureA zip futureB, Duration.Inf)
  }

  def debug(message: String): Unit = {
    val now = java.time.format.DateTimeFormatter.ISO_INSTANT
      .format(java.time.Instant.now()).substring(11,23) //keep only time component
    val thread = Thread.currentThread().getName
    println(s"$now [$thread] $message")
  }

}
