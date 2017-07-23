package com.gigaspaces

import scala.io.Source

/**
  * Created by Barak Bar Orion
  * on 7/23/17.
  */

sealed case class Point(x: Double, y: Double) {
  def sqDistance(p: Point): Double = math.pow(x - p.x, 2) + math.pow(y - p.y, 2)
}

sealed case class PointSum(var size: Int, var x: Double, var y: Double) {

  def add(p: Point): Unit = {
    size += 1
    x += p.x
    y += p.y
    ()
  }

  def toCluster(clusterId: Int): Cluster = Cluster(clusterId, Point(x / size, y / size))

}

sealed case class Cluster(clusterId: Int, center: Point)

object Kmeans extends App {


  def addPoint(p1: Point, p2: Point): Point = Point(p1.x + p2.x, p1.y + p2.y)


  def makeCluster(clusterId: Int, points: List[Point]): Cluster = {
    val pointSum@Point(a, b) = points.foldLeft(Point(0, 0))(addPoint)
    val count = points.length
    Cluster(clusterId, Point(a / count, b / count))
  }

  def decodePoint(line: String): Point = {
    val List(s1, s2) = line.split(" +").toList
    Point(s1.toDouble, s2.toDouble)
  }

  def readPoints(): List[Point] = {
    val bufferedSource = Source.fromFile("points")
    val res = bufferedSource.getLines.toList.map(decodePoint)
    bufferedSource.close
    res
  }

  def decodeCluster(line: String): Cluster = {
    val List(s1, s2, s3) = line.split(" +").toList
    Cluster(s1.toInt, Point(s2.toDouble, s3.toDouble))
  }

  def readClusters(): List[Cluster] = {
    val bufferedSource = Source.fromFile("clusters")
    val res = bufferedSource.getLines.toList.map(decodeCluster)
    bufferedSource.close
    res
  }

  println("read " + readPoints().length + " points")
  println("read clusters: " + readClusters())

  /**
    * divides the points into new sets by finding the Cluster to which each Point is closest. However, instead of collecting sets of Points, we build up a PointSum for each cluster.
    * This is an optimization that avoids constructing the intermediate data structure and allows the algorithm to run in constant space.
    * We’ll represent the output of this step as Vector PointSum
    */
  def assign(clusters: List[Cluster], points: List[Point]): Vector[PointSum] = {
    val vec = clusters.map(_ => PointSum(0, 0, 0)).toVector

    // add point to its nearest cluster point
    def addPoint(p: Point) = {
      val c = nearest(p)
      val cid = c.clusterId
      vec(cid).add(p)
    }

    // find the cluster nearest this point
    def nearest(p: Point): Cluster = {
      val clustersWithDistance = for (c <- clusters) yield (c, p.sqDistance(c.center))
      val min = clustersWithDistance.minBy(_._2)
      min._1
    }

    points.foreach(addPoint)
    vec
  }

  /**
    * @param vec of Point Sum
    * @return List of new clusters that are represented by those nonempty PointSum.
    */
  def makeNewClusters(vec: Vector[PointSum]): List[Cluster] = {
    for ((ps@PointSum(count, _, _), i) <- vec.toList.zipWithIndex if 0 < count) yield ps.toCluster(i)
  }

  /**
    * Perform one step in the kmean algorithm.
    */
  def step(clusters: List[Cluster], points:List[Point]) : List[Cluster] = makeNewClusters(assign(clusters, points))

  /**
    * Perform step up to 80 times
    */
  def kmean(clusters: List[Cluster], points:List[Point]) : Option[List[Cluster]] = {
    val tooMany = 80

    def loop(n:Int, clusters: List[Cluster]) : Option[List[Cluster]] = {
      if (tooMany < n) None
      else{
        println(s"Iteration $n " + clusters)
        val newClusters = step(clusters, points)
        if (newClusters == clusters) Some(clusters) else loop(n + 1, newClusters)
      }
    }

    loop(0, clusters)
  }

  val res = kmean(readClusters(), readPoints())
  println(s"res = $res")
}
