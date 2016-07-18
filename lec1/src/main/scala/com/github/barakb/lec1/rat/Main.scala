package com.github.barakb.lec1.rat

/**
  * Created by Barak Bar Orion
  * on 7/13/16.
  *
  * @since 12.0
  */
object Main {
  implicit def int2Rational(n: Int): Rational = new Rational(n)

  def main(args: Array[String]): Unit = {
    println(new Rational(2, 4) + new Rational(2, 3))
    println(new Rational(2, 4) + 1)
    println(1 + Rational(2, 4))
  }
}
