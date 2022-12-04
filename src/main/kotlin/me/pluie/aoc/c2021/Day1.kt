package me.pluie.aoc.c2021
import me.pluie.aoc.*

fun main() = challenge(2021, 1) {
    val depths = l().ints()

    submit {
        depths
            .windowed(2)
            .count { (a, b) -> a < b  }
    }
    submit {
        depths
            .windowed(4)
            .count { (a, _, _, d) -> a < d  }
    }
}