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
A java code

```Java

boolean hasUpperCase = false;
for(int i = 0; i < name.length(); i++) {
     if(Character.isUpperCase(name.charAt(i))) {
         hasUpperCase = true;
         break;
     }
}

```

Same using Scala.

```Scala

val hasUpperCase = name.exists(_.isUpper)

```


##### Defining a Programmer class in Java

```Java

public class Programmer {
    private String name;
    private String language;
    private String favDrink;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getFavDrink() {
        return favDrink;
    }
    public void setFavDrink(String favDrink) {
        this.favDrink = favDrink;
    }
}

```
Same with Scala


```Scala

class Programmer(var name:String,var language:String,var favDrink:String)

```

* Static typing vs dynamic typing


#### Basic types

|Value type| Description and range|
|----------|----------------------|
|Byte | 8-bit signed 2’s complement integer. It has minimum value of –128 and a maximum value of 127 (inclusive).|
|Short |16-bit signed 2’s complement integer. It has a minimum value of –32,768 and maximum of 32,767 (inclusive).|
|Int | 32-bit signed 2’s complement integer. It has a minimum value of –2,147,483,648 and a maximum value of 2,147,483,647 (inclusive).|
|Long | 64-bit signed 2’s complement integer. It has a minimum value of -9,223,372,036,854,775,808 and a maximum value of 9,223,372,036,854,775,807 (inclusive).|
|Float| A single-precision 32-bit IEEE 754 floating point.|
|Double | A double-precision 64-bit IEEE 754 floating point.|
|Boolean | Two possible values: true and false.|
|Char | A single 16-bit Unicode character. It has a minim.|


### Show in REAPLE
   
* String interpolation
* XML LITERALS

```Scala
    
    val version = 12
    
    val app = <app>
    <title>XAP</title>
    <version>{version}</version>
    <date>{new java.util.Date()}</date>
    </app>
    
```
* val vs var
* tuples
* default value initialization `var willKnowLater:String = _`
* lazy 

```Shell

scala> var a = 1
a: Int = 1
scala> lazy val b = a + 1
b: Int = <lazy>
scala> a = 5
a: Int = 5
scala> b
res1: Int = 6

```

* variable declaration can sometimes have a pattern on the left side

```Shell

scala> val first :: rest = List(1, 2, 3)
first: Int = 1
rest: List[Int] = List(2, 3)

scala> val (a, b) = (1, 2)
a: Int = 1
b: Int = 2


```

* Defining functions `def welcome(name: String) :String = {"Exciting times ahead" + name }`
`def welcome() :String = {"Exciting times ahead" }`
`def welcome :String = "Exciting times ahead"`
`def welcome = "Exciting times ahead"`
* return is optional `def max(a: Int, b: Int) = if(a > b) a else b`
* polymorphic function, type parameter `def toList[A](value:A) = List(value)`
* function as objects

```Shell

scala> val evenNumbers = List(2, 4, 6, 8, 10)
evenNumbers: List[Int] = List(2, 4, 6, 8, 10)

scala> evenNumbers.foldLeft(0) { (a: Int, b:Int) => a + b }
res14: Int = 30

scala> evenNumbers.foldLeft(0) { (a, b) => a + b }
res15: Int = 30

scala> evenNumbers.foldLeft(0) { _ + _ }
res16: Int = 30

```

```Scala

val breakException = new RuntimeException("break exception")
def breakable(op: => Unit) {
    try {
        op
    } catch { case _ => }
}

def break = throw breakException

def install = {
    val env = System.getenv("SCALA_HOME")
    if(env == null) break
    println("found scala home lets do the real work")
}


breakable {
    val env = System.getenv("SCALA_HOME")
    if(env == null) break
    println("found scala home lets do the real work")
}
```

* If is an expression

```Shell

scala> if (1 < 2) 3 else 4
res26: Int = 3

scala> if (1 < 2) 3 else ""
res27: Any = 3

scala> if (1 < 2) Array(3) else Array("")
res30: Array[_ >: Int with String] = Array(3)

```

* for `‘for’ (‘(’ Enumerators ‘)’ | ‘{’ Enumerators ‘}’) {nl} [‘yield’] Expr`

```Scala

val files = new java.io.File(".").listFiles
for(file <- files) {
    val filename = file.getName
    if(fileName.endsWith(".scala")) println(file)
}


for(
    file <- files;
    fileName = file.getName;
    if(fileName.endsWith(".scala"))
) println(file)

```

```Shell

scala> val aList = List(1, 2, 3)
aList: List[Int] = List(1, 2, 3)
scala> val bList = List(4, 5, 6)
bList: List[Int] = List(4, 5, 6)
scala> for { a <- aList; b <- bList } println(a + b)
5
6
7
6
7
8
7
8
9

scala> for { a <- aList; b <- bList } yield a + b
res31: List[Int] = List(5, 6, 7, 6, 7, 8, 7, 8, 9)


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


