package me.pluie.aoc.c2017

import me.pluie.aoc.*

fun main() = challenge(2017, 2) {
    val input = l { it.ws(::int).toL() }

    submit {
        input.m {
            val (a, b) = it.minmax()!!
            b - a
        }.sum()
    }
    submit {
        input.m {
            val (x, y) = it.sortedDescending().splitInTwain()
            val (a, b) = (x cartesian y).find { (a, b) -> a % b == 0 }!!
            a / b
        }.sum()
    }
}