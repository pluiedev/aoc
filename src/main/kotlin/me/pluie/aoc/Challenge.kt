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
    fun <T> Iterable<T>.massSubmit() {
        mapTo(results) { it.toString() }
    }
    fun <T> Sequence<T>.massSubmit() {
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