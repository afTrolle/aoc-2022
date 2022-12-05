package day5

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day5Test {

    @Test
    fun parse() {
        val day = Day5("Day05_sample")
        val (state, actions) = day.inputParsed

//            [D]
//        [N] [C]
//        [Z] [M] [P]
//         1   2   3
        val initialSolution: List<MutableList<Char>> = listOf(
            ArrayDeque(mutableListOf('N', 'Z')),
            ArrayDeque(mutableListOf('D', 'C', 'M')),
            ArrayDeque(mutableListOf('P'))
        )
        assertEquals(initialSolution, state)

//        move 1 from 2 to 1
//        move 3 from 1 to 3
//        move 2 from 2 to 1
//        move 1 from 1 to 2
        val expectedActions = listOf(
            Day5.Action(2, 1, 1),
            Day5.Action(1, 3, 3),
            Day5.Action(2, 1, 2),
            Day5.Action(1, 2, 1),
        )
        assertEquals(expectedActions, actions)
    }

    @Test
    fun solve() {
        val day = Day5("Day05_sample")
        val solution = day.part1(day.inputParsed)
        assertEquals("CMZ", solution)
    }

    @Test
    fun solve2() {
        val day = Day5("Day05_sample")
        val solution = day.part2(day.inputParsed)
        assertEquals("MCD", solution)
    }
}