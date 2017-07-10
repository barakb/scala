package com.github.barakb.lec1.tree


/**
  * Created by Barak Bar Orion
  * on 7/9/17.
  */
sealed trait Tree[+A]

case object Empty extends Tree[Nothing]

case class Node[+A](left: Tree[A], a: A, right: Tree[A]) extends Tree[A]

object Tree {

  def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = tree match {
    case Empty => Empty
    case Node(left, v, right) => Node(map(left)(f), f(v), map(right)(f))
  }

  def insert[A](isBigger: (A, A) => Boolean)(a: A, tree: Tree[A]): Tree[A] = (a, tree) match {
    case (v, Empty) => Node(Empty, v, Empty)
    case (v, Node(l, vn, r)) if v == vn => Node(l, vn, r)
    case (v, Node(l, vn, r)) if isBigger(v, vn) => Node(l, vn, insert(isBigger)(v, r))
    case (v, Node(l, vn, r)) if isBigger(vn, v) => Node(insert(isBigger)(v, l), vn, r)
  }


  def apply[A](la: List[A], isBigger: (A, A) => Boolean): Tree[A] = {
    la.foldRight(Empty: Tree[A])(insert(isBigger))
  }

  def search[A](isBigger: (A, A) => Boolean)(a: A)(tree: Tree[A]): Option[A] = {
    def loop(tree: Tree[A]): Option[A] = tree match {
      case Empty => None
      case Node(l, v, r) if v == a => Some(v)
      case Node(l, v, r) if isBigger(v, a)=> loop(l)
      case Node(l, v, r) if isBigger(a, v)=> loop(r)
    }

    loop(tree)
  }
}
