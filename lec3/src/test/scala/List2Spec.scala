import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Barak Bar Orion
  * on 7/24/16.
  *
  * @since 12.0
  */
class List2Spec extends FlatSpec with Matchers {
  import List2._

  "A list " can "be created with cons and nil " in {
    (1 :: Nil) shouldBe a [List[_]]
  }
  "An empty list" should "be  nil " in {
    List() shouldBe Nil
  }
  "A list" can "be create either way " in {
    List(1) shouldBe 1 :: Nil
  }

  "A list" can "be matched using ::" in {
    (List(1) match { case _ :: Nil => true; case _ => false}) shouldBe true
  }

  "A list" can "be matched using List()" in {
    (1 :: Nil match { case List(_) => true; case _ => false}) shouldBe true
  }


}
