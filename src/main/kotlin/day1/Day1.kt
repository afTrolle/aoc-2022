package day1

import Day

fun main() {
    Day1("Day01").solve()
}

class Day1(file: String) : Day<List<List<Int>>>(file) {

    override fun parse(): List<List<Int>> = inputByGroups
        .map { elf -> elf.mapNotNull { snack -> snack.toIntOrNull() } }

    override fun part1(input: List<List<Int>>) = input.maxOfOrNull { it.sum() }

    override fun part2(input: List<List<Int>>) = input.map { it.sum() }.sortedDescending().take(3).sum()

}
