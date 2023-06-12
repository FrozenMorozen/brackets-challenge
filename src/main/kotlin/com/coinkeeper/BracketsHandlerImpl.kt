package com.coinkeeper

import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

private val OPENING_AND_CLOSING_BRACKETS_MAP = mapOf('(' to ')', '{' to '}', '[' to ']')
private val OPENING_BRACKETS = OPENING_AND_CLOSING_BRACKETS_MAP.keys
private val CLOSING_BRACKETS = OPENING_AND_CLOSING_BRACKETS_MAP.values


class BracketsHandlerImpl() : BracketsHandler {

    @Throws(IllegalArgumentException::class)
    override fun validate(inputString: String) {
        val validString = mutableListOf<Char>()

        inputString.withIndex().forEach { (index, char) ->
            if (char in OPENING_AND_CLOSING_BRACKETS_MAP.keys) {
                validString.add(char)
            } else if (char in CLOSING_BRACKETS) {
                if (validString.isEmpty() || !isMatchingBracket(validString.removeAt(validString.lastIndex), char)) {
                    throw IllegalArgumentException("Невалидная строка. Найдена неверная скобка: $index: '$char' в заданной строке '$inputString'")
                }
            }
        }
        if (validString.isNotEmpty()) {
            val index = inputString.indexOf(validString.last())
            throw IllegalArgumentException("Невалидная строка. Не хватает закрывающей скобки для открывающей скобки: $index в заданной строке '$inputString'")
        }
    }

    override fun getAllCoordinates(inputString: String): Map<String, List<Pair<Int, Int>>> {
        val bracketIndexList = mutableListOf<Pair<Char, Int>>()
        val resultCoordinates = mutableMapOf<String, MutableList<Pair<Int, Int>>>()

        inputString.withIndex().forEach { (index, char) ->
            if (char in OPENING_BRACKETS) {
                bracketIndexList.add(Pair(char, index))
            } else if (char in CLOSING_BRACKETS) {
                if (bracketIndexList.isNotEmpty() && isMatchingBracket(bracketIndexList.last().first, char)) {
                    val openingBracket = bracketIndexList.removeAt(bracketIndexList.lastIndex)
                    val openingIndex = openingBracket.second
                    val bracketPair = "${openingBracket.first}${char}"
                    if (bracketPair !in resultCoordinates) {
                        resultCoordinates[bracketPair] = mutableListOf()
                    }
                    resultCoordinates[bracketPair]?.add(Pair(openingIndex, index))
                }
            }
        }

        return resultCoordinates
    }


    private fun isMatchingBracket(opening: Char, closing: Char): Boolean =
        OPENING_AND_CLOSING_BRACKETS_MAP[opening] == closing
}