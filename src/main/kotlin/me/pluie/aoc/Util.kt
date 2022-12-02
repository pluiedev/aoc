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

fun Sequence<String>.ints(radix: Int = 10): Sequence<Int> = map { it.toInt(radix) }

fun Sequence<Float>.product(): Float = reduce(Float::times)
fun Sequence<Double>.product(): Double = reduce(Double::times)
fun Sequence<Int>.product(): Int = reduce(Int::times)
fun Iterable<Float>.product(): Float = reduce(Float::times)
fun Iterable<Double>.product(): Double = reduce(Double::times)
fun Iterable<Int>.product(): Int = reduce(Int::times)

fun CharSequence.blocks(): Sequence<Sequence<String>> =
    splitToSequence("\n\n", "\r\n\r\n").map { it.lineSequence() }

inline fun <reified T> Sequence<T>.occurrences() = groupingBy { it }.eachCount()

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
