package me.pluie.aoc.c2018

import me.pluie.aoc.*

fun main() = challenge(2018, 1) {
    submit { l(::int).sum()  }

    var acc = 0
    val seen = mutableSetOf<Int>()
    for (a in l(::int).cycle()) {
        acc += a
        if (acc in seen) {
            submit { acc }
            break
        }
        seen.add(acc)
    }
}