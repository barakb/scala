package com.github.barakb.lec1

/**
  * Created by Barak Bar Orion
  * on 7/16/17.
  */

object Util{
  def compose[A, B, C](f: B => C,g: A => B): A => C = a => f(g(a))

  implicit class InfixCompose[B, C](private val f: B => C) extends AnyVal {
    def `.`[A](g: A => B): (A => C) = (a: A) => f(g(a))
    def after[A](g: A => B): (A => C) = (a: A) => f(g(a))
  }


  val add1F: Int => Int = i => 1 + i
  val mul5F: Int => Int = i => 5 * i

  def add1(i:Int): Int = i + 1
  def mul5(i: Int): Int = i * 5

//  val mul5AfterAdd1 = compose(mul5, add1)

  val mul5AfterAdd1 =   add1F after mul5F
  val mul5AfterAdd1_ =   add1F `.` mul5F
  println(mul5AfterAdd1_(1))

}
