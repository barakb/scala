/**
  * Created by Barak Bar Orion
  * on 7/25/16.
  *
  * @since 12.0
  */
object Hw4 {

  sealed trait Either[+E, +A] {
  }

  case class Left[+E](value: E) extends Either[E, Nothing]

  case class Right[+A](value: A) extends Either[Nothing, A]


  // example of usage:

  def main(args: Array[String]): Unit = {

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
  }
}
