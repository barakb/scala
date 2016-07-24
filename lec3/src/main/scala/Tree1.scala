/**
  * Created by Barak Bar Orion
  * on 7/24/16.
  *
  * @since 12.0
  */
object Tree1 {
    sealed abstract class Tree[A]
    case class Node[A](value : A, left : Tree[A], right : Tree[A]) extends Tree[A]
    class ET[A] extends Tree[A]


    def main(args: Array[String]): Unit = {
      val intTree : Tree[Int] = Node[Int](1, new ET(), new ET())
      // does  Node[Int](1, new ET(), new ET()) == Node[Int](1, new ET(), new ET()) ?

      // can we assign to Tree[Any] node  a Node[String] ?
//      val anyTree : Tree[Any] = Node[Any]("foo", intTree, new ET())

    }



}
