### Traits

Traits are a fundamental unit of code reuse in Scala. A trait encapsulates
method and field definitions, which can then be reused by mixing them into
classes. Unlike class inheritance, in which each class must inherit from just
one superclass, a class can mix in any number of traits.

A trait definition looks just like a class definition except that it uses the keyword trait.

```Scala

trait Philosophical {
    def philosophize() {
        println("I consume memory, therefore I am!")
    }
}

```


This trait is named Philosophical . It does not declare a superclass, so
like a class, it has the default superclass of AnyRef . It defines one method,
named philosophize , which is concrete. It’s a simple trait, just enough to
show how traits work.

Once a trait is defined, it can be mixed in to a class using either the
extends or with keywords. Scala programmers “mix in” traits rather than
inherit from them, because mixing in a trait has important differences from
the multiple inheritance found in many other languages.

```Scala

class Frog extends Philosophical {
    override def toString = "green"
}

```

You can use the extends keyword to mix in a trait; in that case you
implicitly inherit the trait’s superclass.


If you wish to mix a trait into a class that explicitly extends a superclass,
you use extends to indicate the superclass and with to mix in the trait.

```Scala

class Animal
class Frog extends Animal with Philosophical {
    override def toString = "green"
}


class Animal
trait HasLegs
class Frog extends Animal with Philosophical with HasLegs {
    override def toString = "green"
}

```

In the examples you’ve seen so far, class Frog has inherited an implementation of philosophize from trait Philosophical . Alternatively, Frog
could override philosophize . The syntax looks the same as overriding a
method declared in a superclass. Here’s an example:

```Scala

class Animal
class Frog extends Animal with Philosophical {
    override def toString = "green"
    override def philosophize() {
        println("It ain't easy being "+ toString +"!")
    }
}

```


Traits can, for
example, declare fields and maintain state. In fact, you can do anything in
a trait definition that you can do in a class definition, and the syntax looks
exactly the same, with only two exceptions. First, a trait cannot have any
“class” parameters, i.e., parameters passed to the primary constructor of a
class. In other words, although you could define a class like this:

```Scala

class Point(x: Int, y: Int)

```


The following attempt to define a trait would not compile:

```Scala

trait NoPoint(x: Int, y: Int) // Does not compile

```


The other difference between classes and traits is that whereas in classes,
super calls are statically bound, in traits, they are dynamically bound. If
you write “ super.toString ” in a class, you know exactly which method
implementation will be invoked. When you write the same thing in a trait,
however, the method implementation to invoke for the super call is undefined when you define the trait. Rather, the implementation to invoke will
be determined anew each time the trait is mixed into a concrete class. This
curious behavior of super is key to allowing traits to work as stackable modifications

#####Thin versus rich interfaces

One major use of traits is to automatically add methods to a class in terms
of methods the class already has. That is, traits can enrich a thin interface,
making it into a rich interface.
Thin versus rich interfaces represents a commonly faced trade-off in
object-oriented design. The trade-off is between the implementers and the
clients of an interface. A rich interface has many methods, which make it
convenient for the caller. Clients can pick a method that exactly matches
the functionality they need. A thin interface, on the other hand, has fewer
methods, and thus is easier on the implementers. Clients calling into a thin
interface, however, have to write more code. Given the smaller selection of
methods to call, they may have to choose a less than perfect match for their
needs and write extra code to use it.
Java’s interfaces are more often thin than rich. For example, interface
CharSequence , which was introduced in Java 1.4, is a thin interface common to all string-like classes that hold a sequence of characters. Here’s its
definition when seen as a Scala trait:

```Scala

trait CharSequence {
    def charAt(index: Int): Char
    def length: Int
    def subSequence(start: Int, end: Int): CharSequence
    def toString(): String
}

```

Although most of the dozens of methods in class String would apply to
any CharSequence , Java’s CharSequence interface declares only four methods. Had CharSequence instead included the full String interface, it would
have placed a large burden on implementers of CharSequence . Every programmer that implemented CharSequence in Java would have had to define
dozens more methods. Because Scala traits can contain concrete methods,
they make rich interfaces far more convenient.
Adding a concrete method to a trait tilts the thin-rich trade-off heavily
towards rich interfaces. Unlike in Java, adding a concrete method to a Scala
trait is a one-time effort. You only need to implement the method once, in
the trait itself, instead of needing to reimplement it for every class that mixes
in the trait. Thus, rich interfaces are less work to provide in Scala than in a
language without traits.
To enrich an interface using traits, simply define a trait with a small number of abstract methods—the thin part of the trait’s interface—and a potentially large number of concrete methods, all implemented in terms of the
abstract methods. Then you can mix the enrichment trait into a class, implement the thin portion of the interface, and end up with a class that has all of
the rich interface available.


