package com.coinkeeper.bracketschallenge

import java.lang.IllegalArgumentException
import kotlin.jvm.Throws


class OpenCloseCharsHandlerImpl(
    private val openingAndClosingCharsMap: Map<Char, Char>
) : OpenCloseCharsHandler {

    private val openingCharsList = openingAndClosingCharsMap.keys
    private val closingCharsList = openingAndClosingCharsMap.values

    @Throws(IllegalArgumentException::class)
    override fun validate(inputString: String) {
        val validString = mutableListOf<Char>()

        inputString.withIndex().forEach { (index, char) ->
            if (char in openingCharsList) {
                validString.add(char)
            } else if (char in closingCharsList) {
                if (validString.isEmpty() || !isMatchingChars(validString.removeAt(validString.lastIndex), char)) {
                    throw IllegalArgumentException("Невалидная строка. Найден некорректный символ: '$char' на позиции $index в строке '$inputString'")
                }
            }
        }
        if (validString.isNotEmpty()) {
            val index = inputString.indexOf(validString.last())
            throw IllegalArgumentException("Невалидная строка. Не хватает закрывающего символа для открывающего символа на позиции $index в строке '$inputString'")
        }
    }

    @Deprecated("Have to use getAllCoordinatesList")
    override fun getAllCoordinatesMap(inputString: String): Map<String, List<Pair<Int, Int>>> {
        val openingCharsAndIndexList = mutableListOf<Pair<Char, Int>>()
        val resultCoordinates = mutableMapOf<String, MutableList<Pair<Int, Int>>>()

        inputString.withIndex().forEach { (index, char) ->
            if (char in openingCharsList) {
                openingCharsAndIndexList.add(Pair(char, index))
            } else if (char in closingCharsList) {

                if (openingCharsAndIndexList.isNotEmpty() && isMatchingChars(openingCharsAndIndexList.last().first, char)) {
                    val openingChar = openingCharsAndIndexList.removeAt(openingCharsAndIndexList.lastIndex)
                    val openingCharIndex = openingChar.second
                    val openingAndClosingCharsAsString = "${openingChar.first}${char}"
                    if (openingAndClosingCharsAsString !in resultCoordinates) {
                        resultCoordinates[openingAndClosingCharsAsString] = mutableListOf()
                    }
                    resultCoordinates[openingAndClosingCharsAsString]?.add(Pair(openingCharIndex, index))
                }
            }
        }

        return resultCoordinates
    }

    override fun getAllCoordinatesList(inputString: String): List<CharsCoordinates> {
        val openingCharsAndIndexList = mutableListOf<Pair<Char, Int>>()
        val resultCoordinates = mutableListOf<CharsCoordinates>()

        inputString.withIndex().forEach { (index, char) ->
            if (char in openingCharsList) {
                openingCharsAndIndexList.add(Pair(char, index))
            } else if (char in closingCharsList) {

                if (openingCharsAndIndexList.isNotEmpty() && isMatchingChars(openingCharsAndIndexList.last().first, char)) {
                    val openingChar: Pair<Char, Int> =
                        openingCharsAndIndexList.removeAt(openingCharsAndIndexList.lastIndex)
                    val openingCharIndex: Int = openingChar.second
                    val openingAndClosingCharsAsString = "${openingChar.first}${char}"
                    if (openingAndClosingCharsAsString !in resultCoordinates.map { it.openingClosingChars }) {
                        resultCoordinates.remove(resultCoordinates.find { it.openingClosingChars == openingAndClosingCharsAsString })
                    }
                    resultCoordinates.add(CharsCoordinates(openingAndClosingCharsAsString, openingCharIndex, index))
                }
            }
        }

        return resultCoordinates
    }


    private fun isMatchingChars(opening: Char, closing: Char): Boolean =
        openingAndClosingCharsMap[opening] == closing
}