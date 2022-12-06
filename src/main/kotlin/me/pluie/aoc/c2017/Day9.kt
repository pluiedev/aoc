package me.pluie.aoc.c2017

import me.pluie.aoc.*

fun main() = challenge(2017, 9) {
    data class State(
        val score: Int = 0,
        val garbage: Int = 0,
        val depth: Int = 0,
        val inGarbage: Boolean = false,
        val ignored: Boolean = false
    )

    val (score, garbage) = fold(State()) { s, c ->
        if (s.ignored) s.copy(ignored = false)
        else if (c == '!') s.copy(ignored = true)
        else if (s.inGarbage)
            if (c == '>') s.copy(inGarbage = false)
            else s.copy(garbage = s.garbage + 1)
        else
            when (c) {
                '{' -> s.copy(depth = s.depth + 1)
                '}' -> s.copy(score = s.score + s.depth, depth = s.depth - 1)
                '<' -> s.copy(inGarbage = true)
                else -> s
            }
    }

    submit { score }
    submit { garbage }
}