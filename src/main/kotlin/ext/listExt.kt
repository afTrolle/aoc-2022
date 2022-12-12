package ext

operator fun <E> List<E>.component6(): E {
    return this[5]
}

inline fun CharSequence.indexOfFirstIndexed(predicate: (index: Int, Char) -> Boolean): Int {
    var index = 0
    return indexOfFirst { predicate(index++, it) }
}

fun <E> MutableList<E>.removeWhile(predicate: (E) -> Boolean): List<E> {
    val removed = mutableListOf<E>()
    for (index in indices) {
        val value = removeFirst()
        if (predicate(value)) {
            removed.add(value)
        } else {
            add(0, value)
            return removed
        }
    }
    return removed
}

inline fun <reified T> List<List<T>>.transpose(): List<List<T>> {
    val cols = this[0].size
    val rows = this.size
    return List(cols) { col ->
        List(rows) { row ->
            this[row][col]
        }
    }
}

inline fun <reified T, reified R> List<T>.merge(
    items: List<T>,
    combine: (a: T, b: T) -> R
): List<R> = mapIndexed { index, item ->
    combine(item, items[index])
}

inline fun <reified T, reified R> List<List<T>>.mergeMatrix(
    items: List<List<T>>,
    combine: (a: T, b: T) -> R
): List<List<R>> = merge(items) { a: List<T>, b: List<T> ->
    a.merge(b, combine)
}