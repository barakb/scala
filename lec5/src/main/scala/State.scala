/**
  * Created by Barak Bar Orion
  * on 8/8/16.
  *
  * @since 12.0
  */

import State._

case class State[S, +A](run: S => (A, S)) {
  def flatMap[B](f: A => State[S, B]): State[S, B] = State(s => {
    val (a, s1) = run(s)
    f(a).run(s1)
  })

  def map[B](f: A => B): State[S, B] = flatMap(a => unit(f(a)))

  def map2[B, C](sb: State[S, B])(f: (A, B) => C): State[S, C] = flatMap(a => sb.map(b => f(a, b)))

}


object State {
  def unit[A, S](a: A): State[S, A] = State(s => (a, s))

  def sequence[A, S](l: List[State[S, A]]): State[S, List[A]] = {
    def loop(s: S, acc: List[A], l: List[State[S, A]]): (List[A], S) = l match {
      case Nil => (acc.reverse, s)
      case h :: tl => h.run(s) match {
        case (a, s1) => loop(s1, a :: acc, tl)
      }
    }
    State(s => loop(s, List(), l))
  }
}
