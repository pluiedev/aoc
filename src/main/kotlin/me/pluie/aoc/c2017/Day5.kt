package me.pluie.aoc.c2017

import me.pluie.aoc.*
import kotlin.math.*

fun main() = challenge(2017, 5) {
    val input = l(::int).toMutableList()

    submit {
        var tick = 0
        var pos = 0
        while (pos < input.size) {
            val v = input[pos]
            input[pos]++
            pos += v
            tick++
        }
        tick
    }
}