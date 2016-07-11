### What is Scala
Scala is a general-purpose programming language designed to express common pro-
gramming patterns in a concise, elegant, and type-safe way. It smoothly integrates fea-
tures of object-oriented and functional programming languages, enabling
programmers to be more productive. Martin Odersky (the creator of Scala) and his
team started development on Scala in 2001 in the programming methods laboratory
at EPFL (École Polytechnique Fédérale de Lausanne). Scala made its public debut in
January 2004 on the JVM platform.

Scala uses a pure object-oriented model similar to that
of Smalltalk 4 (a pure object-oriented language created by Alan Kay around 1980),
where every value is an object, and every operation is a message send. Here’s a simple
expression:
`1 + 2`
In Scala this expression is interpreted as 1.+(2) by the Scala compiler. That means
you’re invoking a + operation on an integer object (in this case, 1 ) by passing 2 as a
parameter. Scala treats operator names like ordinary identifiers. An identifier in Scala is
either a sequence of letters and digits starting with a letter or a sequence of operator
characters. In addition to `+` , it’s possible to define methods like `<=` , `-`, or `*`.

#### Scala as a functional language

_Functional programming_ is a programming paradigm
that treats computation as the evaluation of mathematical functions and avoids state
and mutable data.

#### Scala as a scalable and extensible language
Scala stands for scalable language. 7 One of the design goals of Scala is to create a lan-
guage that will grow and scale with your demand. Scala is suitable for use as a scripting
language, as well as for large enterprise applications. Scala’s component abstraction,
succinct syntax, and support for both object-oriented and functional programming
make the language scalable.
Scala also provides a unique combination of language mechanisms that makes it
easy to add new language constructs in the form of libraries. You could use any
method as an infix or postfix operator, and closures in Scala can be passed as “pass by
name” arguments to other functions. These features make it easier for developers to define new constructs.

#### Scala does more with less code

```Java

boolean hasUpperCase = false;
for(int i = 0; i < name.length(); i++) {
     if(Character.isUpperCase(name.charAt(i))) {
         hasUpperCase = true;
         break;
     }
}

```

```Scala

val hasUpperCase = name.exists(_.isUpper)

```

#### building

`mvn install`

```bash

java -cp $SCALA_HOME/lib/scala-library.jar:target/lec1-1.0-SNAPSHOT.jar com.github.barakb.lec1.hellow.HelloWorld
Hello, world!

```


##### Scala shell


```
> scala
This is a Scala shell.
Type in expressions to have them evaluated.
Type :help for more information.
scala> object HelloWorld {
    |   def main(args: Array[String]): Unit = {
    |     println("Hello, world!")
    |   }
    | }
defined module HelloWorld
scala> HelloWorld.main(Array())
Hello, world!
scala>:q

```


