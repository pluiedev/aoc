package me.pluie.aoc

// too lazy to type the entire thing
/**
 * Shortcut for [map].
 */
fun <T, R> Iterable<T>.m(f: (T) -> R) = map(f)
fun <T, R> Iterable<T>.fm(f: (T) -> Iterable<R>) = flatMap(f)
fun <T, R> Sequence<T>.m(f: (T) -> R) = map(f)
fun <T, R> Sequence<T>.fm(f: (T) -> Sequence<R>) = flatMap(f)

inline fun <reified T> Iterable<T>.occurrences() = groupingBy { it }.eachCount()
inline fun <reified T> Sequence<T>.occurrences() = groupingBy { it }.eachCount()

// 2-fold cartesian products
infix fun <T> Iterable<T>.cartesian(o: Iterable<T>) = fm { f -> o.m { e -> e to f } }
infix fun <T> Sequence<T>.cartesian(o: Sequence<T>) = fm { f -> o.m { e -> e to f } }

// 3-fold cartesian products
fun <T> Iterable<T>.cartesian3(a: Iterable<T>, b: Iterable<T>)
        = fm { x -> a.fm { y -> b.m { z -> Triple(x, y, z) }} }
fun <T> Sequence<T>.cartesian3(a: Sequence<T>, b: Sequence<T>)
        = fm { x -> a.fm { y -> b.m { z -> Triple(x, y, z) }} }

fun <T> Iterable<T>.cartesianSquare() = cartesian(this)
fun <T> Sequence<T>.cartesianSquare() = cartesian(this)
fun <T> Iterable<T>.cartesianCube() = cartesian3(this, this)
fun <T> Sequence<T>.cartesianCube() = cartesian3(this, this)

fun <T> Sequence<T>.toL() = toList()
fun <T> Iterable<T>.toS() = asSequence()

// very useful iterable/sequence functions that are missing in Kotlin
// most of them came from Rust

fun <T: Comparable<T>> Iterable<T>.minmax(): Pair<T, T>? {
    val iterator = iterator()
    if (!iterator.hasNext()) return null
    var min = iterator.next()
    var max = min
    while (iterator.hasNext()) {
        val e = iterator.next()
        if (min > e) min = e
        if (max < e) max = e
    }
    return min to max
}

fun <T> Sequence<T>.cycle() = sequence { while (true) yieldAll(this@cycle) }

inline fun <T> Iterable<T>.takeUntil(
    predicate: (T) -> Boolean
): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = !predicate(it)
        result
    }
}
inline fun <T> Sequence<T>.takeUntil(
    crossinline predicate: (T) -> Boolean
): Sequence<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = !predicate(it)
        result
    }
}
