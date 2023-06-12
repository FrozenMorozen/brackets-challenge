package com.coinkeeper

class Main

fun main(args: Array<String>) {
    // Это должен был быть инжект без явного указания имплементации,
    // но я не успел завезти какой-нибудь IOC контейнер
    val bracketsHandler: BracketsHandler = BracketsHandlerImpl()

    with(args[0]) {
        val inputString = this

        runCatching { bracketsHandler.validate(inputString) }
            .onFailure { ex ->
                throw RuntimeException("Что-то пошло не так: ${ex.message}")
            }
            .onSuccess {
                bracketsHandler.getAllCoordinates(inputString)
                    .forEach { (key, value) -> println("Пара скобок = $key, их индексы по-парно = $value") }
            }


    }

}