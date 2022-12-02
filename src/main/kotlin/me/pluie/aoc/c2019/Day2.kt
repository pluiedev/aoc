package me.pluie.aoc.c2019

import me.pluie.aoc.*

fun main() = challenge(2019, 2) {
    val input = commaList().ints()

    fun evaluate(noun: Int, verb: Int): Int {
        var pc = 0
        val program = input.toMutableList()
        program[1] = noun
        program[2] = verb
        fun read() = program[pc].also { pc++ }

        while (true) {
            when (read()) {
                1 -> {
                    val op1 = read()
                    val op2 = read()
                    val dst = read()

                    program[dst] = program[op1] + program[op2]
                }
                2 -> {
                    val op1 = read()
                    val op2 = read()
                    val dst = read()
                    program[dst] = program[op1] * program[op2]
                }
                99 -> break
                else -> error("impossible")
            }
        }
        return program[0]
    }

    submit { evaluate(12, 2) }
    submit {
        for (noun in 0..99)
            for (verb in 0..99)
                if (evaluate(noun, verb) == 19690720)
                    return@submit 100 * noun + verb
        error("didn't find any for some reason")
    }
}