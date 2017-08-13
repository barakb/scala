package com.gigaspaces.par.blocking

/**
  * Created by Barak Bar Orion
  * on 8/13/17.
  */
object FirstDraft extends App {

  //noinspection SimplifiableFoldOrReduce
  def sum(ints: Seq[Int]): Int = ints.foldLeft(0)((a, b) => a + b)

  def sum1(ints: IndexedSeq[Int]): Int =
    if (ints.size <= 1)
      ints.headOption getOrElse 0
    else {
      val (l, r) = ints.splitAt(ints.length / 2)
      sum1(l) + sum1(r)
    }

  def sum2(ints: IndexedSeq[Int]): Int =
    if (ints.size <= 1)
      ints.headOption getOrElse 0
    else {
      val (l, r) = ints.splitAt(ints.length / 2)
      val sumL: Par[Int] = Par.unit(sum2(l))
      val sumR: Par[Int] = Par.unit(sum2(r))
      Par.get(sumL) + Par.get(sumR)
    }

  // if unit begins evaluating its argument concurrently, then calling get arguably
  // breaks referential transparency. We can see this by replacing sumL and sumR with their
  // definitions—if we do so, we still get the same result, but our program is no longer
  // parallel

  // Par.get(Par.unit(sum2(l))) + Par.get(Par.unit(sum2(r)))

  // If unit starts evaluating its argument right away, the next thing to happen is that get
  // will wait for that evaluation to complete. So the two sides of the + sign won’t run in
  // parallel if we simply inline the sumL and sumR variables. We can see that unit has a
  // definite side effect, but only with regard to get.


  //  Let’s see if we can avoid the aforementioned pitfall of combining unit and get . If we
  //  don’t call get, that implies that our sum function must return a Par[Int].
  //  What consequences does this change reveal? Again, let’s just invent functions with the required
  //  signatures:
  def sum3(ints: IndexedSeq[Int]): Par[Int] =
  if (ints.size <= 1)
    Par.unit(ints.headOption getOrElse 0)
  else {
    val (l, r) = ints.splitAt(ints.length / 2)
    Par.map2(sum3(l), sum3(r))(_ + _)
  }


  class Par[A](val a: A)

  object Par {
    def unit[A](a: => A): Par[A] = ???

    def get[A](p: Par[A]): A = ???

    def map2[A, B, C](p1: => Par[A], p2: => Par[B])(f: (A , B) => C) : Par[C] = ???
  }

}


