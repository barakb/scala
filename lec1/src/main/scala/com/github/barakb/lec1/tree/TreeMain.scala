package com.github.barakb.lec1.tree

/**
  * Created by Barak Bar Orion
  * on 7/9/17.
  */
object TreeMain extends App{
  import com.github.barakb.lec1.tree.Tree._
  val t = Tree(List(5, 4, 6, 3, 7, 2, 8, 1), (i1:Int, i2:Int) => i1 > i2)
  println(t)
  println(search((a:Int, b:Int) => a > b)(3)(t))
  println(search((a:Int, b:Int) => a > b)(-3)(t))

}
