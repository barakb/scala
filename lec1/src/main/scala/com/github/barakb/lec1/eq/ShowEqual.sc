



object ShowEqual {
  import com.github.barakb.lec1.typeclasses.Equal
  import com.github.barakb.lec1.typeclasses.Equal._

  "a" === "b"
  //  println("a" === 1) // will not compile
  println("a" === "a")
  println(List(1, 2) === List(1, 2))
  println(List(1) === List(1, 2))
  println(List(1, 3) === List(1, 2))
  //  println(List("1", "2") === List(1, 2)) // will not compile
  println(Map(1 -> 2) === Map(1 -> 2))
  //  println(Map(1 -> 2) === Map("1" -> 2)) // will not compile
  println(Map[Int, Int]() === Map(1 -> 2))
  println(Map(1 -> 3) === Map(1 -> 2))
  println(Map(2 -> 2) === Map(1 -> 2))
  println(Option(2) === Option(2))

  def areAllEq[A: Equal](la: List[A]): Boolean = {
    val firstO = la.headOption

    val ob = firstO.map(first => la.forall(e => e === first))
    ob.getOrElse(true)
  }

  //> areAllEq: [A](la: List[A])(implicit evidence$2: typeclasses.Equal[A])Boolean
  //|

  class Person(val name: String)


  //la.forall(e => e == la.head)

  //areAllEq(List.fill(10)(new Person("John")))
  areAllEq(List(Option(2), Option(2), None)) //> res0: Boolean = false
  areAllEq(List(1, 1, 1, 1, 1, 1, 1)) //> res1: Boolean = true
  areAllEq(List(1, 1, 1, 2, 1, 1, 1)) //> res2: Boolean = false

  //areAllEq(Nil)
}

