package me.pluie.aoc.c2017

import me.pluie.aoc.*

fun main() = challenge(2017, 4) {
    submit {
        l().count { !it.spaces().occurrences().any { (_, v) -> v > 1 } }
    }
    submit {
        l().count {
            !it
                .spaces()
                .map(String::toSortedSet)
                .withIndex()
                .cartesianSquare()
                .fold(false) { acc, (a, b) ->
                    acc || (a.index != b.index && a.value == b.value)
                }
        }
    }

}