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
    println(quickSortInts(List(3, 1, 4)))
    println(quickSort(List(3, 1, 4))(_ > _))
  }

  def quickSortInts(ints: List[Int]): List[Int] = ints match {
    case Nil => Nil
    case x :: xs =>
      val smallerSorted = quickSortInts(for (s <- xs; if s < x) yield s)
      val biggerSorted = quickSortInts(for (b <- xs; if x <= b) yield b)
      smallerSorted ++ List(x) ++ biggerSorted
  }

  def quickSort[A](values: List[A])(isBigger: (A, A) => Boolean): List[A] = values match {
    case Nil => Nil
    case (x :: xs) =>
      val smallerSorted = quickSort(for (s <- xs; if !isBigger(s, x)) yield s)(isBigger)
      val biggerSorted = quickSort(for (b <- xs; if isBigger(b, x)) yield b)(isBigger)
      smallerSorted ++ List(x) ++ biggerSorted
  }
}
