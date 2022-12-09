package me.pluie.aoc.c2018

import me.pluie.aoc.*

fun main() = challenge(2018, 3) {
    data class Claim(val n: Int, val x: Int, val y: Int, val w: Int, val h: Int)
    val grid = ByteArray(1000 * 1000) { 0 }

    val claims = l {
        it.match("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")!!
            .toList()
            .ints().let {(n, x, y, w, h) ->
                Claim(n, x, y, w, h)
            }
    }

    claims.forEach {
        for (y in it.y until it.y + it.h) {
            for (x in it.x until it.x + it.w) {
                grid[y * 1000 + x]++
            }
        }
    }

    submit { grid.count { it > 1 } }
    submit {
        claims.findLast {
            for (y in it.y until it.y + it.h) {
                for (x in it.x until it.x + it.w) {
                    if (grid[y * 1000 + x] > 1) return@findLast false
                }
            }
            true
        }!!.n
    }
}