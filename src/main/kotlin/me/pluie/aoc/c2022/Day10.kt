package me.pluie.aoc.c2022
import me.pluie.aoc.*
import kotlin.math.sign

private const val WIDTH = 40
private const val HEIGHT = 6

fun main() = challenge(2022, 10) {
    var cycles = 0
    var x = 1
    var strength = 0
    var crt = 0

    val grid = BitGrid(WIDTH, HEIGHT)

    fun step() {
        if (++cycles % WIDTH == 20)
            strength += cycles * x

        grid.data[crt] = (crt++ % WIDTH) in x - 1..x + 1
    }

    l().forEach {
        when (it) {
            "noop" -> step()
            else -> {
                val (_, by) = it.split(" ")
                step()
                step()
                x += by.toInt()
            }
        }
    }
    submit { strength }

    grid.display()
}