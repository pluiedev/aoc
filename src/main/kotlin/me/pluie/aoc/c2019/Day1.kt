package me.pluie.aoc.c2019

import me.pluie.aoc.*

fun main() = challenge(2019, 1) {
    submit { lineSequence().ints().sumOf { it / 3 - 2 } }
    submit {
        lineSequence().ints().sumOf {
            var sum = 0
            var mass = it
            while (true) {
                val fuel = mass / 3 - 2
                if (fuel <= 0) break

                sum += fuel
                mass = fuel
            }
            sum
        }
    }
}