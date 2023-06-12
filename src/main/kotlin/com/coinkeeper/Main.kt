package com.coinkeeper

class Main(
    private val parenthesisHandlerImpl: ParenthesisHandlerImpl
)

fun main(args: Array<String>) {
    // Это должен был быть инжект без явного указания имплементации,
    // но я не успел завести какой-нибудь IOC контейнер
    val parenthesisHandler: ParenthesisHandler = ParenthesisHandlerImpl()

    with(args[0]) {
        val inputString = this

        runCatching { parenthesisHandler.validate(inputString) }
            .onFailure { ex -> println("Что-то пошло не так: ${ex.message}") }
            .onSuccess { println(parenthesisHandler.getAllCoordinates(inputString)) }
    }

}