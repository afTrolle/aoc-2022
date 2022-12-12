package day10

import Day

fun main() {
    Day10("Day10").solve()
}

class Day10(input: String) : Day<List<Int>>(input) {

    override fun parseInput(): List<Int> {
        return inputByLines.map {
            val (a, b) = (it.split(" ") + " ")
            a to b.toIntOrNull()
        }.flatMap { (action, num) ->
            when (action) {
                "noop" -> listOf(0)
                else -> listOf(0, num!!)
            }
        }
    }

    override fun part1(input: List<Int>): Any? {
        val register =
            input.runningFold(1) { acc: Int, i: Int ->
                acc + i
            }
        return listOf(20, 60, 100, 140, 180, 220).map {
            register[it - 1] * it
        }.sumOf { it }
    }

    override fun part2(input: List<Int>): String {
        val width = 40
        val spritePos: List<Pair<Int, Boolean>> =
            input.runningFoldIndexed(1 to false) { index: Int, (acc: Int, _: Boolean), i: Int ->
                val crt = index % width
                val spirePosition = acc + i
                val sprite = (acc - 1)..(acc + 1)
                spirePosition to sprite.contains(crt)
            }.drop(1)
        return "\n" + spritePos.map { it.second }.chunked(width)
            .joinToString(System.lineSeparator()) { line ->
                line.joinToString("") { if (it) "#" else "." }
            }
    }
}

