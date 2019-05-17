package ScalaCourse1.Week5

/**
  * Created by miguel.ludena on 07/09/2018.
  */
object MergeSortExample {
  def msort(xs: List[Int]): List[Int] = {
    val n = xs.length/2
    if(n == 0) xs
    else{
      def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match{
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if(x < y) x :: merge(xs1, ys1)
          else y :: merge(xs, ys1)

      }
      val (fstd, snd) = xs.splitAt(n)
      merge(msort(fstd), msort(snd))
    }
  }
  
}
