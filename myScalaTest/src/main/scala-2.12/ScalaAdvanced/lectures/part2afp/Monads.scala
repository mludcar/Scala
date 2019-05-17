package ScalaAdvanced.lectures.part2afp

/**
  * Created by miguel.ludena on 08/04/2019.
  */
object Monads extends App{

  // our own Try Monad
  trait Attempt[+A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }
  object Attempt {
    def apply[A](a: => A): Attempt[A] =
      try {
        Success(a)
      } catch {
        case e: Throwable => Fail(e)
      }
  }

  case class Success[+A](value: A) extends Attempt[A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B] =
      try {
        f(value)
      }catch {
        case e: Throwable => Fail(e)
      }
  }

  case class Fail(e: Throwable) extends Attempt[Nothing] {
    def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = this
  }

  /*
    left-identity
    unit.flatMap(f) = f(x)
    Attempt(x).flatMAp(f) = f(x) //Succes case!
    Success(x).flatMap(f) = f(x) // proved

    right-identity
    attempt.flatMap(unit) = attempt
    Success(x).flatMap(x => Attempt(x)) = Attempt(x) = Success(x)
    Fail(_).flatMap(...) = Fail(e)

     associativity
     attempt.flatMap(f).flatMap(g) == sttempt.flatMap(x => f(x).flatMap(g))
     Fail(e).flatMap(f).flatMap(g) = Fail(e)
     Fail(e).flatMap(x => f(x).flatMap(g)) = Fail(e)

     Succes(v).flatMAp(f).flatMap(g) =
      f(v).flatMAp(g) OR Fail(e)

     Success(v).flatMap(x => f(x).flatMap(g)) =
      f(v).flatMap(g) OR Fail(e)
   */
  val attempt = Attempt {
    throw new RuntimeException("My own Monad, yes!")
  }

  println(attempt)

  /*
    EXERCISE:
    1) Implement a Lazy[T] monad = computation which will only be executed when it´s needed
      unit/apply
      flatMAp

    2) Monads = unit + flatMap
       Monads = unit + map + flatten

       Monad[T] {

          def flatMap[B](f: T => Monad[B]): Monad[B] = ... (implemented)

          def map[B](f: T => B): Monad[B] = ???
          def flatten(m: Monad[Monad[T]]): Monad[T] = ???

          (have List in minf)
       }
   */

  // 1 - Lazy Monad
  // El para metro byName evita que se evalue value durante la construccion del objeto
  class Lazy[+A](value: => A){
    // call by need, creamos un atributo interno para que asi no tengamos que evaluar si ya ha sido evaluado una vez
    private lazy val internalValue = value
    // normal def use: A = value
    def use: A = internalValue
    def flatMap[B](f: (=>A) => Lazy[B]): Lazy[B] = f(internalValue) //para hacer que el parametro tb sea lazy lo pasamos byName
  }
  object Lazy {
    def apply[A](value: => A): Lazy[A] = new Lazy(value) // unit
  }

  val lazyInstance = Lazy {
    println("Today I don´t feel like doing anything")
    42
  }
  //println(lazyInstance.use)

  val flatMappedInstance = lazyInstance.flatMap(x => Lazy{
    10 * x
  })

  val flatMappedInstance2 = lazyInstance.flatMap(x => Lazy{
    10 * x
  })
  flatMappedInstance.use
  flatMappedInstance2.use

  /*
   left-identity
   unit.flatMao(f) = f(v)
   lazy(v).flatMap(f) = f(v)

   right-identity
   l.flatMap(unit) = l
   Lazy(v).flatMap(x => Lazy(x)) = Lazy(v)

   associativity: l.flatmap(f).flatMap(g) = l.flatMAp(x => f(x).flatMap(g))

   Lazy(v).flatMap(f).flatMap(g) = f(v).flatMap(g)
   Lazy(v).flatMap(x => f(x).flatMap(g)) = f(v).flatMap(g)
   */

  // 2: map and flatten in terms of flatMap
  /*
    Monad[T] { //List
          def flatMap[B](f: T => Monad[B]): Monad[B] = ... (implemented)

          def map[B](f: T => B): Monad[B] = flatMap(x => unit(f(x))) //Monad[B]
          def flatten(m: Monad[Monad[T]]): Monad[T] = m.flatMAp((x: Monad[T]) => x)

          List(1,2,3).map(_ * 2) = List(1,2,3).flatMap(x => List(x * 2))
          List(List(1,2), List(3,4)).flatten = List(List(1,2), List(3,4)).flatMap(x => x) = List(1,2,3,4)
       }
   */
}
