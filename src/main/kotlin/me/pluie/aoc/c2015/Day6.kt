package me.pluie.aoc.c2015
import me.pluie.aoc.*
import java.util.BitSet

const val SIZE = 1000

fun main() = challenge(2015, 6) {
    val cmds = Regex("(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)")
        .findAll(this)
        .toList()

    submit {
        val bits = BitSet(SIZE * SIZE)

        fun modify(p1x: Int, p1y: Int, p2x: Int, p2y: Int, func: BitSet.(Int, Int) -> Unit) {
            for (y in p1y..p2y) {
                bits.func(y * SIZE + p1x, y * SIZE + p2x + 1 /* inclusive */)
            }
        }
        cmds.forEach {
            val (_, action, p1x, p1y, p2x, p2y) = it.groupValues

            modify(
                p1x.toInt(),
                p1y.toInt(),
                p2x.toInt(),
                p2y.toInt(),
                when (action) {
                    "toggle" -> BitSet::flip
                    "turn on" -> BitSet::set
                    "turn off" -> BitSet::clear
                    else -> error("impossible")
                }
            )
        }
        bits.cardinality()
    }

    submit {
        val lights = ByteArray(SIZE * SIZE) { 0.toByte() }

        cmds.forEach {
            val (_, action, p1x, p1y, p2x, p2y) = it.groupValues

            fun modify(p1x: Int, p1y: Int, p2x: Int, p2y: Int, delta: Byte) {
                for (y in p1y..p2y) {
                    for (x in p1x..p2x) {
                        val idx = y * SIZE + x
                        // this is so stupid oml
                        lights[idx] = (lights[idx] + delta).toByte().coerceAtLeast(0)
                    }
                }
            }

            modify(
                p1x.toInt(),
                p1y.toInt(),
                p2x.toInt(),
                p2y.toInt(),
                when (action) {
                    "toggle" -> 2
                    "turn on" -> 1
                    "turn off" -> -1
                    else -> error("impossible")
                }
            )
        }

        lights.sum()

    }
}