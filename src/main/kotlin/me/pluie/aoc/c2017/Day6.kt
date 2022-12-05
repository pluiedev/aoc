package me.pluie.aoc.c2017

import me.pluie.aoc.*
import kotlin.math.*

fun main() = challenge(2017, 6) {
    val banks = ws(::int).toMutableList()
    val seen = mutableListOf<List<Int>>()

    var lastBank = emptyList<Int>()

    while (lastBank !in seen) {
        seen.add(lastBank)

        val max = banks.max()
        val maxIdx = banks.indexOf(max)

        var remaining = max
        var idx = maxIdx + 1

        banks[maxIdx] = 0
        while (remaining > 0) {
            if (idx >= banks.size) idx = 0
            banks[idx]++
            remaining--
            idx++
        }
        lastBank = banks.toList()
    }
    submit { seen.size }
    submit {
        seen.size - seen.indexOf(lastBank)
    }
}