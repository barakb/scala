package com.github.barakb.lec1.list

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

  def foo(ints: List[Int]):Int = foldl((a:Int, b:Int) => a + b)(0)(ints)

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
  def head[A](l: List[A]): A = ???

  def length[A](l: List[A]): Int = ???

  //  the function drop , which removes the first n elements from a list
  def drop[A](l: List[A], n: Int): List[A] = ???

  //  Implement dropWhile , which removes elements from the List prefix as long as they
  //  match a predicate.
  def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = ???


  def take[A](l: List[A], n: Int): List[A] = ???

  def takeWhile[A](l: List[A])(f: A => Boolean): List[A] = ???

  def append[A](a1: List[A], a2: List[A]): List[A] = ???

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

  //  implement zip1 using zipWith
  def zip1[A, B](la: List[A])(lb: List[B]): List[(A, B)] = zipWith(la)(lb)((a, b) => (a,b))

  def map[A, B](l: List[A])(f: A => B): List[B] = ???

  def filter[A](l: List[A])(f: A => Boolean): List[A] = ???

  def elm[A](a: A)(l: List[A]): Boolean = ???

  def maximum[A](isBigger: (A, A) => Boolean)(l: List[A]): A = ???

  def reverse[A](l: List[A]): List[A] = foldl((l: List[A], a: A) => Cons(a, l):List[A])(Nil)(l)


  def foldl[A, B](f: (B, A) => B)(b: B)(l: List[A]): B = l match {
    case Nil => b
    case Cons(h, tl) => foldl(f)(f(b, h))(tl)
  }

  def foldr[A, B](f: (A, B) => B)(b: B)(l: List[A]): B = l match {
    case Nil => b
    case Cons(h, tl) => f(h, foldr(f)(b)(tl))
  }

  //  implement the following in term of foldl or foldr
  def lengthF[A](l: List[A]): Int = ???

  def appendF[A](a1: List[A], a2: List[A]): List[A] = ???

  def mapF[A, B](l: List[A])(f: A => B): List[B] = ???

  def filterF[A](l: List[A])(f: A => Boolean): List[A] = ???

  // assume that the list is not empty
  def maximumF[A](isBigger: (A, A) => Boolean)(l: List[A]): A = ???

  def reverseF[A](l: List[A]): List[A] = ???



  // implement the flatmap function
  def flatMap[A, B](f: A => List[B])(l: List[A]):List[B] = l match {
    case Nil => Nil
    case Cons(h, t) => append(f(h), flatMap(f)(t))
  }

  // implement map using flatMap
  def mapF[A, B](f: A => B)(l: List[A]): List[B] = flatMap((a:A) => List(f(a)))(l)
  val f: (List[Int]) => List[Int] = mapF((i:Int) => i + 1)
  mapF((i:Int) => i + 1)(Cons(1, Nil))

  def mapFF[A, B](f: A => B):(List[A] => List[B]) = flatMap((a:A) => List(f(a)))
  mapFF((i:Int) => i + 1)(Cons(1, Nil))

  def uncurreidMap[A, B](f: A => B, l: List[A]): List[B] = flatMap((a:A) => List(f(a)))(l)

  val mapFFTag = uncurreidMap _ curried
//  val f1 = mapFFTag((i:Int) => i)

}

