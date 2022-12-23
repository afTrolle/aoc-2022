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

data class Vertex(
    val y: Int,
    val x: Int,
    val encodedAltitude: Char,
) {
    val altitude: Int = Day12.convertChar[encodedAltitude]!!
    var left: Vertex? = null
    var right: Vertex? = null
    var up: Vertex? = null
    var down: Vertex? = null
    val neighbors get() = listOfNotNull(left, right, up, down)
}

fun Vertex.pointIsValidHeight(current: Vertex): Vertex? = takeIf {
    altitude <= current.altitude + 1
}

class Day12(input: String) : Day<List<List<Vertex>>>(input) {
    private val List<List<Vertex>>.start
        get() = findInMatrix { _, _, vertex ->
            if (vertex.encodedAltitude == startChar) vertex else null
        }!!
    private val List<List<Vertex>>.end
        get() = findInMatrix { _, _, vertex ->
            if (vertex.encodedAltitude == endChar) vertex else null
        }!!

    private fun bfs(start: Vertex, end: Vertex): Int {
        val queue = ArrayDeque<Pair<Long, Vertex>>()
        val visited = mutableSetOf<Vertex>()
        visited.add(start)
        queue.add(1L to start)
        var counter = 0

        while (queue.isNotEmpty()) {
            val (depth, item) = queue.removeFirst()
            val adj = item.neighbors
            if (end == item) {
                return depth.toInt()
            }
            adj.forEach {
                if (!visited.contains(it)) {
                    if (end == it) {
                        return depth.toInt()
                    }
                    visited.add(it)
                    queue.add(depth + 1 to it)
                    counter++
                }
            }
        }
        return Int.MAX_VALUE
    }

    override fun parseInput(): List<List<Vertex>> {
        val graph = inputByLines.map { it.toList() }.mapIndexed { rowIndex, row ->
            row.mapIndexed { colIndex, c ->
                Vertex(rowIndex, colIndex, c)
            }
        }
        graph.forEach { rows ->
            rows.forEach { vertex ->
                vertex.apply {
                    down = graph.getOrNull(y + 1)?.getOrNull(x)?.pointIsValidHeight(this)
                    up = graph.getOrNull(y - 1)?.getOrNull(x)?.pointIsValidHeight(this)
                    left = graph.getOrNull(y)?.getOrNull(x - 1)?.pointIsValidHeight(this)
                    right = graph.getOrNull(y)?.getOrNull(x + 1)?.pointIsValidHeight(this)
                }
            }
        }
        return graph
    }

    override fun part1(input: List<List<Vertex>>): Any {
        val start = input.start
        val end = input.end
        return bfs(start, end)
    }

    override fun part2(input: List<List<Vertex>>): Any {
        val end = input.end
        return input.asSequence().flatten().filter { it.encodedAltitude == 'a' }.map { bfs(it, end) }.min()
    }

    companion object {
        const val startChar = 'S'
        const val endChar = 'E'

        val convertChar: Map<Char, Int> = buildMap {
            ('a'..'z').forEach {
                put(it, it - 'a')
            }
            // S is same as a
            put(startChar, get('a')!!)
            // E is same as z
            put(endChar, get('z')!!)
        }
    }
}




