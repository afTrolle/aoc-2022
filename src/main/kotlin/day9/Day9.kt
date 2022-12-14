package day9

import Day

fun main() {
    Day9("Day09").apply {
        println(part1(parsedInput))
        println(part2(parsedInput))
    }
}

data class Point(
    var height: Int = 0,
    var width: Int = 0
)

class Day9(input: String) : Day<List<Char>>(input) {


    override fun parseInput() = inputByLines.map {
        val (a, b) = it.split(" ")
        a.first() to b.toInt()
    }.flatMap { (direction, step) ->
        List(step) { direction }
    }

    fun MutableMap<Pair<Int, Int>, Boolean>.setVisited(point: Point) {
        this[point.height to point.width] = true
    }

    fun Point.isTouching(head: Point): Boolean =
        head.height in (height - 1)..(height + 1) && head.width in (width - 1)..(width + 1)

    fun Point.updateTail(head: Point) {
        if (!isTouching(head)) {
            height += (head.height - height).coerceIn(-1..1)
            width += (head.width - width).coerceIn(-1..1)
        }
    }

    fun Point.updateHead(input: List<Char>, updateTails: (head: Point) -> Unit) {
        input.forEach {
            when (it) {
                'U' -> height += 1
                'D' -> height -= 1
                'L' -> width -= 1
                'R' -> width += 1
                else -> error("invalid dir")
            }
            updateTails(this)
        }
    }

    override fun part1(input: List<Char>): Any {
        val visitedMap: MutableMap<Pair<Int, Int>, Boolean> = mutableMapOf()
        val head = Point()
        val tail = Point()
        visitedMap.setVisited(head)

        head.updateHead(input) { currentHead ->
            tail.updateTail(currentHead)
            visitedMap.setVisited(tail)
        }
        return visitedMap.count { it.value }
    }

    override fun part2(input: List<Char>): Any {
        val visitedMap: MutableMap<Pair<Int, Int>, Boolean> = mutableMapOf()
        val head = Point()
        visitedMap.setVisited(head)
        val tails = List(9) { Point() }

        head.updateHead(input) { currentHead ->
            tails.fold(currentHead) { prev, tail ->
                tail.updateTail(prev)
                tail
            }
            visitedMap.setVisited(tails.last())
        }

        return visitedMap.count { it.value }
    }
}

