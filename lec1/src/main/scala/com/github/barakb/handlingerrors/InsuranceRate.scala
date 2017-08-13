package com.github.barakb.handlingerrors

/**
  * Created by Barak Bar Orion
  * on 8/6/17.
  */
object InsuranceRate {


  def insuranceRateQuoteOption(optAge: Option[Int], optTickets: Option[Int]): Option[Double] = ???

  def parseInsuranceRateQuote(age: String, numberOfSpeedingTickets: String): Option[Double] = {
    val optAge: Option[Int] = Try(age.toInt)
    val optTickets: Option[Int] = Try(numberOfSpeedingTickets.toInt)
    insuranceRateQuoteOption(optAge, optTickets)
  }

  def Try[A](a: => A): Option[A] =
    try Some(a)
    catch {
      case e: Exception => None
    }


  /* home work
    Write a generic function map2 that combines two Option values using a binary function.
    If either Option value is None , then the return value is too. Here is its signature:
   */

  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = ???


  /*
  With map2 , we can now implement parseInsuranceRateQuote :
   */
  def parseInsuranceRateQuote2(age: String, numberOfSpeedingTickets: String): Option[Double] = {
    val optAge: Option[Int] = Try {
      age.toInt
    }
    val optTickets: Option[Int] = Try {
      numberOfSpeedingTickets.toInt
    }
    map2(optAge, optTickets)(insuranceRateQuote)
  }

  def insuranceRateQuote(optAge: Int, optTickets: Int): Double = ???

  // home work
  /*
  Write a function sequence that combines a list of Option s into one Option containing
  a list of all the Some values in the original list. If the original list contains None even
  once, the result of the function should be None ; otherwise the result should be Some
  with a list of all the values. Here is its signature:
  */

  def sequence[A](a: List[Option[A]]): Option[List[A]] = ???


  def parseInts(a: List[String]): Option[List[Int]] =
    sequence(a map (i => Try(i.toInt)))


  // home work
  /*
  Unfortunately, this is inefficient, since it traverses the list twice, first to convert each
    String to an Option[Int] , and a second pass to combine these Option[Int] values
  into an Option[List[Int]] . Wanting to sequence the results of a map this way is a
    common enough occurrence to warrant a new generic function, traverse , with the
  following signature:
  */
  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = ???


  // After the home work is done, explain For-comprehensions
  // implement map2 using For-comprehensions
}
