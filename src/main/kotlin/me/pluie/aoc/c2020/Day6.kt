package me.pluie.aoc.c2020

import me.pluie.aoc.*

fun main() = challenge(2020, 6) {
    submit { blocks().sumOf { it.fm { s -> s.asSequence() }.toSet().dbg().size } }

    submit { blocks().sumOf { it.m { s -> s.toSet() }.reduce { a, c -> a intersect c }.size } }
}