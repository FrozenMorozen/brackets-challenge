package com.coinkeeper

import org.junit.jupiter.params.provider.Arguments

internal val classicBracketsMap = mapOf('(' to ')', '{' to '}', '[' to ']')
internal val customCharsMap = mapOf('<' to '>', 'O' to 'P')

class TestDataGenerator {

    companion object {

        @JvmStatic
        fun provideOpeningAndClosingCharsMapAndCorrectInputData(): List<Arguments> = listOf(
            Arguments.of(
                classicBracketsMap,
                "{([])}",
                mapOf(
                    "()" to arrayListOf(Pair(1, 4)),
                    "{}" to arrayListOf(Pair(0, 5)),
                    "[]" to arrayListOf(Pair(2, 3))
                )
            ),
            Arguments.of(
                customCharsMap,
                "<O<O<OP>P>P>",
                mapOf(
                    "OP" to arrayListOf(Pair(1, 10), Pair(3, 8), Pair(5, 6)),
                    "<>" to arrayListOf(Pair(0, 11), Pair(2, 9), Pair(4, 7))
                )
            ),
        )

        @JvmStatic
        fun provideOpeningAndClosingCharsMapAndIncorrectInputData(): List<Arguments> = listOf(
            Arguments.of(classicBracketsMap, "{([(]))}"),
            Arguments.of(customCharsMap, "<O<<<O<OP>P>P>"),
        )

    }
}