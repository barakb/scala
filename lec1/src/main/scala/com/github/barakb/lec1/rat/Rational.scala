package com.github.barakb.lec1.rat

/**
  * Created by Barak Bar Orion
  * on 7/13/16.
  * An example of a class pa
  * @since 12.0
  */

//case class Rational (n : Int, d : Int)


// 2. with toString
/*
class Rational (n : Int, d : Int){
  override def toString: String = n + "/" + d
}*/

//3 checking pre condition
/*
class Rational (n : Int, d : Int) {

  require(d != 0)

  override def toString: String = n + "/" + d
}

*/

//adding the add method take one
/*
class Rational (n : Int, d : Int) {

  require(d != 0)

  override def toString: String = n + "/" + d

  def add(r : Rational): Rational = {
    new Rational(r.n + this.n, r.d + this.d)
  }
}
*/

// adding the add method take 2
/*
class Rational (n : Int, d : Int) {

  require(d != 0)
  val number : Int = n
  val denom : Int = d

  override def toString: String = number + "/" + denom

  def add(r: Rational): Rational = {
    new Rational(r.number * denom +  number * r.denom ,  denom * r.denom)
  }
}
*/
// Secondary constractor
/*
class Rational (val number : Int, val denom : Int) {

  require(denom != 0)
//  val number : Int = n
//  val denom : Int = d

  override def toString: String = number + "/" + denom

  def add(r: Rational): Rational = {
    new Rational(r.number * denom +  number * r.denom ,  denom * r.denom)
  }
  def this(n : Int) = this(n, 1)
}
*/

// private methods
/*
class Rational (n : Int, d : Int) {

  require(d != 0)


  private val g = gcd(n, d)

  val number : Int = n / g
  val denom : Int = d / g

  override def toString: String = number + "/" + denom

  def add(r: Rational): Rational = {
    new Rational(r.number * denom +  number * r.denom ,  denom * r.denom)
  }
  def this(n : Int) = this(n, 1)

  private def gcd(a: Int, b: Int) : Int = if (b == 0) a else gcd(b, a % b)

}
*/

// using natural operation name
/*
class Rational (n : Int, d : Int) {

  require(d != 0)


  private val g = gcd(n, d)

  val number : Int = n / g
  val denom : Int = d / g

  override def toString: String = number + "/" + denom

  def +(r: Rational): Rational = {
    new Rational(r.number * denom +  number * r.denom ,  denom * r.denom)
  }
  def this(n : Int) = this(n, 1)

  private def gcd(a: Int, b: Int) : Int = if (b == 0) a else gcd(b, a % b)

}
*/

// operator overloading
class Rational (n : Int, d : Int) {

  require(d != 0)


  private val g = gcd(n, d)

  val number : Int = n / g
  val denom : Int = d / g

  override def toString: String = number + "/" + denom

  def +(r: Rational): Rational = {
    new Rational(r.number * denom +  number * r.denom ,  denom * r.denom)
  }
  def +(n: Int): Rational = {
    new Rational(n * denom +  number ,  denom)
  }
  def this(n : Int) = this(n, 1)

  private def gcd(a: Int, b: Int) : Int = if (b == 0) a else gcd(b, a % b)

}
//

object Rational {
   def apply(n : Int, d : Int): Rational = new Rational(n, d)
}