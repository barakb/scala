package com.github.barakb.lec1.hello

/**
  * Created by Barak Bar Orion
  * on 7/11/16.
  *
  * @since 12.0
  */
object HelloWorld {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    println(List(1, 2, 3).foldLeft(List[Int]())((l, b) => b::l))
  }
}
