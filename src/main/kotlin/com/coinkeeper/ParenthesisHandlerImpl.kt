package com.coinkeeper

import java.lang.IllegalArgumentException

private val OPENING_BRACKETS = setOf('(', '{', '[')
private val CLOSING_BRACKETS = setOf(')', '}', ']')


class ParenthesisHandlerImpl(

) : ParenthesisHandler {

    override fun validate(inputString: String) {
        val validString = mutableListOf<Char>()

        inputString.withIndex().forEach { (index, char) ->
            if (char in OPENING_BRACKETS) {
                validString.add(char)
            } else if (char in CLOSING_BRACKETS) {
                if (validString.isEmpty() || !isMatchingBracket(validString.removeAt(validString.lastIndex), char)) {
                    throw IllegalArgumentException("Невалидная строка. Найдена неверная скобка: $index: $char")
                }
            }
        }
        if (validString.isNotEmpty()) {
            val index = inputString.indexOf(validString.last())
            throw IllegalArgumentException("Невалидная строка. Не хватает закрывающей скобки для открывающей скобки: $index")
        }
    }

    override fun getAllCoordinates(inputString: String): Map<String, List<Pair<Int, Int>>> {
        val stack = mutableListOf<Pair<Char, Int>>()
        val coordinates = mutableMapOf<String, MutableList<Pair<Int, Int>>>()

        for ((index, char) in inputString.withIndex()) {
            if (char in OPENING_BRACKETS) {
                stack.add(Pair(char, index))
            } else if (char in CLOSING_BRACKETS) {
                if (stack.isNotEmpty() && isMatchingBracket(stack.last().first, char)) {
                    val openingBracket = stack.removeAt(stack.lastIndex)
                    val openingIndex = openingBracket.second
                    val closingIndex = index
                    val bracketPair = "${openingBracket.first}${char}"
                    if (bracketPair !in coordinates) {
                        coordinates[bracketPair] = mutableListOf()
                    }
                    coordinates[bracketPair]?.add(Pair(openingIndex, closingIndex))
                }
            }
        }

        return coordinates
    }


    private fun isMatchingBracket(opening: Char, closing: Char): Boolean {
        return when (opening) {
            '(' -> closing == ')'
            '{' -> closing == '}'
            '[' -> closing == ']'
            else -> false
        }
    }
}