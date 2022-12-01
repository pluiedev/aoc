package me.pluie.aoc

import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.system.exitProcess

class Challenge(private val input: String): CharSequence by input {
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
Sanity check failed! (case $case, part $part)
    
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

fun Sequence<String>.ints(): Sequence<Int> = map(String::toInt)

fun Sequence<Float>.product(): Float = reduce(Float::times)
fun Sequence<Double>.product(): Double = reduce(Double::times)
fun Sequence<Int>.product(): Int = reduce(Int::times)
fun Iterable<Float>.product(): Float = reduce(Float::times)
fun Iterable<Double>.product(): Double = reduce(Double::times)
fun Iterable<Int>.product(): Int = reduce(Int::times)

fun CharSequence.blocks(): Sequence<Sequence<String>> =
    splitToSequence("\n\n", "\r\n\r\n").map { it.lineSequence() }