package me.pluie.aoc.c2022
import me.pluie.aoc.*

fun main() = challenge(2022, 3) {
    fun priority(c: Char) = 1 + if (c in 'a' .. 'z') {
        c - 'a'
    } else {
        c - 'A' + 26

    }

    submit {
        l().fm {
            val (a, b) = it.splitInTwain()

            (a % b).m(::priority)
        }.sum()
    }
    submit {
        l().chunked(3).sumOf {
            val (a, b, c) = it
            priority((a % b % c).first())
        }
    }
}