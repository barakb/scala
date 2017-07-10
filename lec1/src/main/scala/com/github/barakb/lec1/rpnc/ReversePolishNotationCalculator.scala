package com.github.barakb.lec1.rpnc

/**
  * Created by Barak Bar Orion
  * on 7/9/17.
  * https://en.wikipedia.org/wiki/Reverse_Polish_notation
  *
  */
object ReversePolishNotationCalculator extends App {

  /*
The infix expression "5 + ((1 + 2) × 4) − 3" can be written down like this in RPN:
5 1 2 + 4 * + 3 −
The expression is evaluated left-to-right, with the inputs interpreted as shown in the following table (the Stack is the list of values the algorithm is "keeping track of" after the Operation given in the middle column has taken place):
Input	Action	Stack	Notes
5	Operand	5	Push onto stack.
1	Operand	1 5	Push onto stack.
2	Operand	2 1 5	Push onto stack.
+	Operator	3 5	Pop the two operands (1, 2), calculate (1 + 2 = 3) and push onto stack.
4	Operand	4 3 5	Push onto stack.
×	Operator	12 5	Pop the two operands (3, 4), calculate (3 * 4 = 12) and push onto stack.
+	Operator	17	Pop the two operands (5, 12), calculate (5 + 12 = 17) and push onto stack.
3	Operand	3 17	Push onto stack.
−	Operator	14	Pop the two operands (17, 3), calculate (17 − 3 = 14) and push onto stack.
Result	14
   */
  def solveRPN(s: String): Double = {
    s.split("\\s+").toList.foldLeft(Nil: List[Double])(foldingFunction).head
  }

  def foldingFunction(stack: List[Double], exp: String): List[Double] = (stack, exp) match {
    case (x :: y :: ys, "*") => (x * y) :: ys // execute *
    case (x :: y :: ys, "+") => (x + y) :: ys // execute +
    case (x :: y :: ys, "-") => (x - y) :: ys // execute -
    case (xs, n) => n.toDouble :: xs // push number onto the stack
  }

  val s = solveRPN("5 1 2 + 4 * + 3 -")
  println(s"s is: $s")


  //  HW add the following pos

  //  sum that sum all elements on the stack
  //  "sum 10 20 30" => "60.0"

  //  max that return the max element on the stack
  //  "max 10 20 30" => "30.0"

  //  negate that change the sign of the top of the stack
  //  "negate 10 20 30 " => "-10 20 30"

}
