
/**
  * Created by Barak Bar Orion
  * on 7/25/16.
  *
  * @since 12.0
  */
object Lec5 {

//  trait RNG {
//    def nextInt: (Int, RNG)
//  }
//
//  case class SimpleRNG(seed: Long) extends RNG {
//    def nextInt: (Int, RNG) = {
//      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
//      val nextRNG = SimpleRNG(newSeed)
//      val n = (newSeed >>> 16).toInt
//      (n, nextRNG)
//    }
//  }
//
//  def ints(count: Int)(rng: RNG): (List[Int], RNG) =
//    if (count == 0) (Nil, rng)
//    else {
//      val (n, rng1) = rng.nextInt
//      val (l, r) = ints(count - 1)(rng1)
//      (n :: l, r)
//    }
//
//  type Rand[+A] = RNG => (A, RNG)
//
//  val int: Rand[Int] = _.nextInt
//
//  def unit[A](a: A): Rand[A] = rng => (a, rng)
//
//  def map[A, B](s: Rand[A])(f: A => B): Rand[B] =
//    rng => {
//      val (a, rng2) = s(rng)
//      (f(a), rng2)
//    }
//
//  def map2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] =
//    rng => {
//      val (a, rng2) = ra(rng)
//      val (b, rng3) = rb(rng2)
//      (f(a, b), rng3)
//    }
//
//  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] =
//    rng => {
//      def loop(fs: List[Rand[A]])(rng: RNG): (List[A], RNG) = fs match {
//        case Nil => (Nil, rng)
//        case (h :: tl) =>
//          val (n1, nextRNG) = h(rng)
//          val (restN, nrgFinal) = loop(tl)(nextRNG)
//          (n1 :: restN, nrgFinal);
//      }
//      loop(fs)(rng)
//    }
//
//  def flatMap[A, B](f: Rand[A])(g: A => Rand[B]): Rand[B] =
//    rng => {
//      val (a, rng2) = f(rng)
//      g(a)(rng2)
//    }
//
//  def mapUsingFlattMap[A, B](s: Rand[A])(f: A => B): Rand[B] =
//    flatMap(s)(a => unit(f(a)))
//
//  def map2UsingFlattMap[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] =
//    flatMap(ra)(a => flatMap(rb)(b => unit(f(a, b))))
//
//  def map[S, A, B](a: S => (A, S))(f: A => B): S => (B, S) =
//    s => {
//      val (a1, s1) = a(s)
//      (f(a1), s1)
//    }


}

import State._

case class State[S, +A](run: S => (A, S)) {
  def map[B](f: A => B): State[S, B] = State(s => {
    val (a1, s1) = run(s)
    (f(a1), s1)
  })


  def flatMap[B](f: A => State[S, B]): State[S, B] = State(s => {
    val (a, s1) = run(s)
    f(a).run(s1)
  })

  def mapUsingFlatMap[B](f: A => B): State[S, B]  = flatMap(a => unit(f(a)))

  def map2[B,C](sb: State[S, B])(f: (A, B) => C): State[S, C] = State(s => {
    val (a, s1) = run(s)
    val (b, s2) = sb.run(s1)
    (f(a, b), s2)
  })

  def map2UsingFlatMap[B,C](sb: State[S, B])(f: (A, B) => C): State[S, C] = flatMap(a => sb.map(b => f(a, b)))
}


object State {
  def unit[S, A](a: A): State[S, A] = State(s => (a, s))
}


