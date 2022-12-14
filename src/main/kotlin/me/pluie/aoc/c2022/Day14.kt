package me.pluie.aoc.c2022

import me.pluie.aoc.*
import kotlin.math.*

fun main() = challenge(2022, 14) {
    val grid = BitGrid(200  until 1000, 0 until 200)
    val maxY = l().maxOf { s ->
        s.splitToSequence(" -> ")
            .m { it.pos() }
            .windowed(2) { (a, b) ->
                grid.fill(a, b, true)
                max(a.y, b.y)
            }.max()
    } + 2

    val grid2 = grid.copy(data = grid.data.copy())
    grid2.setRow(grid2.xRange, maxY, true)

    fun simulate(grid: BitGrid): Int {
        var cnt = 0
        top@ while (true) {
            var start = GridPos(500, 0, grid)
            while (true) {
                val s = start.s() ?: break@top
                if (s.value) {
                    val sw = start.sw() ?: break@top
                    if (!sw.value) { start = sw; continue }

                    val se = start.se() ?: break@top
                    if (!se.value) { start = se; continue }

                    if (start.value) break@top
                    start.value = true
                    cnt++
                    break
                } else {
                    start = s
                }
            }
        }
        return cnt
    }

    submit { simulate(grid) }
    submit { simulate(grid2) }
}