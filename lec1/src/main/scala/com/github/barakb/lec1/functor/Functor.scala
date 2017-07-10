package com.github.barakb.lec1.functor

import com.github.barakb.lec1.tree.Tree

/**
  * Created by Barak Bar Orion
  * on 7/9/17.
  */
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  implicit def ListFunctor = new Functor[List]{
    override def map[A, B](fa: List[A])(f: (A) => B): List[B] = fa.map(f)
  }

  implicit def OptionFunctor = new Functor[Option]{
    override def map[A, B](fa: Option[A])(f: (A) => B): Option[B] = fa.map(f)
  }

  implicit def TreeFunctor = new Functor[Tree]{
    override def map[A, B](fa: Tree[A])(f: (A) => B): Tree[B] = map(fa)(f)
  }
}
