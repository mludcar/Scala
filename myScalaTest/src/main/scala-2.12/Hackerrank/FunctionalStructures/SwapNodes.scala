package Hackerrank.FunctionalStructures

/**
  * Created by miguel.ludena on 12/09/2018.
  */
object SwapNodes extends App{

}
/*
trait BinaryTree[T] {

  def isEmpty: Boolean

  def show(node: BinaryTree[T]): String=
    node match {
      case n: NonEmptyNode[T] => if(n.leftTree.isEmpty){print(n.value); show(n.rightTree)} else show(n.rightTree)
      case e: EmptyNode[T] =>
    }
}

class EmptyNode[T] extends BinaryTree[T] {
  def isEmpty = true
  def incl(x: T) = new NonEmptyNode[T](x, new EmptyNode[T], new EmptyNode[T])
}

case class NonEmptyNode[T](value: T, leftTree: BinaryTree[T], rightTree: BinaryTree[T]) extends BinaryTree[T]{
  def isEmpty = false
}
*/