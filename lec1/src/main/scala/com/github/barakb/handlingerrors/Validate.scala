package com.github.barakb.handlingerrors

/**
  * Created by Barak Bar Orion
  * on 8/6/17.
  */
object Validate {

//  here’s an application of map2 , where the function mkPerson validates both the given name and the given age before constructing a valid Person.

  case class Person(name: Name, age: Age)

  sealed class Name(val value: String)

  sealed class Age(val value: Int)

  def mkName(name: String): Either[String, Name] =
    if (name == "" || name == null) Left("Name is empty.")
    else Right(new Name(name))

  def mkAge(age: Int): Either[String, Age] =
    if (age < 0) Left("Age is out of range.")
    else Right(new Age(age))

  def mkPerson(name: String, age: Int): Either[String, Person] =
    mkName(name).map2(mkAge(age))(Person)
}