#####The Ordered trait

Comparison is another domain where a rich interface is convenient. Whenever you compare two objects that are ordered, it is convenient if you use
a single method call to ask about the precise comparison you want. If you
want “is less than,” you would like to call < , and if you want “is less than
or equal,” you would like to call <= . With a thin comparison interface, you
might just have the < method, and you would sometimes have to write things
like `(x < y) || (x == y)`. A rich interface would provide you with all of
the usual comparison operators, thus allowing you to directly write things
like `x <= y`.
Before looking at Ordered , imagine what you might do without it. Suppose you took the Rational class and added comparison
operations to it. You would end up with something like this:

```Scala

class Rational(n: Int, d: Int) {
// ...
    def < (that: Rational) =
    this.numer * that.denom > that.numer * this.denom
    def > (that: Rational) = that < this
    def <= (that: Rational) = (this < that) || (this == that)
    def >= (that: Rational) = (this > that) || (this == that)
}

```
This class defines four comparison operators `( < , > , <= , and >= )`, and it’s a
classic demonstration of the costs of defining a rich interface. First, notice
that three of the comparison operators are defined in terms of the first one.
For example, `>` is defined as the reverse of `<` , and `<=` is defined as literally
“less than or equal.” Additionally, notice that all three of these methods
would be the same for any other class that is comparable. There is nothing
special about rational numbers regarding `<=` . In a comparison context, `<=` is
always used to mean “less than or equals.” Overall, there is quite a lot of boilerplate code in this class which would be the same in any other class that
implements comparison operations.

This problem is so common that Scala provides a trait to help with it. The
trait is called Ordered . To use it, you replace all of the individual comparison
methods with a single compare method. The Ordered trait then defines `< , > ,
<= , and >=` for you in terms of this one method. Thus, trait Ordered allows
you to enrich a class with comparison methods by implementing only one
method, compare .

Here is how it looks if you define comparison operations on Rational
by using the Ordered trait:

```Scala

class Rational(n: Int, d: Int) extends Ordered[Rational] {
// ...
    def compare(that: Rational) = (this.numer * that.denom) - (that.numer * this.denom)
}

```
There are just two things to do. First, this version of Rational mixes in the
Ordered trait. Unlike the traits you have seen so far, Ordered requires you
to specify a type parameter when you mix it in.
The second thing you need to do is define a compare method for comparing two objects. This method should compare the receiver, this , with
the object passed as an argument to the method. It should return an integer
that is zero if the objects are the same, negative if receiver is less than the
argument, and positive if the receiver is greater than the argument. In this
case, the comparison method of Rational uses a formula based on converting the fractions to a common denominator and then subtracting the resulting
numerators. Given this mixin and the definition of compare , class Rational
now has all four comparison methods:

```Bash

scala> val half = new Rational(1, 2)
half: Rational = 1/2
scala> val third = new Rational(1, 3)
third: Rational = 1/3

scala> half < third
res5: Boolean = false
scala> half > third
res6: Boolean = true

```
Any time you implement a class that is ordered by some comparison,
you should consider mixing in the Ordered trait. If you do, you will provide
the class’s users with a rich set of comparison methods.
Beware that the Ordered trait does not define equals for you, because
it is unable to do so. The problem is that implementing equals in terms of
compare requires checking the type of the passed object, and because of type
erasure, Ordered itself cannot do this test. Thus, you need to define equals
yourself, even if you inherit Ordered . You’ll find out how to go about this

#####Traits as stackable modifications

You have now seen one major use of traits: turning a thin interface into a
rich one. Now we’ll turn to a second major use: providing stackable modifications to classes. Traits let you modify the methods of a class, and they do
so in a way that allows you to stack those modifications with each other.
As an example, consider stacking modifications to a queue of integers.
The queue will have two operations: put , which places integers in the queue,
and get , which takes them back out. Queues are first-in, first-out, so get
should return the integers in the same order they were put in the queue.
Given a class that implements such a queue, you could define traits to
perform modifications such as these:

