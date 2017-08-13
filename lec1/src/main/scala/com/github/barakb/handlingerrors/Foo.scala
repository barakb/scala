package com.github.barakb.handlingerrors

/**
  * Created by Barak Bar Orion
  * on 8/13/17.
  */
object Foo extends App{

  def lazyFoo[A](a: => A): Unit = {
    lazy val f = a
    val f1 = f
    ()
  }
  def eagerFoo[A](a: A): Unit = ()

  lazyFoo(println("calling lazyFoo"))

}
