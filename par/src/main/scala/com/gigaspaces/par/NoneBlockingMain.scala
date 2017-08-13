package com.gigaspaces.par

import java.util.concurrent.Executors

import com.gigaspaces.par.noneblocking.Par.Par
import com.gigaspaces.par.noneblocking.Par.Par.ParOps


/**
  * Created by Barak Bar Orion
  * on 8/13/17.
  */
object NoneBlockingMain extends App{
  val p = Par.parMap(List.range(1, 100000))(math.sqrt(_))
  private val service = Executors.newFixedThreadPool(1)
  val x = Par.run(service)(p)
  println(x)


  def sum(ints: IndexedSeq[Int]): Par[Int] =
    if (ints.length <= 1)
      Par.unit(ints.headOption getOrElse 0)
    else {
      val (l, r) = ints.splitAt(ints.length / 2)
      Par.map2(Par.fork(sum(l)), Par.fork(sum(r)))(_ + _)
    }

  val sm = sum(1 to 100)
  println("sum: " + Par.run(service)(sm))


  val twiceSm = sum(1 to 100) map ( _ * 2)
  println("2 * sum: " + Par.run(service)(twiceSm))

  service.shutdown()

}
