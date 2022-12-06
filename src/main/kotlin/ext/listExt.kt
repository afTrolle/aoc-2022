package ext

operator fun <E> List<E>.component6(): E {
    return this[5]
}

inline fun CharSequence.indexOfFirstIndexed(predicate: (index: Int, Char) -> Boolean): Int {
    var index = 0
    return indexOfFirst { predicate(index++, it) }
}