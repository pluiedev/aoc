package me.pluie.aoc.c2020

import me.pluie.aoc.*

// TODO
fun main() = challenge(2020, 10) {
    val nums = l(::int).toSortedSet()
    nums += 0
    nums += nums.last() + 3

    val diffs = nums.asSequence().windowed(2).m { it[1] - it[0] }
    submit { diffs.count { it == 3 } * diffs.count { it == 1 } }
}