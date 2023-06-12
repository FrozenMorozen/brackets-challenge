package com.coinkeeper

import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

interface BracketsHandler {

    @Throws(IllegalArgumentException::class)
    fun validate(inputString: String)

    fun getAllCoordinates(inputString: String): Map<String, List<Pair<Int, Int>>>
}