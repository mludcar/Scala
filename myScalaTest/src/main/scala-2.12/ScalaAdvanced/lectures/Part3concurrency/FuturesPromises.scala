package ScalaAdvanced.lectures.Part3concurrency

import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success}
import scala.concurrent.duration._

// important for futures
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by miguel.ludena on 08/04/2019.
  */
object FuturesPromises extends App{

  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife // calculates the meaning of life on ANOTHER thread
  } // (global) which is passed by the compiler

  println(aFuture.value) // Option[Try[Int]]

  println("waiting on the future")
  // La función que admite onComplete es una funcion parcial
  aFuture.onComplete( t => t match {
    case Success(meaningOfLife) => println(s"the meaning of life is $meaningOfLife")
    case Failure(e) => println(s"I have failed with $e")
  })

  Thread.sleep(3000)

  //mini social network

  case class Profile(id: String, name: String){
    def poke(anotherProfile: Profile) = println(s"${this.name} poking ${anotherProfile.name}")
  }

  object SocialNetwork {
    // "database"
    val names = Map(
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill",
      "fb.id.0-dummy" -> "Dummy"
    )
    val friends = Map(
      "fb.id.1-zuck" -> "fb.id.2-bill"
    )

    val ramdom = new Random()

    //API
    def fetchProfile(id: String): Future[Profile] = Future {
      Thread.sleep(ramdom.nextInt(300))
      Profile(id, names(id))
    }
    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(ramdom.nextInt(400))
      val bfId = friends(profile.id)
      Profile(bfId, names(bfId))
    }

  }

  // client: mark to poke bill
  val mark = SocialNetwork.fetchProfile("fb.id.1-zuck")
/*  mark.onComplete{
    case Success(markProfile) => {
      val bill = SocialNetwork.fetchBestFriend(markProfile)
      bill.onComplete{
        case Success(billProfile) => markProfile.poke(billProfile)
        case Failure(e) => e.printStackTrace()
      }
    }
    case Failure(ex) => ex.printStackTrace()
  }*/



  // functional composition of futures (RECOMENDED)
  // map, flatMap, filter
  val nameOnTheWall = mark.map(profile => profile.name)
  val markBestFriend = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile))
  val zuckBestFriendRestricted = markBestFriend.filter(profile => profile.name.startsWith("Z"))

  //for-comprehensions
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriend(mark)
  } mark.poke(bill)

  Thread.sleep(1000)

  // fallbacks
  val aProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recover {
    case e: Throwable => Profile("fb.id.0-dummy", "Forever Alone")
  }

  val aFetchedProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recoverWith{
    case e: Throwable => SocialNetwork.fetchProfile("fb.id.0-dummy")
  }

  val fallbackResult = SocialNetwork.fetchProfile("unknown id").fallbackTo(SocialNetwork.fetchProfile("fb.id.0-dummy"))

  // online banking app
  case class User(name: String)
  case class Transaction(sender: String, receiver: String, amount: Double, status: String)

  object BankingApp {
    val name = "Rocke the JVM banking"

    def fetchUser(name: String): Future[User] = Future {
      // simulate fetching from DB
      Thread.sleep(500)
      User(name)
    }

    def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
      // simulate some processes
      Thread.sleep(1000)
      Transaction(user.name, merchantName, amount, "SUCCESS")
    }

    def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
      // fetch the user from the DB => Future
      // create a transaction => Future
      // WAIT for the transaction to finish

      val transactionStatusFuture = for {
        user <- fetchUser(username)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status

      Await.result(transactionStatusFuture, 2.seconds) // implicit conversions -> pimp my library
                                                       // block until futures are completed
    }
  }
  println(BankingApp.purchase("Miguel", "3 Kilos de Estramonio", "Ramon de Pitis", 60))

  // promises

  val promise = Promise[Int]() // "controller" over a future
  val future = promise.future

  // Thread 1 - "consumer"
  future.onComplete {
    case Success(r) => println("[consumer] I´ve received " + r)
  }

  // Thread 2 - "producer"
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(1000)
    //"fulfilling" the promise
    promise.success(42)
    println("[producer] done")
  })

  producer.start()
  Thread.sleep(1000)

  /*
    1) fulfill a future INMEDIATLY with a value
    2) inSequence(fa,fb)
    3) first(fa, fb) => new future with the first value of the two futures
    4) last(fa, fb) => new future with the last value
    5) retryUntil[t](action: () => Future[T], condition: T => Boolean): Future[T]
   */

  // 1 - fulfill inmediatly
  def fulfillInmediately[T](value: T): Future[T] = Future(value)
  // 2 - insequence
  def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] = first.flatMap(_ => second)

  // 3 - first out of two Futures
  /*
  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {

  }
  */

}
