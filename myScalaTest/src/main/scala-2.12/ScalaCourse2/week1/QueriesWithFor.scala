package ScalaCourse2.week1

/**
  * Created by miguel.ludena on 12/09/2018.
  */
case class Book(title: String, authors: List[String])

object QueriesWithFor extends App{

  val books = Set(
    Book(title = "Structure and Interpretation of Computer Programs",
      authors = List("Abelson, Harald", "Sussman, Gerald J.")),
    Book(title = "Introduction to Functional Programming",
      authors = List("Bird, Richard", "Wadler, Phil")),
    Book(title = "Effective Java",
      authors = List("Bloch, Joshua")),
    Book(title = "Java Puzzlers",
      authors = List("Bloch, Joshua", "Gafter, Neal")),
    Book(title = "Programming in Scala",
      authors = List("Odersky, Martin", "Spoon, Lex", "Venners, Bill")))

  //Find titles f boojs whose author´s name is "Bird"
  for(b <- books; a <- b.authors if a.startsWith("Bird")) yield println(b.title)

  //Find all the books which have the word "Program in the title"
  for(b <- books if b.title.indexOf("Program") <= 0) yield println(b.title)

  //Find names of all authors who have written at least two books present in the database
 val a = {for {
    b1 <- books
    b2 <- books
    if b1 != b2
    a1 <- b1.authors
    a2 <- b2.authors
    if a1 > a2
  }yield a1}

  println(a)

  //F
}
