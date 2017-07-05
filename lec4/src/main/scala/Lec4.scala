/**
  * Created by Barak Bar Orion
  * on 7/25/16.
  *
  * @since 12.0
  */
object Lec4 {

  def sequence[A](a: List[Option[A]]): Option[List[A]] = a match {
    case Nil => None
    case h :: tl => h.flatMap(hv => sequence(tl).map(tlv => hv :: tlv))
  }

    def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = a match{
      case Nil => None
      case h :: tl => f(h).flatMap(hv => traverse(tl)(f).map(tlv => hv :: tlv))
    }
}
