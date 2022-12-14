package me.pluie.aoc.c2017

import me.pluie.aoc.*

fun main() = challenge(2017, 4) {
    submit {
        l().count { !it.spaces().occurrences().any { (_, v) -> v > 1 } }
    }
    submit {
        l().count {
            !it.spaces(String::toSortedSet)
                .withIndex()
                .cartesianSquare()
                .any { (a, b) -> a.index != b.index && a.value == b.value }
        }
    }
}