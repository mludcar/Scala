package ScalaAdvanced.lectures.Part3concurrency

import java.util.concurrent.Executors

/**
  * Created by miguel.ludena on 29/04/2019.
  */
object Intro extends App{

  /*
  interface Runnable {
    public void run()
  }
   */
  // JVM threads
  /*val aThread = new Thread(new Runnable {
    override def run() = println("Running in parallel")
  })

  val runnable = new Runnable {
    override def run() = println("Running in parallel")
  }

  aThread.start() // gives the signal to the JVM to start a JVM thread
  // create a JVM thread => OS thread
  runnable.run() // doesn´t do anything in parallel!!!
  aThread.join() // blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach( _ => println("hello")))
  val threadGoodBye = new Thread(() => (1 to 5).foreach( _ => println("GoodBye")))

  threadHello.start()
  threadGoodBye.start()*/
  // different runs produce different results!!
  // threads scheduling depends on a number of factors including the operating system and the JVM implementation

  // EXECUTORS
  // In the JVM is very expensive to start and kill threads, the best solution is to reuse them.
  // executors => easy way to reuse threads(Java Library)
/*  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the thread pool"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("done after 1 second")
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("done after 2")
  })*/

/*  pool.shutdown() // Interrupts the pool but all the runnable executing will not be interrupted
  //pool.execute(() => println("should not appear")) // throws an exception in the calling thread, cause pool is closed

  //pool.shutdownNow() // Interrupts the pool and the runnning threads

  println(pool.isShutdown)*/

  // RACE CONDITION
  def runInParallel = {
    var x = 0

    val thread1 = new Thread(() => {
    x=1
    })

    val thread2 = new Thread(() => {
      x=2
    })

    thread1.start()
    thread2.start()
    println(x)
  }

  //for(_ <- 1 to 10000) runInParallel

  // race condition -> two threads are attempting to set the same memory zone at the same time, BIG RISK

  class BankAccount(@volatile var amount: Int) {
    override def toString: String = "" + amount
  }

  def buy(account: BankAccount, thing: String, price: Int) = {
    account.amount -= price
    /*println("I´ve bought " + thing)
    println("My account is now " + account)*/
  }

  /*for(_ <- 1 to 10000) {
    val account = new BankAccount(50000)
    val thread1 = new Thread(() => buy(account, "shoes", 3000))
    val thread2 = new Thread(() => buy(account, "iphone12", 4000))

    thread1.start()
    thread2.start()
    Thread.sleep(10)
    if(account.amount != 43000) println("AHA: " + account.amount)
    //println()
  }*/

  /*
      thread1(shoes): 50000
        - account = 50000 - 3000 = 47000
      thread2(iphone): 50000
        - account = 50000 - 4000 = 46000 overwrites the memory of account.amount
   */

  // SOLUTIONS
  // option #1: use synchronized()
  def buySafe(account: BankAccount, thing: String, price: Int) = {
    account.synchronized {
      //synchronized => no two threads can evaluate this at the same time
      account.amount -= price
      println("I´ve bought " + thing)
      println("My account is now " + account)
    }
  }

  //option #2: use @volatile

  /*
      EXERCISES

      1) Construct 50 "inception" threads
          Thread1 -> thread2 -> thread3 -> ....
          println("hello from thread #3")
          in REVERSE ORDER
   */

  def inception( maxThread: Int, i: Int = 1): Thread = new Thread(() => {
    if (maxThread > i){
      val newThread = inception(maxThread, i + 1)
      newThread.start()
      newThread.join()
    }
    println(s"hello from thread #$i")
  })

  //inception(50).start()

  /*
      2)
   */
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  /*
    1) what is the biggest value possible for x? - 100
    2) what is the smallest value possible for x? - 1
   */
  //threads.foreach(_.join())
  //println(x)

  /*
      3 sleep fallacy
   */
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })

  message = "Scala sucks"
  awesomeThread.start()
  //Thread.sleep(2001)
  awesomeThread.join()
  println(message)
  /*
      what´s the value of message? almost always "Scala is awesome"
      is it guaranteed? NO!
      why? why not?

      (main thread)
        message = "Scala sucks"
        awesomeThread.start()
        sleep() - relieves execution
      (awesome thread)
        sleep() - relieves execution
      (OS gives the CPU to some important thread - takes CPU for more than 2 seconds)
      (OS gives the CPU to the MAIN thread)
        println("Scala sucks")
      (OS gives the CPU to awesomeThread)
        message = "Scala is awesome"

      ¡¡ The sleep() method doesn´t guarantees the exact time, only guarantees at least this time!!
      if we change main thread sleep the answer could change for example 1001
   */

  // How do we fix it??
  // synchronizing doesn´t work -> synchronizing only works or is only useful for concurrent modifications, and this
  // is a sequential problem
  // Sequential problem solution -> join, join makes main thread to wait to the awesomeThread
}
