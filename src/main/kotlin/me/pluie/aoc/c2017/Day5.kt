package me.pluie.aoc.c2017

import me.pluie.aoc.*
import kotlin.math.*

fun main() = challenge(2017, 5) {
    val input = l(::int)

    submit {
        val i = input.toMutableList()
        var tick = 0
        var pos = 0
        while (pos < i.size) {
            val v = i[pos]
            i[pos]++
            pos += v
            tick++
        }
        tick
    }
    submit {
        val i = input.toMutableList()
        var tick = 0
        var pos = 0
        while (pos < i.size) {
            val v = i[pos]
            i[pos] += if (v >= 3) -1 else 1
            pos += v
            tick++
        }
        tick
    }
}