package com.gigaspaces.par.blocking

import java.util.concurrent._
import language.implicitConversions

/**
  * Created by Barak Bar Orion
  * on 8/13/17.
  */
object FirstImpl extends App {

  import Par._

  def sum(ints: IndexedSeq[Int]): Par[Int] =
    if (ints.length <= 1)
      Par.unit(ints.headOption getOrElse 0)
    else {
      val (l, r) = ints.splitAt(ints.length / 2)
      Par.map2(Par.fork(sum(l)), Par.fork(sum(r)))(_ + _)
    }


  object Par {
    type Par[A] = ExecutorService => Future[A]

    def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)

    def unit[A](a: A): Par[A] = (es: ExecutorService) => UnitFuture(a)

    private case class UnitFuture[A](get: A) extends Future[A] {
      def isDone = true

      def get(timeout: Long, units: TimeUnit): A = get

      def isCancelled = false

      def cancel(evenIfRunning: Boolean): Boolean = false
    }

    def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) => C): Par[C] =
      (es: ExecutorService) => {
        val af = a(es)
        val bf = b(es)
        UnitFuture(f(af.get, bf.get))
      }

    def fork[A](a: => Par[A]): Par[A] =
      es => es.submit(new Callable[A] {
        def call: A = a(es).get
      })

    def lazyUnit[A](a: => A): Par[A] = fork(unit(a))

    // adding to the API derived combinator
    def asyncF[A, B](f: A => B): A => Par[B] = a => lazyUnit(f(a))

    def parMap[A, B](ps: List[A])(f: A => B): Par[List[B]] = {
      val fbs: List[Par[B]] = ps.map(asyncF(f))
      // now we need to turn List[Par[B]] to Par[List[B]]
      sequence(fbs)
    }

    def sequence[A](l: List[Par[A]]): Par[List[A]] = l match {
      case Nil => unit(Nil)
      case h :: tl => map2(h, sequence(tl))(_ :: _)
    }

    def sequence_using_fold[A](l: List[Par[A]]): Par[List[A]] =
      l.foldRight[Par[List[A]]](unit(List()))((h, t) => map2(h, t)(_ :: _))

  }

  /*
  Suppose we have a Par[List[Int]] representing a parallel computation that produces a List[Int],
  and we’d like to convert this to a Par[List[Int]] whose result is
  sorted:
  */
  // We could of course run the Par , sort the resulting list, and repackage it in a Par with
  // unit . But we want to avoid calling run .
  def sortPar(parList: Par[List[Int]]): Par[List[Int]] = Par.map2(parList, Par.unit(()))((a, _) => a.sorted)

  //  we can define map using map2
  def map[A, B](pa: Par[A])(f: A => B): Par[B] = Par.map2(pa, Par.unit(()))((a, _) => f(a))

  // and we can define sortPar1 using map
  def sortPar1(parList: Par[List[Int]]): Par[List[Int]] = map(parList)(_.sorted)

  // The fact that we can implement map in terms of map2 , but not the other way around, just shows that map2 is strictly more powerful than map.

  // Home work
  // Implement parFilter, which filters elements of a list in parallel again without calling get.
  def parFilter[A](as: List[A])(f: A => Boolean): Par[List[A]] = {
    val f1 = (a: A) => if (f(a)) List(a) else Nil
    val mapped: List[Par[List[A]]] = as.map(Par.asyncF(f1))
    val sequenced: Par[List[List[A]]] = Par.sequence(mapped)
    map(sequenced)(_.flatten)
  }

  // laws:
  // map(y)(id) == y
  // map(y) (f andThen g) == map(map(y)(f))(g)

  println("before")
  val a = Par.lazyUnit(42 + 1)
  val s = Executors.newFixedThreadPool(100)
  val v = Par.fork(a)(s)
  println(v.get())
  println("after")

  val sm = sum(1 to 100)(s)
  println("sum: " + sm.get)
  s.shutdownNow()

  // deadlock at
  /*
  def fork[A](a: => Par[A]): Par[A] =
      es => es.submit(new Callable[A] {
        def call: A = a(es).get
      })
    */
  //  from the thread pool we are calling a blocking operation get

  //  Why doesn't
  /*
  def fork[A](fa: => Par[A]): Par[A] =
    es => fa(es)
    */
  //  work ?

//  It is still useful
  def delay[A](fa: => Par[A]): Par[A] =
    es => fa(es)

  def flatMap[A,B](p: Par[A])(f: A => Par[B]): Par[B] =
    es => {
      val k = run(es)(p).get
      run(es)(f(k))
    }

  def join[A](a: Par[Par[A]]): Par[A] =
    es => run(es)(run(es)(a).get())


}
