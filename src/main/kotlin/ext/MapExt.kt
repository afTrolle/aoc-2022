package ext

fun <K, V> Map<K, V>.reverse(): Map<V, K> = entries.associate { it.value to it.key }
