package com.gigaspaces.par

import java.util.concurrent.Executors

/**
  * Created by Barak Bar Orion
  * on 8/13/17.
  */
object ActorExample extends App{
  val s = Executors.newFixedThreadPool(1)
  val echoer = Actor[String](s) {
    msg => println(s"Got message: '$msg'")
  }
  echoer ! "hello"
  echoer ! "goodbye"
  s.shutdown()

}
