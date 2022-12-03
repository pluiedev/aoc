package me.pluie.aoc.c2022
import me.pluie.aoc.*

fun main() = challenge(2022, 3) {
    fun priority(c: Char) = 1 + if (c in 'a' .. 'z') {
        c - 'a'
    } else {
        c - 'A' + 26
    }

    submit {
        lineSequence().flatMap {
            val (a, b) = it.splitInTwain().map { s -> s.toSet() }

            a.intersect(b).map(::priority).asSequence()
        }.sum()
    }
    submit {
        lineSequence().chunked(3).sumOf {
            val (a, b, c) = it.map { s -> s.toSet() }
            priority(a.intersect(b).intersect(c).first())
        }
    }
}