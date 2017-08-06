# Handling errors without exceptions
### referentially transparent


````Scala
def failingFn(i: Int): Int = {
  val y: Int = throw new Exception("fail!")
  try {
    val x = 42 + 5
    x + y
  }
  catch { case e: Exception => 43 }
}
````

````Scala
def failingFn(i: Int): Int = {
  try {
    val y: Int = throw new Exception("fail!")
    val x = 42 + 5
    x + y
  }
  catch { case e: Exception => 43 }
}
````

* Another way of understanding RT is that the meaning of RT expressions does not depend
on context and may be reasoned about locally, whereas the meaning of non-RT expressions is context-dependent and requires more global reasoning. For instance,
the meaning of the RT expression `42 + 5` doesn’t depend on the larger expression it’s embedded
in it’s always and forever equal to `47` .
But the meaning of the expression throw new Exception("fail") is very context-dependent—as we just demonstrated, it takes on
different meanings depending on which try block (if any) it’s nested within.
There are two main problems with exceptions:
`. As we just discussed, exceptions break RT and introduce context dependence, moving us
away from the simple reasoning of the substitution model and making it possible
to write confusing exception-based code. This is the source of the folklore advice
that exceptions should be used only for error handling, not for control flow.

2. Exceptions are not type-safe. The type of failingFn , `Int => Int` tells us nothing
about the fact that exceptions may occur, and the compiler will certainly not
force callers of failingFn to make a decision about how to handle those exceptions.
If we forget to check for an exception in failingFn , this won’t be detected until runtime.

````Scala
def mean(xs: Seq[Double]): Double =
  if (xs.isEmpty)
   throw new ArithmeticException("mean of empty list!")
   else xs.sum / xs.length
````

Using `Double.NaN`

* It allows errors to silently propagate
* Besides being error-prone, it results in a fair amount of boilerplate code at call
  sites, with explicit if statements to check whether the caller has received a
  “real” result.
* It’s not applicable to polymorphic code.
* It demands a special policy or calling convention of callers.

Another try.

````Scala
def mean_1(xs: IndexedSeq[Double], onEmpty: Double): Double =
  if (xs.isEmpty) onEmpty
  else xs.sum / xs.length
````

* it requires that immediate callers have direct knowledge of how to handle the undefined case and limits them to returning a Double .

The 3rd **Option**
 
````Scala
def mean(xs: Seq[Double]): Option[Double] =
  if (xs.isEmpty) None
  else Some(xs.sum / xs.length)
````