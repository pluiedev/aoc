package me.pluie.aoc.c2021
import me.pluie.aoc.*
import kotlin.math.abs

fun main() = challenge(2021, 13) {
    val (pointsRaw, foldsRaw) = split("\n\n")
    val points = pointsRaw.l { it.pos() }.toMutableList()
    val folds = foldsRaw.l {
        it.match("fold along ([xy])=(\\d+)") { (axis, n) -> axis[0] to n.int() }!!
    }

    folds.forEachIndexed { i, (axis, n) ->
        points.forEachIndexed { j, p ->
            points[j] = when (axis) {
                'x' -> p.copy(x = n - abs(p.x - n))
                'y' -> p.copy(y = n - abs(p.y - n))
                else -> impossible()
            }
        }
        if (i == 0) submit { points.toSet().size }
    }

    submit { BitGrid.fromPoints(points).display() }
}