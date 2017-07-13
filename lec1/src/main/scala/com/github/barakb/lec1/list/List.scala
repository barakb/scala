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

  def zip[A, B](la: List[A])(lb: List[B]): List[(A, B)] = ???

  def zipWith[A, B, C](la: List[A])(lb: List[B])(f: (A, B) => C): List[C] = ???

  //  implement zip1 using zipWith
  def zip1[A, B](la: List[A])(lb: List[B]): List[(A, B)] = ???

  def map[A, B, C](l: List[A])(f: A => B): List[B] = ???

  def filter[A](l: List[A])(f: A => Boolean): List[A] = ???

  def elm[A](a: A)(l: List[A]): Boolean = ???

  def maximum[A](isBigger: (A, A) => Boolean)(l: List[A]): A = ???

  def reverse[A](l: List[A]): List[A] = ???

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

  def mapF[A, B, C](l: List[A])(f: A => B): List[B] = ???

  def filterF[A](l: List[A])(f: A => Boolean): List[A] = ???

  // assume that the list is not empty
  def maximumF[A](isBigger: (A, A) => Boolean)(l: List[A]): A = ???

  def reverseF[A](l: List[A]): List[A] = ???

}

