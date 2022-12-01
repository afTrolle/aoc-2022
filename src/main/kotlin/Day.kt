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
    val inputByGroups by lazy(LazyThreadSafetyMode.NONE) { input.split(lineSeparator() + lineSeparator()) }

    val inputParsed by lazy(LazyThreadSafetyMode.NONE) { parse() }

    fun run() {
        println("$file partOne: ${partOne(inputParsed)}")
        println("$file partTwo: ${partTwo(inputParsed)}")
    }

    abstract fun parse(): R
    abstract fun partOne(input: R): Any?
    abstract fun partTwo(input: R): Any?

    companion object {
        fun read(file: String): String {
            val path = Files.find(
                Path("src", "main", "kotlin"),
                2,
                { t: Path, _: BasicFileAttributes -> t.name == "$file.txt" }).findFirst().get()
            return path.readText()
        }
    }
}