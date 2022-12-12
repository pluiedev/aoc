package me.pluie.aoc

import kotlin.math.abs

data class HexPos(val q: Int, val r: Int) {
    val s: Int = -q - r

    fun n() = copy(r = r - 1)
    fun s() = copy(r = r + 1)
    fun nw() = copy(q = q - 1)
    fun se() = copy(q = q + 1)
    fun ne() = copy(q = q + 1, r = r - 1)
    fun sw() = copy(q = q - 1, r = r + 1)

    operator fun plus(o: HexPos) = copy(q = q + o.q, r = r + o.r)
    operator fun minus(o: HexPos) = copy(q = q - o.q, r = r - o.r)

    fun distance(o: HexPos = origin()): Int {
        val vec = this - o
        return (abs(vec.q) + abs(vec.q + vec.r) + abs(vec.r)) / 2
    }

    companion object {
        fun origin() = HexPos(0, 0)
    }
}