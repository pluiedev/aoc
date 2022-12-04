package me.pluie.aoc

import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.system.exitProcess

class Challenge(val input: String): CharSequence by input {
    val results = mutableListOf<Int>()

    inline fun submit(action: () -> Int) {
        results.add(action())
    }
}

fun challenge(year: Int, day: Int, action: Challenge.() -> Unit) {
    val scPath = Path("inputs/$year/sc${day}.txt")
    if (scPath.exists()) {
        val text = scPath.readText()

        text.split("\n%\n").forEachIndexed { case, input ->
            val (scInput, scExpectedResults) = input.split("\n#\n", limit = 2)

            val expected = scExpectedResults
                .split("\n")
                .map(String::toInt)
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


fun int(s: String): Int = s.toInt()
@JvmName("int2")
fun String.int(): Int = toInt()
fun Iterable<String>.ints(radix: Int = 10): List<Int> = map { it.toInt(radix) }

fun Iterable<Float>.product(): Float = reduce(Float::times)
fun Iterable<Double>.product(): Double = reduce(Double::times)
fun Iterable<Int>.product(): Int = reduce(Int::times)

// shortcuts
fun <T, R> Iterable<T>.m(f: (T) -> R) = map(f)
fun <T, R> Iterable<T>.fm(f: (T) -> Iterable<R>) = flatMap(f)
fun <R> CharSequence.m(f: (Char) -> R) = map(f)


operator fun <T> Iterable<T>.rem(other: Iterable<T>): Set<T> = this intersect other.toSet()
operator fun String.rem(other: String): Set<Char> = this.toSet() intersect other.toSet()
operator fun Set<Char>.rem(other: String): Set<Char> = this intersect other.toSet()

fun CharSequence.l(): List<String> = lines()
fun <R> CharSequence.l(f: (String) -> R): List<R> = l().map(f)

fun CharSequence.blocks(): List<List<String>> =
    split("\n\n", "\r\n\r\n").map { it.l() }
fun <R> CharSequence.blocks(f: (List<String>) -> R): List<R> = blocks().map(f)
fun CharSequence.csv(): List<String> = split(",")
fun <R> CharSequence.csv(f: (String) -> R): List<R> = csv().map(f)
fun CharSequence.hyphens(): List<String> = split("-")
fun <R> CharSequence.hyphens(f: (String) -> R): List<R> = hyphens().map(f)


inline fun <reified T> Iterable<T>.occurrences() = groupingBy { it }.eachCount()

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


fun CharSequence.split2(vararg delimiters: String): Pair<String, String> {
    val (a, b) = split(*delimiters, limit = 2)
    return a to b
}
fun CharSequence.splitAt(idx: Int): Pair<String, String> = substring(0..idx) to substring(idx)
fun CharSequence.splitInTwain(): Pair<String, String> = splitAt(length / 2)

// pair bullshit

fun <T> Pair<T, T>.s(): Sequence<T> = listOf(first, second).asSequence()
inline fun <T, R> Pair<T, T>.m(f: (T) -> R): Pair<R, R> = f(first) to f(second)

fun <T> Triple<T, T, T>.s(): Sequence<T> = listOf(first, second, third).asSequence()
inline fun <T, R> Triple<T, T, T>.m(f: (T) -> R): Triple<R, R, R> = Triple(f(first), f(second), f(third))
