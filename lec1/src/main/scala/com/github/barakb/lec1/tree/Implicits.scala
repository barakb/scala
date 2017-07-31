package com.github.barakb.lec1.tree

import java.util.{Calendar, Date}

/**
  * Created by Barak Bar Orion
  * on 7/31/17.
  */

object Implicits extends App{

  implicit class RI(val i:Int) extends AnyVal{
    def inc(increment:Int) : Int = i + increment
  }

  implicit class DI(val date:Date) extends AnyVal{
    def inc(increment:Int) : Date = {
      val c = Calendar.getInstance
      c.setTime(date)
      c.add(Calendar.DATE, increment)
      c.getTime
    }
  }



  1.to(100)
  val v = RI(1).inc(10)
  println(v)
  val v1 = new Date().inc(10)
  println(v1)

}
