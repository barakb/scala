#### How and what Scala add to java Integer

RichInt

Predef


#### Working with lists


```Scala
val oneTwo = List(1, 2)
// show how to create oneTwo using cons
val threeFour = List(3, 4)
// append
val oneTwoThreeFour = oneTwo ::: threeFour
```

head
tail
exists 
map // for + yield
flatMap
count
drop
filter
forall
foreach
isEmpty
mkString
reverse
sort
collect

apply

####Partial function

```Scala
val fraction = new PartialFunction[Int, Int] {
  def apply(d: Int) = 42 / d
  def isDefinedAt(d: Int) = d != 0
}
```

#### Set yet another collection type

create 
add
union
intersection
apply
can be view as partial function


#####   Pattern Matching

A better switch Statement

```Scala

// With side effects

val firstArg = if (args.length > 0) args(0) else ""
    firstArg match {
        case "salt" => println("pepper")
        case "chips" => println("salsa")
        case "eggs" => println("bacon")
        case _ => println("huh?")
}

Functional
val firstArg = if (!args.isEmpty) args(0) else ""
val friend =
    firstArg match {
        case "salt" => "pepper"
        case "chips" => "salsa"
        case "eggs" => "bacon"
        case _ => "huh?"
    }
println(friend)



```
###### Used in:

1. value match 
2. assignments
3. for loop 
4. Exceptions catching

###### Working on:
Types
BuiltIn
Case classes.



Example of expression

```Scala

abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String,
left: Expr, right: Expr) extends Expr

val op = BinOp("+", Number(1), v)


```

Talk about case class copy

Simplify

````Scala

def simplifyTop(expr: Expr): Expr = expr match {
case UnOp("-", UnOp("-", e))
=> e
    // Double negation
    case BinOp("+", e, Number(0)) => e // Adding zero
    case BinOp("*", e, Number(1)) => e
    case _ => expr // Multiplying by one
}

```

###### Kinds of patterns

* Wildcard patterns
* Constant patterns
* Variable patterns, Variable or constant? "import math.{E, Pi}"
* Constructor patterns
* Sequence patterns // List(0, _, _), List(0, _*)
* Tuple patterns
* Typed patterns // expr.isInstanceOf[String] , expr.asInstanceOf[String]
* Variable binding // case UnOp("abs", e @ UnOp("abs", _)) => e
* Pattern guards
    
Pattern overlaps and Sealed classes
    
Implement `def describe(e: Expr): String`


### Work Work

Implement the function `def simplify(expr: Expr): Expr`


    
    







  





