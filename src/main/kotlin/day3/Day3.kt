package day3

import Day

fun main() {
    Day3("Day03").solve()
}

class Day3(input: String) : Day<List<Day3.Rucksack>>(input) {

    data class Rucksack(val data: String) {
        private val compartmentSize = data.length / 2
        private val a = data.take(compartmentSize).toSet()
        private val b = data.drop(compartmentSize).toSet()
        val overlap = a.intersect(b)
        val items = data.toSet()
    }

    private fun Char.priority(): Int = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> error("invalid char")
    }

    override fun parseInput(): List<Rucksack> = inputByLines.map { Rucksack(it) }

    override fun part1(input: List<Rucksack>): Any = input.map { it.overlap }.flatten().sumOf { it.priority() }

    override fun part2(input: List<Rucksack>): Any = input.chunked(3).map { groupBags ->
        groupBags.map {
            it.items
        }.reduce { acc, chars ->
            acc.intersect(chars)
        }.first()
    }.sumOf { it.priority() }
}