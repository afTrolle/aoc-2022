fun main() {
    Day1("Day01").run()
}

class Day1(file: String) : AOC<List<List<Int>>>(file) {

    override fun parse(input: String): List<List<Int>> = input.split("^\\s*\$".toRegex(RegexOption.MULTILINE))
        .map { elf -> elf.lines().mapNotNull { snack -> snack.toIntOrNull() } }

    override fun partOne(input: List<List<Int>>) = input.maxOfOrNull { it.sum() }

    override fun partTwo(input: List<List<Int>>) = input.map { it.sum() }.sortedDescending().take(3).sum()

}
