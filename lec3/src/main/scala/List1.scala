
/**
  * Created by Barak Bar Orion
  * on 7/24/16.
  *
  * @since 12.0
  */
object List1 {

  sealed abstract class List[+A] {
  }

  case class Cons[A](head: A, tail: List[A]) extends List[A]

  object Nil extends List[Nothing] {
    override def toString: String = "Nil"
  }

  object List {
    def apply[A](el: A*) : List[A] = el.foldRight(Nil: List[A]) { (h, tl) => Cons(h, tl) }
  }

}
