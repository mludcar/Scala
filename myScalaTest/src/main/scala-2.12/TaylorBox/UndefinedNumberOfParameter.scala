package TaylorBox

/**
  * Created by miguel.ludena on 13/10/2018.
  */
object UndefinedNumberOfParameter extends App{

  def printAll(strings: String*) {
    println("------")
    strings.foreach(println)
  }

  printAll()
  printAll("foo")
  printAll("foo", "bar")
  printAll("foo", "bar", "baz")

}
