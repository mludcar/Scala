package TaylorBox

/**
  * Created by miguel.ludena on 23/11/2018.
  */
object FunctionReturningFunction extends App{

    def saySomething(prefix: String) = (s: String) => {prefix + " " + s}

    val sayHello = saySomething("Que pasa")

    println(sayHello("Hulio"))

}
