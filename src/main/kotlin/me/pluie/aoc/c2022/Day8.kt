package me.pluie.aoc.c2022
import me.pluie.aoc.*

fun main() = challenge(2022, 8) {
    val trees = grid(Char::digitToInt)

    submit {
        trees.count { p ->
            val t = p.value
            p.cardinals().any { dir -> dir.all { it.value < t } }
        }
    }
    submit {
        trees.maxOf { p ->
            val t = p.value
            p.norths().takeUntil { it.value >= t }.count() *
            p.souths().takeUntil { it.value >= t }.count() *
            p.easts().takeUntil { it.value >= t }.count() *
            p.wests().takeUntil { it.value >= t }.count()
        }
    }
}