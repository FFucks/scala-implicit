trait Show[T]:
    def show(t: T): String

object Show:
    def apply[T](using s: Show[T]): Show[T] = s

    extension [T](t: T)(using s: Show[T])
        def show: String = s.show(t)

    given Show[String] with
        def show(s: String): String = s

    given Show[Int] with
        def show(i: Int): String = i.toString

    given [T](using ev: Show[T]): Show[Option[T]] with
        def show(opt: Option[T]): String =
            opt match
                case Some(v) => s"Some(${ev.show(v)})"
                case None    => "None"

    given [T](using ev: Show[T]): Show[List[T]] with
        def show(xs: List[T]): String =
            xs.map(ev.show).mkString("List(", ", ", ")")

import Show.*

case class Person(name: String, age: Int)

given Show[Person] with
    def show(p: Person): String =
        s"Person(name=${p.name}, age=${p.age})"

def printShown[T](value: T)(using s: Show[T]): Unit =
    println(s.show(value))

def printShown2[T: Show](value: T): Unit =
    println(summon[Show[T]].show(value))

@main def demoShow(): Unit =
    val p = Person("Fábio", 34)
    val nums = List(1, 2, 3)
    val maybe = Option(42)

    println(p.show)
    println(nums.show)
    println(maybe.show)

    printShown(p)
    printShown2(nums)
