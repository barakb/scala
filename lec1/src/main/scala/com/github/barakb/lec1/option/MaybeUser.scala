package com.github.barakb.lec1.option

/**
  * Created by Barak Bar Orion
  * on 7/16/17.
  */
case class UserWithOneChild(child: Option[UserWithOneChild])


object UserWithOneChildApp extends App {
  val userWithOneChild =
    UserWithOneChild(Some(UserWithOneChild
    (Some(UserWithOneChild
    (Some(UserWithOneChild(None)))))))

  def grandChild(userWithOneChild: UserWithOneChild): Option[UserWithOneChild] =
    userWithOneChild.child.flatMap(_.child)

  grandChild(userWithOneChild) match{
    case None => println("No  grand child")
    case Some(gc) => println("found grand child " + gc)
  }
  grandChild(userWithOneChild).map(gc => println("found one gc " + gc) )


}



