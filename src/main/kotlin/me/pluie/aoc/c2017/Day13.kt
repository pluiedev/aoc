package me.pluie.aoc.c2017

import me.pluie.aoc.*

// TODO
fun main() = challenge(2017, 13) {
    val pos = l {
        it.split2(": ") { s -> s.int() }
    }.toMap()

    val length = pos.keys.max() + 1
    val ranges = List(length) { pos[it] ?: 0 }

    val state = MutableList(length) { 0 }
    fun simulation(delay: Int): Int {
        state.fill(0)

        for (u in 0 until delay) {
            for (i in state.indices) {
                val period = (ranges[i] - 1) * 2
                if (ranges[i] != 0) state[i] = (state[i] + 1) % period
            }
        }
        return state.asSequence().mapIndexed { ps, s ->
            val v = if (s == 0) (delay + ps) * ranges[ps] else 0

            for (i in state.indices) {
                val period = (ranges[i] - 1) * 2
                if (ranges[i] != 0) state[i] = (state[i] + 1) % period
            }
            v
        }.sum()
    }

    submit { simulation(0) }

//    submit {
//        (0..Int.MAX_VALUE).find { simulation(it) == 0 }!!
//    }
}