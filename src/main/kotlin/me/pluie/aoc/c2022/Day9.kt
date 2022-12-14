package me.pluie.aoc.c2022
import me.pluie.aoc.*
import kotlin.math.sign

const val SIZE = 400
const val OFFSET = 200

fun main() = challenge(2022, 9) {
    val grid = BitGrid(-200..200, -200..200)

    val movements = l { s ->
        val (dir, amt) = s.split(" ")
        when (dir) {
            "U" -> 0 to 1
            "D" -> 0 to -1
            "R" -> 1 to 0
            "L" -> -1 to 0
            else -> error("impossible")
        } to amt.toInt()
    }

    fun simulate(knotsNum: Int): Int {
        val knots = MutableList(knotsNum) { grid.origin() }

        movements.forEach { (dir, amt) ->
            for (i in 0 until amt) {
                knots[0] = knots.first().move(dir)!!
                for (j in 0 until knots.lastIndex) {
                    val prev = knots[j]
                    val cur = knots[j + 1]
                    if (prev.euclideanDistance(cur) >= 2) {
                        knots[j + 1] = cur.move((prev.x - cur.x).sign, (prev.y - cur.y).sign)!!
                    }
                }
                knots.last().value = true
            }
        }
        val c = grid.cardinality()
        grid.data.clear()
        return c
    }
    submit { simulate(2) }
    submit { simulate(10) }
}