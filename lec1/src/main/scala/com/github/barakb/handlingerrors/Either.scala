package com.github.barakb.handlingerrors

/**
  * Created by Barak Bar Orion
  * on 8/6/17.
  */
sealed trait Either[+E, +A]{
  // home work
  // Implement versions of map,flatMap, orElse, and map2 on Either that operate on the Right value.
  def map[B](f: A => B): Either[E, B] = ???
  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = ???
  def orElse[EE >: E,B >: A](b: => Either[EE, B]): Either[EE, B] = ???
  def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] = ???

}

case class Left[+E](value: E) extends Either[E, Nothing]

case class Right[+A](value: A) extends Either[Nothing, A]


object EitherMain extends App {
  def mean(xs: IndexedSeq[Double]): Either[String, Double] = if (xs.isEmpty)
    Left("mean of empty list!")
  else
    Right(xs.sum / xs.length)


  def safeDiv1(x: Int, y: Int): Either[Exception, Int] =
    try Right(x / y)
    catch {
      case e: Exception => Left(e)
    }

  def safeDiv2(x: Int, y: Int): Either[Exception, Int] = Try(x / y)

  def Try[A](a: => A): Either[Exception, A] =
    try Right(a)
    catch {
      case e: Exception => Left(e)
    }



  def parseInsuranceRateQuote(age: String, numberOfSpeedingTickets: String): Either[Exception,Double] =
    for {
      a <- Try { age.toInt }
      tickets <- Try { numberOfSpeedingTickets.toInt }
    } yield insuranceRateQuote(a, tickets)

  def insuranceRateQuote(optAge: Int, optTickets: Int): Double = ???


  /*
  // home work
  Implement sequence and traverse for Either . These should return the first error
  that’s encountered, if there is one.
  */

  def sequence[E, A](es: List[Either[E, A]]): Either[E, List[A]] = ???
  def traverse[E, A, B](as: List[A])(f: A => Either[E, B]): Either[E, List[B]] = ???

}

