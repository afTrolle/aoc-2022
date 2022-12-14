import java.lang.System.lineSeparator
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import kotlin.io.path.Path
import kotlin.io.path.name
import kotlin.io.path.readText

abstract class Day<R>(private val file: String) {
    val input = read(file)
    val inputByLines by lazy(LazyThreadSafetyMode.NONE) { input.lines() }
    val inputByGroups by lazy(LazyThreadSafetyMode.NONE) {
        input.split(lineSeparator() + lineSeparator()).map { it.lines() }
    }

    val parsedInput get() = parseInput()

    fun solve() {
        println("$file partOne: ${part1(parsedInput)}")
        kotlin.runCatching { println("$file partTwo: ${part2(parsedInput)}") }
    }

    abstract fun parseInput(): R
    abstract fun part1(input: R): Any?
    abstract fun part2(input: R): Any?

    companion object {
        fun read(file: String): String {
            val searchPath = Path("src", "main", "kotlin")
            return Files.find(searchPath, 2, { t: Path, _: BasicFileAttributes -> t.name == "$file.txt" })
                .findFirst()
                .get()
                .readText()
        }
    }
}