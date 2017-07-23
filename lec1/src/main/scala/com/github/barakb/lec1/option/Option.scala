package com.github.barakb.lec1.option

/**
  * Created by Barak Bar Orion
  * on 7/16/17.
  */
sealed abstract class Option[+A] {

//  import com.github.barakb.lec1.Util._

  def isEmpty: Boolean

  def get: A


//  def map[B](f:A => B): Option[B] = this match{
//    case None => None
//    case Some(a) => Some(f(a))
//  }

  def flatMap[B](f: A => Option[B]): Option[B] = this match{
    case None => None
    case Some(a) => f(a)
  }

  def map[B](f:A => B): Option[B] = flatMap(f andThen Option.pure)
  def map[B](f:A => B): Option[B] = flatMap((a) => Some(f(a)))
}

case object None extends Option[Nothing] {
  override def isEmpty: Boolean = true

  override def get: Nothing = throw new NoSuchElementException("None.get")
}

case class Some[+A](a: A) extends Option[A] {
  override def isEmpty: Boolean = false

  override def get: A = a
}

object Option{
  def pure[A](a: A): Option[A] = Some(a)
}



