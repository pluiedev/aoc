package me.pluie.aoc.c2017

import me.pluie.aoc.*

// TODO
fun main() = challenge(2017, 12) {
    val pos = l {
        it.match("(\\d+) <-> (.+)"){ (n, list) ->
            n.toInt() to list.spacedCsv(::int).toSet()
        }!!
    }.toMap()

    val seen = mutableSetOf<Int>()
    fun discover(i: Int) {
        if (i in seen) return
        seen += i
        pos[i]!!.forEach { discover(it) }
    }
    discover(0)

    submit { seen.size }
}