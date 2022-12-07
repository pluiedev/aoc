package me.pluie.aoc.c2020

import me.pluie.aoc.*

fun main() = challenge(2020, 1) {
    val nums = l(::int)

    submit {
        val (a, b) = nums.selfCartesian().find { (a, b) -> a + b == 2020 }!!
        a * b
    }
    submit {
        val (a, b, c) = nums.selfCartesian3().find { (a, b, c) -> a + b + c == 2020 }!!
        a * b * c
    }
}