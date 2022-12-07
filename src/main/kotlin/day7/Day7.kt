package day7

import Day
import kotlin.LazyThreadSafetyMode.NONE

fun main() {
    Day7("Day07").apply {
        println(part1(parsedInput))
        println(part2(parsedInput))
    }
}

class Day7(input: String) : Day<List<Day7.Dir>>(input) {

    class Dir(
        var filesSize: Int = 0,
        val dirs: MutableMap<String, Dir> = mutableMapOf(),
        val parent: Dir? = null
    ) {
        val size: Int by lazy(NONE) { filesSize + dirs.values.sumOf { it.size } }
        val allDirs: List<Dir> get() = listOf(this) + dirs.values.flatMap { it.allDirs }
    }

    override fun parseInput(): List<Dir> = Dir().also { root ->
        inputByLines.map { it.split(" ") + "" }.fold(root) { workspace, (a, b, c) ->
            when {
                a == "\$" && b == "ls" -> workspace
                a == "\$" && b == "cd" && c == "/" -> workspace
                a == "\$" && b == "cd" && c == ".." -> workspace.parent!!
                a == "\$" && b == "cd" -> workspace.dirs[c]!!
                a == "dir" -> workspace.apply { dirs[b] = Dir(parent = workspace) }
                else -> workspace.apply { filesSize += a.toInt() }
            }
        }
    }.allDirs

    override fun part1(input: List<Dir>): Any = input.map { it.size }.filter { it <= 100000 }.sum()

    override fun part2(input: List<Dir>): Any {
        val neededDiskSpace = 30000000 + input.first().size - 70000000
        return input.map { it.size }.filter { it > neededDiskSpace }.min()
    }
}

