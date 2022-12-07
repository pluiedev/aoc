package me.pluie.aoc

import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.system.exitProcess

data class Challenge(val input: String): CharSequence by input {
    val results = mutableListOf<String>()

    inline fun <T> submit(action: () -> T) {
        results.add(action().toString())
    }
    inline fun <T> Iterable<T>.massSubmit() {
        mapTo(results) { it.toString() }
    }
    inline fun <T> Sequence<T>.massSubmit() {
        mapTo(results) { it.toString() }
    }

    override fun toString() = input
}

fun challenge(year: Int, day: Int, action: Challenge.() -> Unit) {
    val scPath = Path("inputs/$year/sc${day}.txt")
    if (scPath.exists()) {
        val text = scPath.readText()

        text.split("\n%\n").forEachIndexed { case, input ->
            val (scInput, scExpectedResults) = input.split("\n#\n", limit = 2)

            val expected = scExpectedResults
                .split("\n")
            val actual = Challenge(scInput).apply(action).results

            expected.zip(actual).mapIndexed { part, (e, a) ->
                if (e != a) {
                    System.err.println("""
Sanity check failed! (case ${case + 1}, part ${part + 1})

Input:
$scInput

Expected: $e;
Actual: $a.
                    """)
                    exitProcess(1)
                }
            }
        }

        println("Sanity checks passed!")
    }

    Path("inputs/$year/day${day}.txt")
        .readText()
        .let(::Challenge)
        .apply(action)
        .results
        .forEachIndexed { i, v ->
            println("Part ${i + 1}: $v")
        }
}


fun int(s: String, radix: Int = 10): Int = s.trim().toInt(radix)
@JvmName("int2")
fun CharSequence.int(radix: Int = 10): Int = toString().trim().toInt(radix)
fun Iterable<String>.ints(radix: Int = 10): List<Int> = map { it.toInt(radix) }
fun Sequence<String>.ints(radix: Int = 10): Sequence<Int> = map { it.toInt(radix) }

fun Iterable<Float>.product(): Float = reduce(Float::times)
fun Iterable<Double>.product(): Double = reduce(Double::times)
fun Iterable<Int>.product(): Int = reduce(Int::times)

// shortcuts
fun <T, R> Iterable<T>.m(f: (T) -> R) = map(f)
fun <T, R> Iterable<T>.fm(f: (T) -> Iterable<R>) = flatMap(f)
fun <T, R> Sequence<T>.m(f: (T) -> R) = map(f)
fun <T, R> Sequence<T>.fm(f: (T) -> Sequence<R>) = flatMap(f)
fun <R> CharSequence.m(f: (Char) -> R) = map(f)


operator fun <T> Iterable<T>.rem(other: Iterable<T>): Set<T> = this intersect other.toSet()
operator fun String.rem(other: String): Set<Char> = this.toSet() intersect other.toSet()
operator fun Set<Char>.rem(other: String): Set<Char> = this intersect other.toSet()

fun CharSequence.l(): Sequence<String> = lineSequence()
fun <R> CharSequence.l(f: (String) -> R): Sequence<R> = l().m(f)

fun CharSequence.blocks(): Sequence<Sequence<String>> =
    splitToSequence("\n\n", "\r\n\r\n").m { it.l() }
fun <R> CharSequence.blocks(f: (Sequence<String>) -> R): Sequence<R> = blocks().m(f)
fun CharSequence.csv(): Sequence<String> = splitToSequence(",")
fun <R> CharSequence.csv(f: (String) -> R): Sequence<R> = csv().m(f)
fun CharSequence.hyphens(): Sequence<String> = splitToSequence("-")
fun <R> CharSequence.hyphens(f: (String) -> R): Sequence<R> = hyphens().m(f)
fun CharSequence.spaces(): Sequence<String> = splitToSequence(" ")
fun <R> CharSequence.spaces(f: (String) -> R): Sequence<R> = spaces().m(f)
fun CharSequence.ws(): Sequence<String> = splitToSequence(Regex("\\s+"))
fun <R> CharSequence.ws(f: (String) -> R): Sequence<R> = ws().m(f)

fun CharSequence.split2(vararg delimiters: String): Pair<String, String>
    = split(*delimiters, limit = 2).let { it[0] to it[1] }
fun <R> CharSequence.split2(vararg delimiters: String, f: (String) -> R): Pair<R, R>
    = split(*delimiters, limit = 2).let { f(it[0]) to f(it[1]) }


inline fun <T: Comparable<T>> Iterable<T>.minmax(): Pair<T, T>? {
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
inline fun <reified T> Iterable<T>.occurrences() = groupingBy { it }.eachCount()
inline fun <reified T> Sequence<T>.occurrences() = groupingBy { it }.eachCount()
fun CharSequence.occurrences() = groupingBy { it }.eachCount()

inline fun <reified T> T.dbg() = also(::println)
inline fun <reified T> Iterable<T>.dbgAll() = forEach { it.dbg() }
inline fun <reified T> Sequence<T>.dbgAll() = forEach { it.dbg() }

@Suppress("NOTHING_TO_INLINE")
inline fun p(vararg v: Any) = v.forEach { it.dbg() }

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> List<T>.component6(): T {
    return get(5)
}

infix fun UShort.shl(by: UShort) = (toInt() shl by.toInt()).toUShort()
infix fun UShort.shr(by: UShort) = (toInt() shr by.toInt()).toUShort()

fun CharSequence.splitAt(idx: Int): Pair<String, String> = substring(0..idx) to substring(idx)
fun CharSequence.splitInTwain(): Pair<String, String> = splitAt(length / 2)
fun <T> List<T>.splitAt(idx: Int): Pair<List<T>, List<T>> = subList(0, idx) to subList(idx, size)
fun <T> List<T>.splitInTwain(): Pair<List<T>, List<T>> = splitAt(size / 2)

// pair bullshit

fun <T> Pair<T, T>.s(): Sequence<T> = listOf(first, second).asSequence()
inline fun <T, R> Pair<T, T>.m(f: (T) -> R): Pair<R, R> = f(first) to f(second)

fun <T> Triple<T, T, T>.s(): Sequence<T> = listOf(first, second, third).asSequence()
inline fun <T, R> Triple<T, T, T>.m(f: (T) -> R): Triple<R, R, R> = Triple(f(first), f(second), f(third))

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
fun CharSequence.toS() = asSequence()

fun <T> List<T>.copy() = toMutableList()
@JvmName("copy2")
fun <T> List<List<T>>.copy() = map { it.toMutableList() }.toMutableList()


fun Iterable<Char>.join() = joinToString("")
fun Sequence<Char>.join() = joinToString("")
@JvmName("joinString")
fun Iterable<String>.join() = joinToString("")
@JvmName("joinString")
fun Sequence<String>.join() = joinToString("")

fun <T> Sequence<T>.cycle() = sequence { while (true) yieldAll(this@cycle) }

fun CharSequence.match(r: String) = Regex(r).find(this)?.destructured
