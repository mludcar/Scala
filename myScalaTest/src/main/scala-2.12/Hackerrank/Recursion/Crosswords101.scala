package Hackerrank.Recursion

/**
  * Created by miguel.ludena on 29/08/2018.
  */
object Crosswords101 extends App{

  val myResource = io.Source.fromURL(getClass.getResource("/testFile.txt"))
  val firstArray = myResource.getLines().toArray

  val words = firstArray.last.split(";")
  val charArray = firstArray.map(_.toCharArray)
  var names = Array[String]()
  var emptyPlaces = Array[Chord]()

  println(words.mkString(" "))

  for(i <- 0 until firstArray.length){
    if(firstArray(i).count(_ == '-') > 1) println("Hay palabras en esta fila")
  }

  val horizontalChords = horizontalSearch(firstArray)


  /*
  for(i <- 0 until charArray.length - 1){
    println(charArray(i).mkString(""))

    for(j <- 0 until charArray(i).length -1){
      if(charArray(i).count("-") > 1) horizontalSearch(charArray(i), i, j)
    }
  }
  */
  def horizontalSearch(arr: Array[String]): Array[Chord] = {

    var arr = Array[Chord]()
    for(i <- 0 until arr.length) arr +: horizontalSearchAux(arr(i).toString, 0,0)

    def horizontalSearchAux(line: String, pos: Int, acc: Int): Array[Option[Chord]] = {
      var returningArray = Array[Option[Chord]]()

      for (i <- pos until line.size) {
        if (line(i) == "-" && i != line.size) horizontalSearchAux(line, i, acc + 1)
        else if(line(i) != "-" && acc > 1) {returningArray :+ Option(Chord(i - acc, i)); horizontalSearchAux(line, i, 0)}
        else if(line(i) == "i" && i == line.size) returningArray :+ Option(Chord(i - acc, i))
      }
      returningArray.filter(_.isDefined)
    }
    for(i <- 0 until arr.length) println(s"Coordenadas (${arr(i).row.toString},${arr(i).column.toString})")
    arr
  }


}

case class Chord(row: Int, column: Int)
