package com.gigaspaces

import com.gigaspaces.FloydWarshall.Weight

import scala.collection.immutable


/**
  * Created by Barak Bar Orion
  * on 7/23/17.
  */

object FloydWarshall extends App {

  type Vertex = Int
  type Weight = Int
  type Graph = Map[Int, Map[Int, Weight]]

  def weight(graph: Graph, i: Vertex, j: Vertex): Option[Weight] = for {
    jmap <- graph.get(i)
    weight <- jmap.get(j)
  } yield weight

  def shortestPaths(vs: List[Vertex], graph: Graph): Graph = {

    def update(graph: Graph, k: Vertex): Graph = {

      def shortmap(i: Vertex, weights: Map[Int, Weight]): Map[Int, Weight] = {

        def shortest(j: Vertex, m: Map[Int, Weight]): Map[Int, Weight] = {
          val old = m.get(j)
          val n = for {
            w1 <- weight(graph, i, k)
            w2 <- weight(graph, k, j)
          } yield w1 + w2

          (old, n) match {
            case (None, None) => m
            case (None, Some(w)) => m + (j -> w)
            case (Some(w), None) => m + (j -> w)
            case (Some(w1), Some(w2)) => m + (j -> Math.min(w1, w2))
          }
        }

        vs.foldRight(Map.empty[Int, Weight])(shortest)
      }

      graph.transform(shortmap)
    }

    vs.foldLeft(graph)(update)
  }


  def mkGraph(xss: List[List[Int]]): Graph = {
    def row(i: Int, xs: List[Int]): (Int, Map[Int, Weight]) = (i, xs.zipWithIndex.map(t => (t._2, t._1)).toMap)

    xss.zipWithIndex.map(t => row(t._2, t._1)).toMap
  }

  //  test  = [[  0, 999, 999,  13, 999, 999],
  //  [999,   0, 999, 999,   4,   9],
  //  [ 11, 999,   0, 999, 999, 999],
  //  [999,   3, 999,   0, 999,   7],
  //  [ 15,   5, 999,   1,   0, 999],
  //  [ 11, 999, 999,  14, 999,   0]]

  val test = List(
    List(0, 999, 999, 13, 999, 999),
    List(999, 0, 999, 999, 4, 9),
    List(11, 999, 0, 999, 999, 999),
    List(999, 3, 999, 0, 999, 7),
    List(15, 5, 999, 1, 0, 999),
    List(11, 999, 999, 14, 999, 0)
  )

  println(mkGraph(test))
  println(shortestPaths((0 to 5).toList, mkGraph(test)))
}
