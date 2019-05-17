package FuturesTrial

/**
  * Created by miguel.ludena on 17/12/2018.
  */

/*
 *This is a very basic code. The main program execute 2 tasks.
 * Each tasks does nothing but wait a few seconds to simulate some execution
 * (e.g. fetching something from a database or over the network, writing to a file, …).
 */
/*
Both tasks are executed sequentially on the same thread as the main program.
The first tasks takes one second to complete and the second task takes 2.
No doubt that we are wasting resources as the main thread should be able to continue execution while
the tasks are executed. Also assuming no dependencies between the tasks we should be able to run them concurrently.
 */
object BasicExample{

  def taskA(): Unit = {
    debug("Starting taskA")
    Thread.sleep(1000) //wait 1 secs
    debug("Finished taskA")
  }

  def taskB(): Unit = {
    debug("Starting taskB")
    Thread.sleep(2000) //wait 1 secs
    debug("Finished taskB")
  }

  def main(args: Array[String]): Unit = {
    debug("Starting Main")
    taskA()
    taskB()
    debug("Finished Main")
  }

  def debug(message: String): Unit = {
    val now = java.time.format.DateTimeFormatter.ISO_INSTANT
      .format(java.time.Instant.now()).substring(11,23) //keep only time component
    val thread = Thread.currentThread().getName
    println(s"$now [$thread] $message")
  }

}
