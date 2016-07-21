import math.{E, Pi}

/**
  * Created by Barak Bar Orion
  * on 7/20/16.
  *
  * @since 12.0
  */


abstract class Expr

case class Var(name: String) extends Expr

case class Number(num: Double) extends Expr

case class UnOp(operator: String, arg: Expr) extends Expr

case class BinOp(operator: String,
                 left: Expr, right: Expr) extends Expr


object Main {

  def simplifyTop(expr: Expr): Expr = expr match {
    case UnOp("-", UnOp("-", e)) => e
    case BinOp("+", e, Number(0)) => e
    case BinOp("+", Number(0), e) => e
    case BinOp("*", e, Number(1)) => e
    case BinOp("*", Number(1), e) => e
    case BinOp("*", e, Number(0)) => Number(0)
    case BinOp("*", Number(0), e) => Number(0)
    case _ => expr
  }

  def simplify(expr: Expr): Expr = expr match {
    case UnOp(operator, exp) => simplifyTop(UnOp(operator, simplify(exp)))
    case BinOp(operator, exp1, exp2) => simplifyTop(BinOp(operator, simplify(exp1), simplify(exp2)))
    case _ => expr
  }
}


