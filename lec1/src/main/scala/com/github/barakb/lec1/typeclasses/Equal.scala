package com.github.barakb.lec1.typeclasses

trait Equal[A] {
  def equal(a1: A, a2: A): Boolean

  object Laws {
    //noinspection SpellCheckingInspection
    def reflexiv(a: A): Boolean = equal(a, a)

    def symmetric(a1: A, a2: A): Boolean = equal(a1, a2) == equal(a2, a1)

    def transitive(a1: A, a2: A, a3: A): Boolean = if (equal(a1, a2) && equal(a2, a3)) equal(a1, a3) else false
  }

}

object Equal {

  implicit class EqualOps[A](val a: A) extends AnyVal {
    def ===(a1: A)(implicit ea: Equal[A]): Boolean = ea.equal(a1, a)
  }
  //   // somehow less efficient since it does not extends AnyVal and thus creating new object.
  //  implicit class EqualOps[A : Equal](val a: A) {
  //    def ===(a1: A) : Boolean = implicitly[Equal[A]].equal(a1, a)
  //  }

  implicit object StringEqual extends Equal[String] {
    override def equal(s1: String, s2: String): Boolean = s1 == s2
  }
  //
  //  implicit object IntEqual extends Equal[Int] {
  //    override def equal(s1: Int, s2: Int): Boolean = s1 == s2
  //  }
  //
  //  def foo[B <: AnyVal](b : B) : AnyVal = b

  implicit def AnyValEqual[B <: AnyVal] = new Equal[B]{
    override def equal(a1: B, a2: B): Boolean = a1 == a2
  }

  implicit def optionEqual[A: Equal] = new Equal[Option[A]] {
    override def equal(oa1: Option[A], oa2: Option[A]): Boolean = (oa1, oa2) match {
      case (None, None) => true
      case (Some(a1), Some(a2)) => a1 === a2
      case _ => false
    }
  }

  implicit def listEqual[A: Equal] = new Equal[List[A]] {
    override def equal(l1: List[A], l2: List[A]): Boolean = (l1, l2) match {
      case (Nil, Nil) => true
      case (h1 :: tl1, h2 :: tl2) => (h1 === h2) && equal(tl1, tl2)
      case _ => false
    }
  }
  implicit def mapEqual[K, A: Equal] = new Equal[({type Z = Map[K,A]})#Z] {
    override def equal(m1: Map[K, A], m2: Map[K, A]): Boolean = {
      val dif: Set[K] = (m1.keySet -- m2.keySet) ++ (m2.keySet -- m1.keySet)
      if (dif.nonEmpty) false else{
        m1.keys.map(k => m1.get(k) === m2.get(k)).reduce((v1, v2) => v1 && v2)
      }
    }
  }

}