• Doubling : double all integers that are put in the queue
• Incrementing : increment all integers that are put in the queue
• Filtering : filter out negative integers from a queue

These three traits represent modifications, because they modify the behavior of an underlying queue class rather than defining a full queue class
themselves. The three are also stackable. You can select any of the three
you like, mix them into a class, and obtain a new class that has all of the
modifications you chose.


An abstract IntQueue class is shown.

```Scala

abstract class IntQueue {
    def get(): Int
    def put(x: Int)
}

```

 
IntQueue has a put method that adds new integers to the queue and a get method that
removes and returns them.
 
 
A basic implementation of IntQueue that uses an ArrayBuffer is shown

```Scala

import scala.collection.mutable.ArrayBuffer
    class BasicIntQueue extends IntQueue {
        private val buf = new ArrayBuffer[Int]
        def get() = buf.remove(0)
        def put(x: Int) { buf += x }
        
```


Class BasicIntQueue has a private field holding an array buffer. The
get method removes an entry from one end of the buffer, while the put
method adds elements to the other end. Here’s how this implementation
looks when you use it:

```Bash

scala> val queue = new BasicIntQueue
queue: BasicIntQueue = BasicIntQueue@24655f
scala> queue.put(10)
scala> queue.put(20)
scala> queue.get()
res9: Int = 10
scala> queue.get()
res10: Int = 20

```
So far so good. Now take a look at using traits to modify this behavior.

Doubling is a trait that doubles integers as they are put in the queue.

```Scala

trait Doubling extends IntQueue {
    abstract override def put(x: Int) { super.put(2 * x) }
}

```

The Doubling trait has two funny things going on. 
The first is that it declares a superclass, IntQueue . This declaration means that the trait can only be
mixed into a class that also extends IntQueue . Thus, you can mix Doubling into BasicIntQueue, but not into Rational.

The second funny thing is that the trait has a super call on a method
declared abstract. Such calls are illegal for normal classes, because they
will certainly fail at run time. For a trait, however, such a call can actually
succeed. Since super calls in a trait are dynamically bound, the super call
in trait Doubling will work so long as the trait is mixed in after another trait
or class that gives a concrete definition to the method.
This arrangement is frequently needed with traits that implement stackable modifications. To tell the compiler you are doing this on purpose, you
must mark such methods as abstract override . This combination of modifiers is only allowed for members of traits, not classes, and it means that
the trait must be mixed into some class that has a concrete definition of the
method in question.
There is a lot going on with such a simple trait, isn’t there! Here’s how
it looks to use the trait:

```Bash

scala> class MyQueue extends BasicIntQueue with Doubling
defined class MyQueue
scala> val queue = new MyQueue
queue: MyQueue = MyQueue@91f017
scala> queue.put(10)
scala> queue.get()
res12: Int = 20

```


In the first line in this interpreter session, we define class MyQueue , which
extends BasicIntQueue and mixes in Doubling . We then put a 10 in the
queue, but because Doubling has been mixed in, the 10 is doubled. When
we get an integer from the queue, it is a 20.


Note that MyQueue defines no new code. It simply identifies a class and
mixes in a trait. In this situation, you could supply “ BasicIntQueue with
Doubling ” directly to new instead of defining a named class. It would look
as shown:

```Bash 

scala> val queue = new BasicIntQueue with Doubling
queue: BasicIntQueue with Doubling = $anon$1@5fa12d
scala> queue.put(10)
scala> queue.get()
res14: Int = 20

```

To see how to stack modifications, we need to define the other two modification traits, Incrementing and Filtering . Implementations of these
traits are shown in:

```Scala

trait Incrementing extends IntQueue {
    abstract override def put(x: Int) { super.put(x + 1) }
}

trait Filtering extends IntQueue {
    abstract override def put(x: Int) {
        if (x >= 0) super.put(x)
    }
}

```


Given these modifications, you can now pick and choose which ones you
want for a particular queue. For example, here is a queue that both filters
negative numbers and adds one to all numbers that it keeps:

