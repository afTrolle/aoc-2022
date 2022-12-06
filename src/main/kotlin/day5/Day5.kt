package day5

import Day
import ext.component6

fun main() {
    Day5("Day05").solve()
}

class Day5(input: String) : Day<Pair<List<MutableList<Char>>, List<Day5.Action>>>(input) {

    data class Action(val from: Int, val to: Int, val units: Int)

    override fun parseInput(): Pair<List<MutableList<Char>>, List<Action>> {
        val (init, actions) = inputByGroups
        val towerByRows = init.dropLast(1).map { line ->
            line.chunked(4).map { it[1].takeIf(Char::isLetter) }
        }
        val numberOfTowers = init.last().split(" ").mapNotNull(String::toIntOrNull).max()
        val towers = List(numberOfTowers) { towerIndex ->
            towerByRows.mapNotNullTo(mutableListOf()) {
                it.getOrNull(towerIndex)
            }
        }

        val tasks = actions.map {
            val (_, units, _, from, _, to) = it.split(" ").map(String::toIntOrNull)
            Action(from!!, to!!, units!!)
        }
        return towers to tasks
    }

    override fun part1(input: Pair<List<MutableList<Char>>, List<Action>>): Any {
        val (towers, tasks) = input
        tasks.forEach { (from, to, units) ->
            for (x in 0 until units) {
                val item = towers[from - 1].removeFirst()
                towers[to - 1].add(0, item)
            }
        }
        return towers.map { it.first() }.joinToString(separator = "")
    }

    override fun part2(input: Pair<List<MutableList<Char>>, List<Action>>): Any {
        val (towers, tasks) = input
        tasks.forEach { (from, to, units) ->
            val cargo = List(units) { towers[from - 1].removeFirst() }
            towers[to - 1].addAll(0, cargo)
        }
        return towers.map { it.first() }.joinToString(separator = "")
    }

}
