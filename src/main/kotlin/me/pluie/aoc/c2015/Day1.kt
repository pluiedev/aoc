package me.pluie.aoc.c2015
import me.pluie.aoc.*

fun main() = challenge(2015, 1) {
    val conv = { c: Char -> if (c == '(') 1 else -1 }

    submit {
        m(conv).sum()
    }
    submit {
        runningFold(0) { acc, c -> acc + conv(c) }.indexOf(-1)
    }
}