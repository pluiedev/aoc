package me.pluie.aoc.c2022
import me.pluie.aoc.*

fun main() = challenge(2022, 1) {
    val calories = blocks()
        .map { it.ints().sum() }
        .sortedDescending()
        .take(3)

    submit { calories.first() }
    submit { calories.sum() }
}