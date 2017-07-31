package com.github.barakb.lec1.list

import scala.annotation.tailrec

/**
  * Created by Barak Bar Orion
  * on 7/9/17.
  * Talk about Scala List and list like.
  * Shows Co Variance, Contravariance
  * case class
  * companion Object / apply
  * varargs A*
  * pattern matching
  */
sealed trait List[+A]

case object Nil extends List[Nothing]

case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

//  def foo(ints: List[Int]): Int = foldl((a: Int, b: Int) => a + b)(0)(ints)

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  // HW
  // returns the first item of the list
  def head[A](l: List[A]): A = l match {
    case Cons(x, _) => x
  }

  def length[A](l: List[A]): Int = l match {
    case Nil => 0
    case Cons(_, xs) => 1 + length(xs)
  }

  //  the function drop , which removes the first n elements from a list
  def drop[A](l: List[A], n: Int): List[A] = if (n == 0) l else l match {
    case Nil => l
    case Cons(_, xs) => drop(xs, n - 1)
  }

  //  Implement dropWhile , which removes elements from the List prefix as long as they
  //  match a predicate.
  def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = l match {
    case Nil => l
    case Cons(h, tl) => if (f(h)) dropWhile(tl)(f) else tl
  }


  def take[A](l: List[A], n: Int): List[A] = if (n == 0) Nil else l match {
    case Nil => Nil
    case Cons(h, tl) => Cons(h, take(tl, n - 1))
  }

  def takeWhile[A](l: List[A])(f: A => Boolean): List[A] = l match {
    case Nil => Nil
    case Cons(h, tl) => if (f(h)) Cons(h, takeWhile(tl)(f)) else Nil
  }

  def append[A](a1: List[A], a2: List[A]): List[A] = (a1, a2) match {
    case (l, Nil) => l
    case (Nil, l) => l
    case (Cons(h, tl), l2) => Cons(h, append(tl, l2))
  }

  def zip[A, B](la: List[A])(lb: List[B]): List[(A, B)] = (la, lb) match {
    case (Cons(h1, t1), Cons(h2, t2)) => Cons((h1, h2), zip(t1)(t2))
    case (Nil, _) => Nil
    case (_, Nil) => Nil
  }

  def zipWith[A, B, C](la: List[A])(lb: List[B])(f: (A, B) => C): List[C] = (la, lb) match {
    case (Cons(h1, t1), Cons(h2, t2)) => Cons(f(h1, h2), zipWith(t1)(t2)(f))
    case (Nil, _) => Nil
    case (_, Nil) => Nil
  }

  def negate[A](f: A => Boolean): A => Boolean = a => !f(a)

  //  implement zip1 using zipWith
  def zip1[A, B](la: List[A])(lb: List[B]): List[(A, B)] = zipWith(la)(lb)((a, b) => (a, b))

  def map[A, B](l: List[A])(f: A => B): List[B] = l match {
    case Nil => Nil
    case Cons(h, tl) => Cons(f(h), map(tl)(f))
  }

  def filter[A](l: List[A])(f: A => Boolean): List[A] = l match {
    case Nil => Nil
    case Cons(h, tl) => if (f(h)) Cons(h, filter(tl)(f)) else filter(tl)(f)
  }

  def elm[A](a: A)(l: List[A]): Boolean = l match {
    case Nil => false
    case Cons(h, tl) => if (h == a) true else elm(a)(tl)
  }

  def maximum[A](isBigger: (A, A) => Boolean)(l: List[A]): A = l match {
    case Cons(x, Nil) => x
    case Cons(h, tl) =>
      val tlMax = maximum(isBigger)(tl)
      if (isBigger(tlMax, h)) tlMax else h
  }

//  10 + [0 1 2]
//    10 + [1 2]
//      11 + 2  []
//        13
//  Nil (i: Int, l: List[Int]) => Const(i, l) [1 2 3]
//  Cons(1, Nil) [2 3]
//  Cons(2, Cons(1, Nil)) [3]
//  Cons(3, Cons(2, Cons(1, Nil)) []

//  def reverse[A](l: List[A]): List[A] = foldl((l: List[A], a: A) => Cons(a, l): List[A])(Nil)(l)
//  def reverse[A](l: List[A]): List[A] = l match{
//    case Nil => Nil
//    case Cons(h, tl) => append(reverse(tl), List(h))
//  }


  @tailrec
  def foldl[A, B](b: B)(l: List[A])(f: (B, A) => B): B = l match {
    case Nil => b
    case Cons(h, tl) => foldl(f(b, h))(tl)(f)
  }

  def foldr[A, B](b: B)(l: List[A])(f: (A, B) => B): B = l match {
    case Nil => b
    case Cons(h, tl) => f(h, foldr(b)(tl)(f))
  }

  //  implement the following in term of foldl or foldr
  def lengthF[A](l: List[A]): Int = foldl(0)(l)((c, a) => c + 1)
  def appendF[A](a1: List[A], a2: List[A]): List[A] = foldr(a2)(a1)((a, l) => Cons(a, l))
//  Z :[B] op [1 2]
//  def mapF[A, B](l: List[A])(f: A => B): List[B] = foldl(Nil)(l)((bs, a) => append(bs, List(f(a))))

  def filterF[A](l: List[A])(f: A => Boolean): List[A] = foldr(Nil:List[A])(l)((a:A, la: List[A]) => if(f(a)) Cons(a, la) else la)

  // assume that the list is not empty
  def maximumF[A](isBigger: (A, A) => Boolean)(l: List[A]): A = {
    val Cons(x, xs) = l
    foldl(x)(xs)((a1, a2) => if (isBigger(a1, a2)) a1 else a2)
  }

  def reverseF[A](l: List[A]): List[A] = ???


  // implement the flatmap function
  def flatMap[A, B](f: A => List[B])(l: List[A]): List[B] = l match {
    case Nil => Nil
    case Cons(h, t) => append(f(h), flatMap(f)(t))
  }

  // implement map using flatMap
  def mapF[A, B](f: A => B)(l: List[A]): List[B] = flatMap((a: A) => List(f(a)))(l)

  val f: (List[Int]) => List[Int] = mapF((i: Int) => i + 1)
  mapF((i: Int) => i + 1)(Cons(1, Nil))

  def mapFF[A, B](f: A => B): (List[A] => List[B]) = flatMap((a: A) => List(f(a)))

  mapFF((i: Int) => i + 1)(Cons(1, Nil))

  def uncurreidMap[A, B](f: A => B, l: List[A]): List[B] = flatMap((a: A) => List(f(a)))(l)

  val mapFFTag = uncurreidMap _ curried
  //  val f1 = mapFFTag((i:Int) => i)

}