```Bash

scala> val queue = (new BasicIntQueue with Incrementing with Filtering)
queue: BasicIntQueue with Incrementing with Filtering...
scala> queue.put(-1); queue.put(0); queue.put(1)
scala> queue.get()
res15: Int = 1
scala> queue.get()
res16: Int = 2

```

The order of mixins is significant, If you reverse the order, first integers will be incremented, and then the integers that are still negative will be discarded:

```Bash

scala> val queue = (new BasicIntQueue
with Filtering with Incrementing)
queue: BasicIntQueue with Filtering with Incrementing...
scala> queue.put(-1); queue.put(0); queue.put(1)
scala> queue.get()
res17: Int = 0
scala> queue.get()
res18: Int = 1
scala> queue.get()
res19: Int = 2

```



#####To trait, or not to trait?

Whenever you implement a reusable collection of behavior, you will have to
decide whether you want to use a trait or an abstract class. There is no firm
rule, but this section contains a few guidelines to consider.

*If the behavior will not be reused*, then make it a concrete class. It is not
reusable behavior after all.

*If it might be reused in multiple, unrelated classes*, make it a trait. Only
traits can be mixed into different parts of the class hierarchy.

*If you want to inherit from it in Java code*, use an abstract class. Since
traits with code do not have a close Java analog, it tends to be awkward to
inherit from a trait in a Java class. Inheriting from a Scala class, meanwhile,
is exactly like inheriting from a Java class. As one exception, a Scala trait
with only abstract members translates directly to a Java interface, so you
should feel free to define such traits even if you expect Java code to inherit
from it.

*If you plan to distribute it in compiled form*, and you expect outside
groups to write classes inheriting from it, you might lean towards using an
abstract class. The issue is that when a trait gains or loses a member, any
classes that inherit from it must be recompiled, even if they have not changed.
If outside clients will only call into the behavior, instead of inheriting from it, then using a trait is fine.

*If efficiency is very important*, lean towards using a class. Most Java
runtimes make a virtual method invocation of a class member a faster oper-
ation than an interface method invocation. Traits get compiled to interfaces
and therefore may pay a slight performance overhead. However, you should
make this choice only if you know that the trait in question constitutes a per-
formance bottleneck and have evidence that using a class instead actually
solves the problem.

*If you still do not know*, after considering the above, then start by making
it as a trait. You can always change it later, and in general using a trait keeps
more options open.    

###HW

#### Either
 
Like Option, Either is a container type. Unlike the aforementioned types, it takes not only one, but two type parameters: An Either[A, B] instance can contain either an instance of A, or an instance of B. This is different from a Tuple2[A, B], which contains both an A and a B instance.

Either has exactly two sub types, Left and Right. If an Either[A, B] object contains an instance of A, then the Either is a Left. Otherwise it contains an instance of B and is a Right.

There is nothing in the semantics of this type that specifies one or the other sub type to represent an error or a success, respectively. In fact, Either is a general-purpose type for use whenever you need to deal with situations where the result can be of one of two possible types. Nevertheless, error handling is a popular use case for it, and by convention, when using it that way, the Left represents the error case, whereas the Right contains the success value.


#####Creating an Either

Creating an instance of Either is trivial. Both Left and Right are case classes, so if we want to implement a rock-solid internet censorship feature, we can just do the following:

```Scala

import scala.io.Source
import java.net.URL
def getContent(url: URL): Either[String, Source] =
  if (url.getHost.contains("google"))
    Left("Requested URL is blocked for the good of the people!")
  else
    Right(Source.fromURL(url))
    
```

Now, if we call getContent(new URL("http://danielwestheide.com")), we will get a scala.io.Source wrapped in a Right. If we pass in new URL("https://plus.google.com"), the return value will be a Left containing a String.

#####Working with Either values

Some of the very basic stuff works just as you know from Option or Try: You can ask an instance of Either if it isLeft or isRight. You can also do pattern matching on it, which is one of the most familiar and convenient ways of working with objects of this type:

```Scala

getContent(new URL("http://google.com")) match {
  case Left(msg) => println(msg)
  case Right(source) => source.getLines.foreach(println)
}

```

You cannot, at least not directly, use an Either instance like a collection, the way you are familiar with from Option. This is because Either is designed to be unbiased.

