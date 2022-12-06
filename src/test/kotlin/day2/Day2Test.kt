package day2

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day2Test {

    @Test
    fun parseInput() {
        val day = Day2("Day02_sample")
        val result = day.parseInput()

        val total = result.sumOf { it.scoreByHands }
        assertEquals(15, total)
    }
}