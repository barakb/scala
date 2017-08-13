package com.github.barakb.handlingerrors

/**
  * Created by Barak Bar Orion
  * on 8/6/17.
  */
case class Employee(name: String, department: String, manager: Option[Employee])

object EmployeeMain extends App {
  val jm = Employee("JoeManager", "Accounting", None)
  val j = Employee("Joe", "Accounting", Some(jm))
  val m = Map("Joe" -> j, "joeManager" -> jm)

  def lookupByName(name: String): Option[Employee] = m.get(name)

  val joeDepartment: Option[String] = lookupByName("Joe").map(_.department)
  val joeManager: Option[Employee] = lookupByName("Joe").flatMap(_.manager)
  val joeDepartmentWithDefault: String = lookupByName("Joe").map(_.department).getOrElse("Default Dept.")
  println(joeManager)
  println(joeDepartmentWithDefault)

//  given emp name return the manager name
  def foo(name:String): Option[String] = {
//    val ret: Option[Employee] = m.get(name)
//    ret.flatMap(_.manager).map(_.name) getOrElse(null)
    for{
      ret <- m.get(name)
      manager <- ret.manager
    } yield manager.name
  }

  println("foo1:" + foo("Joe"))
  println("foo2:" + foo("Joe1"))
/*
  We can use filter to convert successes into failures if the successful values don’t
  match the given predicate. A common pattern is to transform an Option via calls to
  map , flatMap , and/or filter , and then use getOrElse to do error handling at the end:
  val dept: String =
    lookupByName("Joe").
      map(_.department).
      filter(_ != "Accounting").
      getOrElse("Default Dept")
  */


  /*
  It may be easy to jump to the conclusion that once we start using Option , it infects our
  entire code base. One can imagine how any callers of methods that take or return
  Option will have to be modified to handle either Some or None . But this doesn’t happen,
  and the reason is that we can lift ordinary functions to become functions that operate on Option .

  For example, the map function lets us operate on values of type Option[A] using a
  function of type A => B , returning Option[B] . Another way of looking at this is that map
  turns a function f of type A => B into a function of type Option[A] => Option[B] . Let’s

  make this explicit:
   def lift[A,B](f: A => B): Option[A] => Option[B] = _ map f
   This tells us that any function that we already have lying around can be transformed
   (via lift ) to operate within the context of a single Option value. Let’s look at an example:
   val absO: Option[Double] => Option[Double] = lift(math.abs)

   */
}
