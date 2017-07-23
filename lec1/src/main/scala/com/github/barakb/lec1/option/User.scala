package com.github.barakb.lec1.option

/**
  * Created by Barak Bar Orion
  * on 7/16/17.
  */
case class User(children: List[User])


object UserApp extends App {
  val users = User(List(
    User(Nil),
    User(List(User(Nil))),
    User(List(User(Nil))),
    User(Nil)
  ))

  def grandChildren(user: User): List[User] = user.children.flatMap(child => child.children)

  println(grandChildren(users))
}