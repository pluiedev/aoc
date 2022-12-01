package me.pluie.aoc.c2015
import me.pluie.aoc.*

fun main() = challenge(2015, 3) {

    fun Sequence<Char>.listPoints(): Sequence<Pair<Int, Int>> =
        runningFold(0 to 0) { (x, y), c ->
            when (c) {
                '>' -> (x + 1) to y
                '<' -> (x - 1) to y
                '^' -> x to (y + 1)
                'v' -> x to (y - 1)
                else -> error("impossible")
            }
        }

    submit {
        asSequence().listPoints().distinct().count()
    }
    submit {
        withIndex()
            .groupBy { it.index % 2 }
            .values
            .flatMap { l ->
                l.asSequence().map { it.value }.listPoints()
            }
            .distinct()
            .count()
    }
}