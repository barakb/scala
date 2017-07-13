package com.github.barakb.lec1.rat

/**
  * Created by Barak Bar Orion
  * on 7/6/17.
  */
object Sort extends App {

  // Tuple and List
  // number of param list
  // currying
  // pattern matching
  // case classes
  // apply, pattern match, equals, hashcode.

  def quickSortInts(ints: List[Int]): List[Int] = ints match {
    case Nil => Nil
    case x :: xs =>
      val smallerSorted = quickSortInts(for (s <- xs; if s < x) yield s)
      val biggerSorted = quickSortInts(for (b <- xs; if x <= b) yield b)
      smallerSorted ++ List(x) ++ biggerSorted
  }

  def quickSort[A](values: List[A])(isBigger: (A, A) => Boolean): List[A] = values match {
    case Nil => Nil
    case (x :: xs) =>
      val smallerSorted = quickSort(for (s <- xs; if !isBigger(s, x)) yield s)(isBigger)
      val biggerSorted = quickSort(for (b <- xs; if isBigger(b, x)) yield b)(isBigger)
      smallerSorted ++ List(x) ++ biggerSorted
  }

  println(quickSortInts(List(3, 1, 4)))
  println(quickSort(List(3, 1, 4))(_ > _))

  case class Person(age:Int, name:String)

  val f = Person(16, "Barak") match {
    case Person(17, "Barak") => "Barak young"
    case name@Person(18, "Barak") => "Barak" + " full record was " + name + Person(18, "Barak")
    case x@_ => "I don't know " + x

  }

  println(f)

  val persons = List(Person(100, "Bush"), Person(16,"Barak"), Person(17, "Obamma"))

  // example for Person sort by age
  println("sort by age: " + quickSort(persons)(_.age > _.age))
  // example for Person sort by name
  println("sort by Name: " + quickSort(persons)(_.name > _.name))


  // currying

  val sortPersons = quickSort(persons) _
  println("sort by age with currying: " + sortPersons(_.age > _.age))
  // example for Person sort by name
  println("sort by name with currying: " + sortPersons(_.name > _.name))

}

/*
  C++
template <typename T>
void qsort (T *result, T *list, int n)
{
    if (n == 0) return;
    T *smallerList, *largerList;
    smallerList = new T[n];
    largerList = new T[n];
    T pivot = list[0];
    int numSmaller=0, numLarger=0;
    for (int i = 1; i < n; i++)
        if (list[i] < pivot)
            smallerList[numSmaller++] = list[i];
        else
            largerList[numLarger++] = list[i];

    qsort(smallerList,smallerList,numSmaller);
    qsort(largerList,largerList,numLarger);

    int pos = 0;
    for ( int i = 0; i < numSmaller; i++)
        result[pos++] = smallerList[i];

    result[pos++] = pivot;

    for ( int i = 0; i < numLarger; i++)
        result[pos++] = largerList[i];

    delete [] smallerList;
    delete [] largerList;
};

Java
int partition(int arr[], int left, int right)
{
      int i = left, j = right;
      int tmp;
      int pivot = arr[(left + right) / 2];

      while (i <= j) {
            while (arr[i] < pivot)
                  i++;
            while (arr[j] > pivot)
                  j--;
            if (i <= j) {
                  tmp = arr[i];
                  arr[i] = arr[j];
                  arr[j] = tmp;
                  i++;
                  j--;
            }
      };

      return i;
}

void quickSort(int arr[], int left, int right) {
      int index = partition(arr, left, right);
      if (left < index - 1)
            quickSort(arr, left, index - 1);
      if (index < right)
            quickSort(arr, index, right);
}


 Scala

def quickSort[A](values: List[A])(isBigger: (A, A) => Boolean): List[A] = values match {
  case Nil => Nil
  case (x :: xs) =>
    val smallerSorted = quickSort(for (s <- xs; if !isBigger(s, x)) yield s)(isBigger)
    val biggerSorted = quickSort(for (b <- xs; if isBigger(b, x)) yield b)(isBigger)
    smallerSorted ++ List(x) ++ biggerSorted
}

Haskell

 qsort :: (Ord a) => [a] -> [a]
 qsort []     = []
 qsort (x:xs) = qsort less ++ [x] ++ qsort more
     where less = filter (<x)  xs
           more = filter (>=x) xs


 */
