package com.coinkeeper

class Main

fun main(args: Array<String>) {
    val openCloseCharsHandler: OpenCloseCharsHandler = OpenCloseCharsHandlerImpl(
        openingAndClosingCharsMap = mapOf('(' to ')', '{' to '}', '[' to ']')
    )


    with(args[0]) {
        val inputString = this

        runCatching { openCloseCharsHandler.validate(inputString) }
            .onFailure { ex ->
                throw RuntimeException("Что-то пошло не так: ${ex.message}")
            }
            .onSuccess {
                openCloseCharsHandler.getAllCoordinates(inputString)
                    .forEach { (key, value) -> println("Пара скобок = $key, их индексы по-парно: $value") }
            }


    }

}