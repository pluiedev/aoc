package me.pluie.aoc.c2016

import me.pluie.aoc.*

fun main() = challenge(2016, 1) {
    val (_, v) = spacedCsv()
        .fold(Direction.North to Pos(0, 0)) { (dir, pos), move ->
            val newDir = when (move[0]) {
                'R' -> dir.cw()
                'L' -> dir.ccw()
                else -> error("impossible")
            }
            val newPos = pos.move(newDir, move.substring(1).int())!!
            newDir to newPos
        }
    submit { v.manhattanDistance(Pos.origin()) }
}