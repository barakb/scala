package com.github.barakb.lec1.tree


/**
  * Created by Barak Bar Orion
  * on 7/9/17.
  */
sealed trait Tree[+A]

case object Empty extends Tree[Nothing]

case class Node[+A](a: A, left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {

  def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = tree match {
    case Empty => Empty
    case Node(v, left, right) => Node(f(v), map(left)(f), map(right)(f))
  }

  def insert[A](isBigger: (A, A) => Boolean)(a: A, tree: Tree[A]): Tree[A] = (a, tree) match {
    case (v, Empty) => Node(v, Empty, Empty)
    case (v, Node(vn, l, r)) if v == vn => Node(vn, l, r)
    case (v, Node(vn,l, r)) if isBigger(v, vn) => Node( vn, l, insert(isBigger)(v, r))
    case (v, Node(vn, l, r)) if isBigger(vn, v) => Node(vn, insert(isBigger)(v, l), r)
  }


  def apply[A](la: List[A], isBigger: (A, A) => Boolean): Tree[A] = {
    la.foldRight(Empty: Tree[A])(insert(isBigger))
  }

  def search[A](isBigger: (A, A) => Boolean)(a: A)(tree: Tree[A]): Option[A] = {
    def loop(tree: Tree[A]): Option[A] = tree match {
      case Empty => None
      case Node(v, l, r) if v == a => Some(v)
      case Node(v,l, r) if isBigger(v, a)=> loop(l)
      case Node(v, l, r) if isBigger(a, v)=> loop(r)
    }
    loop(tree)
  }
}
