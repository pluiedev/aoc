package me.pluie.aoc.c2020

import me.pluie.aoc.*

fun main() = challenge(2020, 8) {
    data class Cmd(var op: String, val by: Int)

    fun simulate(cmds: List<Cmd>): Pair<Int, Boolean> {
        var acc = 0
        var pc = 0

        val seen = mutableSetOf<Int>()
        while (pc !in seen) {
            seen += pc
            val (op, by) = cmds[pc]
            when (op) {
                "nop" -> pc++
                "acc" -> { acc += by; pc++ }
                "jmp" -> pc += by
            }

            if (pc > cmds.size) break
            else if (pc == cmds.size) return acc to true
        }
        return acc to false
    }


    val cmds = l {
        val (op, by) = it.split(" ")
        Cmd(op, by.toInt())
    }.toMutableList()


    submit { simulate(cmds).first }

    submit {
        for (i in cmds.indices) {
            val oldOp = cmds[i].op
            cmds[i].op = when (oldOp) {
                "jmp" -> "nop"
                "nop" -> "jmp"
                else -> continue
            }
            val (acc, success) = simulate(cmds)
            if (success) return@submit acc
            cmds[i].op = oldOp
        }
    }
}