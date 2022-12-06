package day6

import Day

fun main() {
    Day6("Day06").apply {
        println(part1(parsedInput))
        println(part2(parsedInput))
    }
}

class Day6(input: String) : Day<String>(input) {

    override fun parseInput(): String = input

    private fun String.startMessageIndex(uniqueLength: Int): Int {
        val duplicateIndexMap = mutableMapOf<Char, Int>()
        var mostRecentDuplicateIndex = 0
        var index = 0
        return indexOfFirst { char ->
            val lastSeenIndex = duplicateIndexMap.put(char, index) ?: 0
            mostRecentDuplicateIndex = mostRecentDuplicateIndex.coerceAtLeast(lastSeenIndex)
            index++ - mostRecentDuplicateIndex >= uniqueLength
        } + 1
    }

    override fun part1(input: String): Any = input.startMessageIndex(4)
    override fun part2(input: String): Any = input.startMessageIndex(14)
}
