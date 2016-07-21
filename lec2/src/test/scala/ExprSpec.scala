import org.scalatest.{FlatSpec, Matchers}
import Main._

/**
  * Created by Barak Bar Orion
  * on 7/21/16.
  *
  * @since 12.0
  */
class ExprSpec extends FlatSpec with Matchers {

  "A top level double negation " should "be simplified " in {
      simplify(UnOp("-", UnOp("-", Var("x")))) should be (Var("x"))
  }

  "expr + 0" should "simplified to expr" in {
    simplify(BinOp("+", Var("x"), Number(0))) should be (Var("x"))
  }

  "0 + expr" should "simplified to expr" in {
    simplify(BinOp("+", Number(0), Number(1))) should be (Number(1))
  }

  "expr * 1" should "simplified to expr" in {
    simplify(BinOp("*", Var("x"), Number(1))) should be (Var("x"))
  }

  " 1 * expr" should "simplified to expr" in {
    simplify(BinOp("*", Number(1), Var("x"))) should be (Var("x"))
  }

  "A second level double negation" should "be simplified" in {
    simplify(BinOp("+", UnOp("-", UnOp("-", Var("x"))), UnOp("-", UnOp("-", Var("x"))))) should be (BinOp("+", Var("x"), Var("x")))
  }
  "A second level + 0 " should "be simplified" in {
    simplify(BinOp("+", BinOp("+", Number(0), Number(1)), BinOp("+", Var("x"), Number(0)))) should be (BinOp("+", Number(1), Var("x")))
  }
  "A second level * 1 " should "be simplified" in {
    simplify(BinOp("+", BinOp("*", Number(1), Number(1)), BinOp("*", Number(1), Var("x")))) should be (BinOp("+", Number(1), Var("x")))
  }
  "Multiple simplification" should "work" in {
    simplify(BinOp("+", BinOp("*", Number(1), Number(0)), BinOp("*", Number(1), Var("x")))) should be (Var("x"))
  }
}