Option is success-biased: it offers you map, flatMap and other methods that all work under the assumption that the Option is a Success, and if that’s not the case, they effectively don’t do anything, returning the None as-is.

The fact that Either is unbiased means that you first have to choose whether you want to work under the assumption that it is a Left or a Right. By calling left or right on an Either value, you get a LeftProjection or RightProjection, respectively, which are basically left or right-biased wrappers for the Either.

Mapping

Once you have a projection, you can call map on it:

```Scala

val content: Either[String, Iterator[String]] =
  getContent(new URL("http://danielwestheide.com")).right.map(_.getLines())
// content is a Right containing the lines from the Source returned by getContent
val moreContent: Either[String, Iterator[String]] =
  getContent(new URL("http://google.com")).right.map(_.getLines)
// moreContent is a Left, as already returned by getContent

```

Regardless of whether the Either[String, Source] in this example is a Left or a Right, it will be mapped to an Either[String, Iterator[String]]. If it’s called on a Right, the value inside it will be transformed. If it’s a Left, that will be returned unchanged.

We can do the same with a LeftProjection, of course:

```Scala

val content: Either[Iterator[String], Source] =
  getContent(new URL("http://danielwestheide.com")).left.map(Iterator(_))
// content is the Right containing a Source, as already returned by getContent
val moreContent: Either[Iterator[String], Source] =
  getContent(new URL("http://google.com")).left.map(Iterator(_))
// moreContent is a Left containing the msg returned by getContent in an Iterator

```

Now, if the Either is a Left, its wrapped value is transformed, whereas a Right would be returned unchanged. Either way, the result is of type Either[Iterator[String], Source].

Please note that the map method is defined on the projection types, not on Either, but it does return a value of type Either, not a projection. In this, Either deviates from the other container types you know. The reason for this has to do with Either being unbiased, but as you will see, this can lead to some very unpleasant problems in certain cases. It also means that if you want to chain multiple calls to map, flatMap and the like, you always have to ask for your desired projection again in between.

I’m putting very high requirements on your suspension of disbelief now, coming up with a completely contrived example. Let’s say we want to calculate the average number of lines of two of my articles. You’ve always wanted to do that, right? Here’s how we could solve this challenging problem:

```Scala

val part5 = new URL("http://t.co/UR1aalX4")
val part6 = new URL("http://t.co/6wlKwTmu")
val content = getContent(part5).right.map(a =>
  getContent(part6).right.map(b =>
    (a.getLines().size + b.getLines().size) / 2))
What we’ll end up with is an Either[String, Either[String, Int]]. Now, content being a nested structure of Rights, we could flatten it by calling the joinRight method on it (you also have joinLeft available to flatten a nested structure of Lefts).

```

However, we can avoid creating this nested structure altogether. If we flatMap on our outer RightProjection, we get a more pleasant result type, unpacking the Right of the inner Either:

```Scala

val content = getContent(part5).right.flatMap(a =>
  getContent(part6).right.map(b =>
    (a.getLines().size + b.getLines().size) / 2))
Now content is a flat Either[String, Int], which makes it a lot nicer to work with, for example using pattern matching. 

```


Here are some more interesting usage of Either:

```Scala

    def mean(xs: IndexedSeq[Double]): Either[String, Double] =
      if (xs.isEmpty)
        Left("mean of empty list!")
      else
        Right(xs.sum / xs.length)


    def safeDiv(x: Int, y: Int): Either[Exception, Int] =
      try Right(x / y)
      catch {   
        case e: Exception => Left(e)
      }


    def Try[A](a: => A): Either[Exception, A] =
      try Right(a)
      catch {
        case e: Exception => Left(e)
      }

```

### Implement the following method and their tests on Either (defined in the file Hw4.scala)
Given trait definition:  

```Scala

sealed trait Either[+E, +A] 

```

* Implement `def map[B](f: A => B): Either[E, B]`
* Implement `def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B]`
* Implement `def orElse[EE >: E,B >: A](b: => Either[EE, B]): Either[EE, B]`
  
 Implement sequence and traverse for Either . These should return the first error
 that’s encountered, if there is one.
 
* `def sequence[E, A](es: List[Either[E, A]]): Either[E, List[A]]`
* `def traverse[E, A, B](as: List[A])(f: A => Either[E, B]): Either[E, List[B]]`


 


