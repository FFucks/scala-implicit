object Implicit {
    given greeting: String = "Hello, Scala!"

    def sayHello()(using msg: String): Unit =
        println(msg)

    @main def main(): Unit =
        sayHello()
}
