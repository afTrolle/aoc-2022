package day10

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {

    @Test
    fun part2() {
        val actual = Day10("Day10_sample").run {
            part2(parsedInput).trim()
        }

        val expected =
            """##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....""".trim()

        val expectedLines = expected.lines()
        actual.lines().mapIndexed { index, s ->
            assertEquals(expectedLines[index], s)
        }
    }
}