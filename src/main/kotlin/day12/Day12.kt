package day12

import Day
import ext.findInMatrix
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.absoluteValue

fun main() {
    Day12("Day12").solve()
}

class Day12(input: String) : Day<List<List<Char>>>(input) {

    // TODO change to Map with nodes,
    data class Point(
        val y: Int,
        val x: Int,
        val z: Int,
    )

    fun Point.pointIsValidHeight(other: Point): Point? = takeIf { (z - other.z).absoluteValue <= 1 }

    private val range: Map<Char, Int> =
        ('a'..'z').map { it to (it - 'a') }.plus('S' to 0).plus('E' to ('z' - 'a')).toMap()
    private val start = 'S'
    private val end = 'E'

    override fun parseInput(): List<List<Char>> {
        return inputByLines.map { it.toList() }
    }

    data class Params(
        val current: Point, val end: Point, val map: List<List<Point>>, val visited: Set<Point>
    )

    fun getNextPositon(
        current: Point,
        end: Point,
        map: List<List<Point>>
    ): List<Point> {
        val down = map.getOrNull(current.y + 1)?.getOrNull(current.x)?.pointIsValidHeight(current)
        val up = map.getOrNull(current.y - 1)?.getOrNull(current.x)?.pointIsValidHeight(current)
        val left = map.getOrNull(current.y)?.getOrNull(current.x - 1)?.pointIsValidHeight(current)
        val right = map.getOrNull(current.y)?.getOrNull(current.x + 1)?.pointIsValidHeight(current)

        // c 0 0   - 0
        // 0 0 y   - 1

        // prio-direction.
        val yDiff = current.y - end.y
        val xDiff = current.x - end.x
        return buildList {
            if (xDiff.absoluteValue > yDiff.absoluteValue) {
                if (xDiff < 0) right?.let { add(it) } else left?.let { add(it) }
                if (yDiff < 0) down?.let { add(it) } else up?.let { add(it) }

                if (yDiff < 0) up?.let { add(it) } else down?.let { add(it) }
                if (xDiff < 0) left?.let { add(it) } else right?.let { add(it) }
            } else {
                if (yDiff < 0) down?.let { add(it) } else up?.let { add(it) }
                if (xDiff < 0) right?.let { add(it) } else left?.let { add(it) }

                if (xDiff < 0) left?.let { add(it) } else right?.let { add(it) }
                if (yDiff < 0) up?.let { add(it) } else down?.let { add(it) }
            }
        }
    }

    val depth = DeepRecursiveFunction<Params, Int?> { param ->
        param.run {
            val size = visited.size
            val minSteps = (current.y - end.y).absoluteValue + (current.x - end.x).absoluteValue
            if (current == end) {
                globalMin.update { currentMin ->
                    if (currentMin > size) size else currentMin
                }
                size
            } else if (size + minSteps >= globalMin.value) {
                null
            } else if (visited.contains(current)) {
                null
            } else {
                val updatedVisited = visited.plus(current)
                getNextPositon(current, end, map).mapNotNull { next ->
                    callRecursive(param.copy(current = next, visited = updatedVisited))
                }.minOrNull()
            }
        }
    }


    private val globalMin = MutableStateFlow(Int.MAX_VALUE)

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun findEnd(current: Point, end: Point, map: List<List<Point>>, visited: Set<Point>): Int? {
        val minSteps = (current.y - end.y).absoluteValue + (current.x - end.x).absoluteValue
        return if (current == end) {
            println(visited.size)
            visited.size.also { size ->
                globalMin.update { currentMin ->
                    if (currentMin > size) size else currentMin
                }
            }
        } else if (visited.size + minSteps >= globalMin.value) {
            null
        } else if (visited.contains(current)) {
            null
        } else {
            val updatedVisited = visited.plus(current)
            getNextPositon(current, end, map).map { next ->
                GlobalScope.async(Dispatchers.Default) {
                    findEnd(next, end, map, updatedVisited)
                }
            }.awaitAll().filterNotNull().minOrNull()
        }
    }

    override fun part1(input: List<List<Char>>): Any? {
        val start = input.findInMatrix { row, col, c ->
            if (c == start) {
                Point(y = row, x = col, z = range[c]!!)
            } else null
        }!!

        val end = input.findInMatrix { row, col, c ->
            if (c == end) {
                Point(y = row, x = col, z = range[c]!!)
            } else null
        }!!

        val map = input.mapIndexed { row, it -> it.mapIndexed { col, c -> Point(y = row, x = col, z = range[c]!!) } }

//        return depth(
//            Params(
//                current = start,
//                end = end,
//                map = map,
//                visited = emptySet(),
//            )
//        )

        return runBlocking { findEnd(current = start, end = end, map = map, emptySet()) }
    }

    override fun part2(input: List<List<Char>>): Any? {
        TODO("Not yet implemented")
    }
}




