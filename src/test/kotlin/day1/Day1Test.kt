package day1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day1Test {
    @Test
    fun parseInput() {
        val day = Day1("Day01_sample.txt")
        val result = day.inputParsed

        val firstElf = result.first()
        assertEquals(6000, firstElf.sum())
        assertEquals(listOf(1000, 2000, 3000), firstElf)

        val forthElf = result[3]
        assertEquals(24000, forthElf.sum())
    }
}