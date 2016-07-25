import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Barak Bar Orion
  * on 7/24/16.
  *
  * @since 12.0
  */
class Lec3Spec extends FlatSpec with Matchers {
  import Lec3._

  "Map on None" should "return None" in {
    None map(a => a) shouldBe None
  }

  "Map +1 on Some(1)" should "return Some(2)" in {
    Some(1) map {_ + 1} shouldBe Some(2)
  }

  "GetOrElse on None" should "return returns else" in {
    None getOrElse (1, 2) shouldBe  (1, 2)
//    None getOrElse {println("foo"); (1, 2)} shouldBe  (1, 2)
  }
  "GetOrElse on Some" should "return returns get" in {
    Some("String") getOrElse {println("foo"); (1, 2)}  shouldBe  "String"
  }

}
