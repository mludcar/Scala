package Manning.Part1IntroductionFunctionalProgramming.DataStructures

/**
  * Created by miguel.ludena on 11/04/2019.
  */
sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object PatternMatchingTree extends App{

  // EXERCISE 3.25
  // Write a function size that counts the number of nodes(leaves and branches) in a tree
  def size[A](tree: Tree[A]): Int = tree match {
    case Leaf(_) => 1
    case Branch(l,r) => 1 + size(l) + size(r)
  }
  println(size(Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))))

  // EXERCISE 3.26
  // Write a function maximum that returns the maximum element in a Tree[Int]. (Note: In Scala, you can use x.max(y)
  // or x max y to compute the maximum of two integers x and y)
  def maximum(tree: Tree[Int]): Int = {
    def loop(tree: Tree[Int], acc: Int): Int = tree match {
        case Leaf(x) => x max acc
        case Branch(l, r) => loop(l, acc) max loop(r, acc)
      }
    loop(tree, 0)
  }
  println(maximum(Branch(Branch(Leaf(1), Leaf(99)), Leaf(3))))

  // EXERCISE 3.27
  // Write a function depth that returns the maximum path length from the root of a tree to any leaf
  def depth[A](tree: Tree[A]): Int = {
    def loop(tree: Tree[A], acc: Int): Int = tree match {
      case Leaf(x) => acc + 1
      case Branch(l,r) => loop(l, acc + 1) max loop(r, acc + 1)
    }
    loop(tree, 0)
  }
  println(depth(Branch(Branch(Leaf(1), Leaf(99)), Branch(Leaf(3), Leaf(Nil)))))

  // EXERCISE 3.28
  // Writea function map, analogous to the method of the same name on List, that modifies each element in a tree with
  // a given function
  def map[A,B](tree: Tree[A])(f: A => B): Tree[B] = tree match {
    case Leaf(x) => Leaf(f(x))
    case Branch(l,r) => Branch(map(l)(f), map(r)(f))
  }
  println(map(Branch(Branch(Leaf(1), Leaf(99)), Branch(Leaf(3), Leaf(2))))(_ + 1))
}
