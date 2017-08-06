package com.github.barakb.lec1.zipper

/**
  * Created by Barak Bar Orion
  * on 8/6/17.
  */

object Implicits {

  implicit class MyIntMethods(val int: Int) extends AnyVal{
    def add(anotherInt: Int): Int = int + anotherInt
  }
}


