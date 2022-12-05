package me.pluie.aoc.c2017

import me.pluie.aoc.*

fun main() = challenge(2017, 1) {
    submit {
        windowed(2)
            .sumOf { if (it[1] == it[0]) it[0].digitToInt() else 0 } +
                if (last() == first()) last().digitToInt() else 0
    }
    submit {
        val (a, b) = splitInTwain()
        a.zip(b).sumOf { (x, y) -> if (x == y) x.digitToInt() * 2 else 0 }
    }
}