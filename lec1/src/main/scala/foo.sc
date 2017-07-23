def head[A](l: List[A]) : A = l match {
  case h::_ => h
}

head(List(1, 2, 3))

List(1, 3, 3).reverse

List(1, 2) ++ List(3, 4)
val l1 = List('a', 'b')
val l2 = 'c' :: l1

List(1, 2).foldLeft("")(_ + _)

List(1, 2).foldRight("")((i:Int, s:String) => i + s)
//[1, 2] "" (Int, String) => String
      "2"