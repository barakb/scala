package com.github.barakb.lec1.zipper

import com.github.barakb.lec1.tree.{Empty, Node}

/**
  * Created by Barak Bar Orion
  * on 7/12/17.
  */
object TreeWalk extends App {

  import com.github.barakb.lec1.tree.Tree

  sealed trait Direction

  case object Left extends Direction

  case object Right extends Direction

  type Directions = List[Direction]

  val freeTree: Tree[Char] =
    Node('P', Node('O', Node('L', Node('N', Empty, Empty)
      , Node('T', Empty, Empty))
      , Node('Y', Node('S', Empty, Empty)
        , Node('A', Empty, Empty)))
      , Node('L', Node('W', Node('C', Empty, Empty)
        , Node('R', Empty, Empty))
        , Node('A', Node('A', Empty, Empty)
          , Node('C', Empty, Empty))))

  def changeToP(directions: Directions, tree: Tree[Char]): Tree[Char] = (directions, tree) match {
    case (Left :: ds, Node(x, l, r)) => Node(x, changeToP(ds, l), r)
    case (Right :: ds, Node(x, l, r)) => Node(x, l, changeToP(ds, r))
    case (Nil, Node(_, l, r)) => Node('P', l, r)
  }

  def elemAt[A](directions: Directions, tree: Tree[A]): A = (directions, tree) match {
    case (Left :: ds, Node(x, l, r)) => elemAt(ds, l)
    case (Right :: ds, Node(x, l, r)) => elemAt(ds, r)
    case (Nil, Node(x, _, _)) => x
  }

  private val directions = List(Right, Left)
  val newTree1 = changeToP(directions, freeTree)
  println(newTree1) // L, W => L, P
  println(elemAt(directions, freeTree) + ", " + elemAt(directions, newTree1))


  sealed trait Crumb[+A]

  case class LeftCrumb[+A](a: A, tree: Tree[A]) extends Crumb[A]

  case class RightCrumb[+A](a: A, tree: Tree[A]) extends Crumb[A]

  type Breadcrumbs[A] = List[Crumb[A]]
  type Zipper[A] = (Tree[A], Breadcrumbs[A])

  def goLeft[A](zipper: Zipper[A]): Zipper[A] = zipper match {
    case (Node(a, l, r), bs) => (l, LeftCrumb(a, r) :: bs)
  }

  def goRight[A](zipper: Zipper[A]): Zipper[A] = zipper match {
    case (Node(a, l, r), bs) => (r, RightCrumb(a, l) :: bs)
  }

  def goUp[A](zipper: Zipper[A]): Zipper[A] = zipper match {
    case (tree, LeftCrumb(a, r) :: tlc) => (Node(a, tree, r), tlc)
    case (tree, RightCrumb(a, l) :: tlc) => (Node(a, l, tree), tlc)
  }


  def modify[A](f: A => A)(zipper: Zipper[A]): Zipper[A] = zipper match {
    case (Node(x, l, r), bs) => (Node(f(x), l, r), bs)
    case (Empty, bs) => (Empty, bs)
  }


  def attach[A](tree: Tree[A])(zipper: Zipper[A]): Zipper[A] = zipper match {
    case (_, bs) => (tree, bs)
  }

  implicit class InfixOps[A](private val zipper: Zipper[A]) extends AnyVal {
    def ->>(f: Zipper[A] => Zipper[A]) : Zipper[A] = f(zipper)
  }

  def topMost[A](zipper: Zipper[A]): Zipper[A] = zipper match{
    case top@(t, Nil) => top
    case _ => topMost(zipper ->> goUp)
  }

  println("newFocus1 " +  modify((_: Char) => 'P')(goRight(goLeft(freeTree, Nil))))

  println("newFocus1 " + ((freeTree, Nil) ->> goLeft ->> goRight ->> modify((_: Char) => 'P') ->> goUp ->> modify((_: Char) => 'C') ->> topMost))

}
