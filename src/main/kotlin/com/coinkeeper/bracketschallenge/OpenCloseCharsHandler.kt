package com.coinkeeper.bracketschallenge

import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

interface OpenCloseCharsHandler {

    @Throws(IllegalArgumentException::class)
    fun validate(inputString: String)

    @Deprecated("Have to use getAllCoordinatesList")
    fun getAllCoordinatesMap(inputString: String): Map<String, List<Pair<Int, Int>>>

    fun getAllCoordinatesList(inputString: String): List<CharsCoordinates>
}