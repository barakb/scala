
/**
  * Created by Barak Bar Orion
  * on 7/21/16.
  *
  * @since 12.0
  */


object Tree2 {


  sealed abstract class Tree[+A] {
    def map[B](f: A => B): Tree[B] = this match {
      case Node(v, l, r) => Node(f(v), l.map(f), r.map(f))
      case Empty => Empty
    }
  }

  case class Node[A](value: A, left: Tree[A], right: Tree[A]) extends Tree[A]

  object Empty extends Tree[Nothing]


  def main(args: Array[String]): Unit = {
    val intTree: Tree[Int] = Node[Int](1, Empty, Empty)
    val anyTree: Tree[Any] = Node[Any]("foo", intTree, Empty)
  }

}




