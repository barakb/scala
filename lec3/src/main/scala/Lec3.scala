
/**
  * Created by Barak Bar Orion
  * on 7/24/16.
  *
  * @since 12.0
  */
object Lec3 {

  sealed abstract class Option[+A] {
    def map[B](f: A => B): Option[B] = this match {
      case None => None
      case Some(x) => Some(f(x))
    }

    def getOrElse[B >: A](default: => B): B = this match {
      case None => default
      case Some(x) => x
    }
  }

  object None extends Option[Nothing] {
    override def toString = "None"
  }

  case class Some[A](get: A) extends Option[A]

  case class Employee(name: String, department: String)


  sealed abstract class List[+A] {

  }

  object Nil extends List[Nothing]

  case class Cons[A](head: A, tail: List[A]) extends List[A]

  object List {


    def apply[A](a: A*): List[A] = {
      def combiner(h: A, tl: List[A]) = Cons(h, tl)
      (a foldRight (Nil: List[A])) (combiner)
    }
  }

  def main(args: Array[String]): Unit = {
    val v: Option[AnyRef] = Some[String]("foo")
    val v1: Option[String] = None

    println(None == None)

    println(None match { case None => "None"; case _ => "not match" })

    println(Some(Some(None)))

  }

  type Foo[-A] = (A) => Unit
  var fa: Foo[String] = (s: String) => "foo" + s
  var far: Foo[AnyRef] = (ar: AnyRef) => println(ar)

  var far1: Foo[String] = far
  //  var far1 :Foo[AnyRef] = fa
}
