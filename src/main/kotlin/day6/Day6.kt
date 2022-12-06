package day6

import Day

fun main() {
    Day6("Day06").solve()
}

class Day6(input: String) : Day<String>(input) {

    override fun parse(): String = input

    private fun String.startMessageIndex(size: Int): Int =
        windowed(size) { it.toSet() }.indexOfFirst { it.size == size } + size

    private fun String.startMessageIndexLinear(uniqueLength: Int): Int {
        val duplicateIndexMap = mutableMapOf<Char, Int>()
        var mostRecentDuplicateIndex = 0
        var index = 0
        return indexOfFirst { char ->
            val lastSeen = duplicateIndexMap.put(char, index) ?: 0
            mostRecentDuplicateIndex = mostRecentDuplicateIndex.coerceAtLeast(lastSeen)
            index++ - mostRecentDuplicateIndex >= uniqueLength
        } + 1
    }

    override fun part1(input: String): Any = input.startMessageIndexLinear(4)
    override fun part2(input: String): Any = input.startMessageIndex(14)
}
