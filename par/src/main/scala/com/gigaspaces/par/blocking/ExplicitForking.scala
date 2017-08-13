package com.gigaspaces.par.blocking

import com.gigaspaces.par.blocking.FirstDraft.Par

/**
  * Created by Barak Bar Orion
  * on 8/13/17.
  */
object ExplicitForking extends App {

  // Explicit forking

  /*
  Something still doesn't feel right about our latest choice. Is it always the case that we
  want to evaluate the two arguments to map2 in parallel? Probably not. Consider this
  simple hypothetical example:
  */
  Par.map2(Par.unit(1), Par.unit(1))(_ + _)

  def sum(ints: IndexedSeq[Int]): Par[Int] =
    if (ints.length <= 1)
      Par.unit(ints.headOption getOrElse 0)
    else {
      val (l,r) = ints.splitAt(ints.length/2)
      Par.map2(Par.fork(sum(l)), Par.fork(sum(r)))(_ + _)
    }

  // does fork begins evaluating its argument immediately in parallel ?
  // if so it needed some kind of thread pool.
  // lets assume that get is responsible for stating the evaluation, so lest rename get to run

  class Par[A](val a: A)

  object Par {
    def unit[A](a: A): Par[A] = ???

    def run[A](p: Par[A]): A = ???

    def map2[A, B, C](p1: => Par[A], p2: => Par[B])(f: (A , B) => C) : Par[C] = ???

    def fork[A](a: => Par[A]): Par[A] = ???

    // derived combinator
    def lazyUnit[A](a: => A): Par[A] = fork(unit(a))
  }

}
