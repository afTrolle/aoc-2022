package ext


fun <T : Comparable<T>> ClosedRange<T>.includes(other: ClosedRange<T>) =
    contains(other.start) && contains(other.endInclusive)

fun <T : Comparable<T>> ClosedRange<T>.overlap(other: ClosedRange<T>) =
    contains(other.start) || contains(other.endInclusive)
