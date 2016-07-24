
/**
  * Created by Barak Bar Orion
  * on 7/21/16.
  *
  * @since 12.0
  */


object List2 {


  sealed abstract class List[+A] {

    def ::[T >: A](head: T): List[T] = new ::(head, this)

    def map[B](f: A => B): List[B] = this match {
      case head :: tail => new ::(f(head), tail.map(f))
      case Nil => Nil
    }

    def foldLeft[Z](zero: Z)(combine: (Z, A) => Z): Z = this match {
      case head :: tail => tail.foldLeft(combine(zero, head))(combine)
      case Nil => zero
    }

    def mkString(d: String): String = {
      val buf = this.foldLeft(new StringBuilder) { (sb, s) => if (sb.isEmpty)
        sb append "(" append s
      else
        sb append d append s
      } append ")"
      buf.toString
    }

    override def toString = this.mkString(", ")
  }


  case class ::[A](head: A, tail: List[A]) extends List[A]

  object Nil extends List[Nothing] {
    override def toString: String = "Nil"
  }

  object List {
    def apply[A](a: A*) = a.foldRight(Nil: List[A]) { (first, rest) => first :: rest }

    def unapplySeq[A](l: List[A]): Option[Seq[A]] = {
      import scala.collection.immutable.{List => ScalaList}
      import scala.collection.immutable.{:: => ScalaCons}
      def combine(z: ScalaList[A], a: A): ScalaList[A] = ScalaCons(a, z)
      if (l == Nil) None else Some(l.foldLeft(ScalaList(): ScalaList[A])(combine))
    }
  }
}




