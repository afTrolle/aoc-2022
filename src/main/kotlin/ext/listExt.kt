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