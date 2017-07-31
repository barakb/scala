package com.github.barakb.lec1.list

import com.github.barakb.lec1.list.List._

import scala.collection.immutable.{List => ScalaList}
import scala.collection.immutable.{Nil => ScalaNil}

/**
  * Created by Barak Bar Orion
  * on 7/9/17.
  */
object ListMain extends App{

  val l1 = List(1, 2, 3)
  println(s"l1 is: $l1")

  def add(a:Int, b:Int) : Int = a + b
  add(1, 2)

//  val sumL = foldl(0)(List(1, 2, 3)(add)
//  println(sumL)
//  val sumR = foldr(0)(List(1, 2, 3)(add))
//  println(sumR)
//
  def scalaCons[A](a:A, l:ScalaList[A]): ScalaList[A] = a::l
  def l:List[Int] = List(1, 2, 3)

//  val scalaList = foldr((a: Int, l:ScalaList[Int]) => a ::l)(ScalaNil)(l)

  println("List: " + l)

//  println("scalaList: " + scalaList)

//  val reversed = foldl((l:ScalaList[Int], a:Int) => a::l)(ScalaNil)(l)

//  println("reversed to scala list: " + reversed)

//  println("reversed to cons list: " + foldl(List[Int]())(List(1, 2, 3))(l:List[Int], a:Int) => Cons(a,l): List[Int])

//  println("reversed cons list " + List.reverse(List("a", "b", "c")))

}
