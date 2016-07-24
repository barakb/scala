### TypeParameters

* Explain the point of `Option`
```Scala 

 def mean(xs: Seq[Double]): Option[Double] =
      if (xs.isEmpty) None
      else Some(xs.sum / xs.length)

```

* Write the option class

* Explain +T -T mention the term `covariance`, `contravariance` `invariant`  https://en.wikipedia.org/wiki/Covariance_and_contravariance_(computer_science) 
* Explain lower bounds ([T >: A]) upper bounds ([T <: A])
* Implements option `map[B](f: A => B): Option[B]`, `getOrElse[B >: A](default: => B): B`

#### Usa case for option

```Scala

case class Employee(name: String, department: String)

def lookupByName(name: String): Option[Employee] = ...

val joeDepartment: Option[String] =

lookupByName("Joe").map(_.department)

```

* Define binary tree using type parameter, mention the terms `polymorphic function` `type constractor` 
 
 
### HW

####Add your tests to Option1Spec.scala

* Implement option `flatMap[B](f: A => Option[B]): Option[B]`
* Implement option `orElse[B >: A](ob: => Option[B]): Option[B]`
* Implement option `filter(f: A => Boolean): Option[A]`
* Implement a function `def lift[A,B](f: A => B): Option[A] => Option[B]` that take a function from A to B and return a function from Option[A] to Option[B]  
* Write a function sequence that combines a list of Option s into one Option containing a list of all the Some values in the original list. If the original list contains None even 
  once, the result of the function should be None ; otherwise the result should be Some with a list of all the values. Here is its signature `sequence[A](a: List[Option[A]]): Option[List[A]]`
* Sometimes we’ll want to map over a list using a function that might fail, returning None if applying it to any element of the list returns None . For example, what if we have
   a whole list of String values that we wish to parse to Option[Int] ? In that case, we can simply sequence the results of the map : def parseInts(a: List[String]): Option[List[Int]] = sequence(a map (i => Try(i.toInt)))
   Unfortunately, this is inefficient, since it traverses the list twice, first to convert each
   String to an Option[Int] , and a second pass to combine these Option[Int] values into an Option[List[Int]] . Wanting to sequence the results of a map this way is a common enough occurrence to warrant a new generic function, traverse , with the
   following signature: `traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]]` 
 

####Add your tests to List1Spec.scala
* Define map, flatMap and filter on List
 
 
