import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Barak Bar Orion
  * on 7/24/16.
  *
  * @since 12.0
  */
class List1Spec extends FlatSpec with Matchers {
//  import List1._
  import Lec3._

  "A list " can "be created with cons and nil " in {
    Cons(1, Nil) shouldBe a [List[_]]
  }
  "An empty list" should "be  nil " in {
    List() shouldBe Nil
  }
  "A list" can "be create either way " in {
    List(1) shouldBe  Cons(1, Nil)
  }
  "A list" can "be matched using ::" in {
    (List(1) match { case Cons(_, Nil) => true; case _ => false}) shouldBe true
  }
}
