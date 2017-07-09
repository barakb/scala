package com.github.barakb.lec1.rat

/**
  * Created by Barak Bar Orion
  * on 7/6/17.
  */
object Sort extends App {

  // Tuple and List
  // number of param list
  // currying
  // pattern matching
  // case classes
  // apply, pattern match, equals, hashcode.

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

  println(quickSortInts(List(3, 1, 4)))
  println(quickSort(List(3, 1, 4))(_ > _))

  case class Person(age:Int, name:String)

  val f = Person(16, "Barak") match{
    case Person(17, "Barak") => "Barak young"
    case Person(18, "Barak") => "Barak"
    case x@_ => "I don't know " + x

  }

  println(f)

  val persons = List(Person(100, "Bush"), Person(16,"Barak"), Person(17, "Obamma"))

  // example for Person sort by age
  println("sort by age: " + quickSort(persons)(_.age > _.age))
  // example for Person sort by name
  println("sort by Name: " + quickSort(persons)(_.name > _.name))


  // currying

  val sortPersons = quickSort(persons) _
  println("sort by age with currying: " + sortPersons(_.age > _.age))
  // example for Person sort by name
  println("sort by name with currying: " + sortPersons(_.name > _.name))

}
