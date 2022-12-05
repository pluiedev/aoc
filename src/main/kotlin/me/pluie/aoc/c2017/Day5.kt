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
            if (v >= 3)
                i[pos]--
            else
                i[pos]++
            pos += v
            tick++
        }
        tick
    }
}