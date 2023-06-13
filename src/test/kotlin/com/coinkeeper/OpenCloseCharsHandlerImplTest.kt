package com.coinkeeper

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class OpenCloseCharsHandlerImplTest {


    @ParameterizedTest
    @MethodSource("com.coinkeeper.TestDataGenerator#provideOpeningAndClosingCharsMapAndCorrectInputData")
    fun check_getAllCoordinates_withCorrectData(
        openingAndClosingCharsMap: Map<Char, Char>,
        inputData: String,
        expectedResult: Map<String, List<Pair<Int, Int>>>
    ) {
        val openCloseCharsHandler: OpenCloseCharsHandler = OpenCloseCharsHandlerImpl(
            openingAndClosingCharsMap = openingAndClosingCharsMap
        )

        val actualResult = openCloseCharsHandler.getAllCoordinates(inputData)
        expectedResult.forEach { (key, value) ->
            assertTrue(expectedResult[key]!!.containsAll(actualResult[key]!!))
        }
    }

    @ParameterizedTest
    @MethodSource("com.coinkeeper.TestDataGenerator#provideOpeningAndClosingCharsMapAndCorrectInputData")
    fun check_validate_withCorrectData(
        openingAndClosingCharsMap: Map<Char, Char>,
        inputData: String
    ) {
        val openCloseCharsHandler: OpenCloseCharsHandler = OpenCloseCharsHandlerImpl(
            openingAndClosingCharsMap = openingAndClosingCharsMap
        )

        assertDoesNotThrow { openCloseCharsHandler.validate(inputData) }
    }

    @ParameterizedTest
    @MethodSource("com.coinkeeper.TestDataGenerator#provideOpeningAndClosingCharsMapAndIncorrectInputData")
    fun check_validate_withIncorrectData(
        openingAndClosingCharsMap: Map<Char, Char>,
        inputData: String
    ) {
        val openCloseCharsHandler: OpenCloseCharsHandler = OpenCloseCharsHandlerImpl(
            openingAndClosingCharsMap = openingAndClosingCharsMap
        )

        assertThrows<IllegalArgumentException> { openCloseCharsHandler.validate(inputData) }
    }

}