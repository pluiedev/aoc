package me.pluie.aoc.c2021
import me.pluie.aoc.*

fun main() = challenge(2021, 2) {
    val cmds = lineSequence().map {
        val (dir, mag) = it.split(' ')
        dir to mag.toInt()
    }

    submit {
        var x = 0
        var y = 0

        cmds.forEach { (dir, mag) ->
            when (dir) {
                "forward" -> x += mag
                "down" -> y += mag
                "up" -> y -= mag
                else -> error("impossible direction")
            }
        }
        x * y
    }
    submit {
        var x = 0
        var y = 0
        var aim = 0

        cmds.forEach { (dir, mag) ->
            when (dir) {
                "forward" -> {
                    x += mag
                    y += aim * mag
                }
                "down" -> aim += mag
                "up" -> aim -= mag
                else -> error("impossible direction")
            }
        }

        x * y
    }
}