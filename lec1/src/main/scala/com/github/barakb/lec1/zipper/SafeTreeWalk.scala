package com.github.barakb.lec1.zipper

import com.github.barakb.lec1.tree.{Empty, Node}

/**
  * Created by Barak Bar Orion
  * on 7/12/17.
  */
object SafeTreeWalk extends App {

  import com.github.barakb.lec1.tree.Tree

  val freeTree: Tree[Char] =
    Node('P', Node('O', Node('L', Node('N', Empty, Empty)
      , Node('T', Empty, Empty))
      , Node('Y', Node('S', Empty, Empty)
        , Node('A', Empty, Empty)))
      , Node('L', Node('W', Node('C', Empty, Empty)
        , Node('R', Empty, Empty))
        , Node('A', Node('A', Empty, Empty)
          , Node('C', Empty, Empty))))

  sealed trait Crumb[+A]

  case class LeftCrumb[+A](a: A, tree: Tree[A]) extends Crumb[A]

  case class RightCrumb[+A](a: A, tree: Tree[A]) extends Crumb[A]

  type Breadcrumbs[A] = List[Crumb[A]]
  type Zipper[A] = (Tree[A], Breadcrumbs[A])

  def goLeft[A](zipper: Zipper[A]): Option[Zipper[A]] = zipper match {
    case (Node(a, l, r), bs) => Some(l, LeftCrumb(a, r) :: bs)
    case (Empty, _) => None
  }

  def goRight[A](zipper: Zipper[A]):  Option[Zipper[A]] = zipper match {
    case (Node(a, l, r), bs) => Some(r, RightCrumb(a, l) :: bs)
    case (Empty, _) => None
  }

  def goUp[A](zipper: Zipper[A]):  Option[Zipper[A]] = zipper match {
    case (tree, LeftCrumb(a, r) :: tlc) => Some(Node(a, tree, r), tlc)
    case (tree, RightCrumb(a, l) :: tlc) => Some(Node(a, l, tree), tlc)
    case (_, Nil) => None
  }


  def modify[A](f: A => A)(zipper: Zipper[A]): Option[Zipper[A]] = zipper match {
    case (Node(x, l, r), bs) => Some(Node(f(x), l, r), bs)
    case (Empty, bs) => Some(Empty, bs)
  }


  def attach[A](tree: Tree[A])(zipper: Zipper[A]): Zipper[A] = zipper match {
    case (_, bs) => (tree, bs)
  }

  implicit class InfixOps[A](private val maybeZipper: Option[Zipper[A]]) extends AnyVal {
    def ->>(f: Zipper[A] => Option[Zipper[A]]) : Option[Zipper[A]] = maybeZipper flatMap f
  }

  def topMost[A](zipper: Zipper[A]): Option[Zipper[A]] = zipper match{
    case top@(t, Nil) => Some(top)
    case _ => goUp(zipper) ->> topMost
  }

//  println("newFocus1 " +  modify((_: Char) => 'P')(goRight(goLeft(freeTree, Nil))))

  println("newFocus1 " + (Some(freeTree, Nil) ->> goLeft ->> goRight ->> modify((_: Char) => 'P') ->> goUp ->> modify((_: Char) => 'C') ->> topMost))


//  Home work
// 1. Extend ->> to support list of actions: (Some(freeTree, Nil) ->> List(goLeft, goRight, goLeft,  modify((_: Char) => 'P'), topMost)
// 2. Write zipper and safeZipper for List[A]
}
