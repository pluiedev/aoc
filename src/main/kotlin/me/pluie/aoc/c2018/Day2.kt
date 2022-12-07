package me.pluie.aoc.c2018

import me.pluie.aoc.*

fun main() = challenge(2018, 2) {
    submit {
        val ocs = l { it.occurrences() }
        ocs.count { 2 in it.values } * ocs.count { 3 in it.values }
    }
    submit {
        l { it.toMutableList() }
            .cartesianSquare()
            .m { (a, b) ->
                a to a
                    .zip(b)
                    .withIndex()
                    .filter { (_, p) -> p.first != p.second }
            }
            .filter { (_, b) -> b.size == 1 }
            .m { (a, b) ->
                a.removeAt(b[0].index)
                a.join()
            }
            .first()
    }
}