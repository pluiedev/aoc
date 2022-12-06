package me.pluie.aoc.c2017

import me.pluie.aoc.*

fun main() = challenge(2017, 11) {
    val pos = csv().runningFold(HexPos.ORIGIN) { p, s ->
        when(s) {
            "n" -> p.n()
            "s" -> p.s()
            "nw" -> p.nw()
            "se" -> p.se()
            "ne" -> p.ne()
            "sw" -> p.sw()
            else -> error("impossible")
        }
    }
    submit { pos.last().distance() }
    submit { pos.maxOf { it.distance() } }
}