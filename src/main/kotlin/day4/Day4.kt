package day4

import Day
import ext.includes
import ext.overlap

fun main() {
    Day4("Day04").solve()
}

class Day4(input: String) : Day<List<Pair<IntRange, IntRange>>>(input) {

    override fun parseInput(): List<Pair<IntRange, IntRange>> = inputByLines.map {
        val (a1, a2, b1, b2) = it.split(",", "-").map(String::toInt)
        a1..a2 to b1..b2
    }

    override fun part1(input: List<Pair<IntRange, IntRange>>): Any = input.count { (one, two) ->
        one.includes(two) || two.includes(one)
    }

    override fun part2(input: List<Pair<IntRange, IntRange>>): Any = input.count { (one, two) ->
        one.overlap(two) || two.overlap(one)
    }
}