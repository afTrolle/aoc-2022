abstract class AOC<R>(private val file: String) {
    private val rawInput = read(file)
    val parsed get() = parse(rawInput)
    fun run() {
        val parsedInput = parsed
        println("$file partOne: ${partOne(parsedInput)}")
        println("$file partTwo: ${partTwo(parsedInput)}")
    }

    abstract fun parse(input: String): R
    open fun partOne(input: R): Any? = null
    open fun partTwo(input: R): Any? = null

    companion object {
        fun read(file: String): String {
            return AOC::class.java.getResourceAsStream("$file.txt")!!.bufferedReader().readText()
        }
    }
}