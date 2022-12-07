package day7

import Day
import kotlin.LazyThreadSafetyMode.NONE

fun main() {
    Day7("Day07").apply {
        println(part1(parsedInput))
        println(part2(parsedInput))
    }
}

class Day7(input: String) : Day<List<Int>>(input) {

    class Dir(
        var filesSize: Int = 0,
        val dirs: MutableMap<String, Dir> = mutableMapOf(),
        val parent: Dir? = null
    ) {
        val size: Int by lazy(NONE) { filesSize + dirs.values.sumOf { it.size } }
    }

    override fun parseInput(): List<Int> {
        val dirs = mutableListOf(Dir())
        var workspace = dirs.first()
        inputByLines.forEach { line ->
            when {
                line == "\$ ls" -> Unit
                line == "\$ cd /" -> Unit
                line == "\$ cd .." -> workspace = workspace.parent!!
                line.startsWith("\$ cd ") -> workspace = line.drop(5).let { workspace.dirs[it]!! }
                else -> line.split(" ").let { (sizeOrDir, name) ->
                    if (sizeOrDir == "dir") Dir(parent = workspace).let {
                        workspace.dirs[name] = it
                        dirs.add(it)
                    } else workspace.filesSize += sizeOrDir.toInt()
                }
            }
        }
        return dirs.map { it.size }
    }

    override fun part1(input: List<Int>): Any = input.filter { it <= 100000 }.sum()

    override fun part2(input: List<Int>): Any {
        val neededDiskSpace = 30000000 + input.first() - 70000000
        return input.fold(Int.MAX_VALUE) { acc, dir ->
            dir.takeIf { it in neededDiskSpace..acc } ?: acc
        }
    }
}

