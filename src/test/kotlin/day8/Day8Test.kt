package day8

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day8Test {

    @Test
    fun part1() {
        val day = Day8("Day08_sample")

//        val visibleFromEdge = day.startMatrix(day.parseInput()).sumOf { it.sumOf { if (it) 1L else 0L } }.toInt()
//        assertEquals(16 , visibleFromEdge)

        val result = day.part1(day.parseInput())
        assertEquals(21, result)
    }

    @Test
    fun part2() {
        val day = Day8("Day08_sample")
        val result = day.part2(day.parseInput())
        assertEquals(8, result)
    }

}