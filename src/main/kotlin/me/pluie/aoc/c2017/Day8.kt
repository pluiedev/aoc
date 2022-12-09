package me.pluie.aoc.c2017

import me.pluie.aoc.*

fun main() = challenge(2017, 8) {
    data class Command(val name: String, val op: String, val by: Int, val cmpVar: String, val cmp: String, val cmpBy: Int)

    val cmds = l {
        it.match("([a-z]+) (dec|inc) (-?\\d+) if ([a-z]+) ([=!]=|[<>]=?) (-?\\d+)")
        { (name, op, by, cmpVar, cmp, cmpBy) ->
            Command(name, op, by.toInt(), cmpVar, cmp, cmpBy.toInt())
        }!!
    }

    val regs = mutableMapOf<String, Int>()
    cmds.associateByTo(regs, { it.name }) { 0 }
    cmds.associateByTo(regs, { it.cmpVar }) { 0 }

    val history = mutableSetOf<Int>()

    cmds.forEach {
        val cmpVar = regs[it.cmpVar]!!
        val crit = when (it.cmp) {
            ">=" -> cmpVar >= it.cmpBy
            ">" -> cmpVar > it.cmpBy
            "<=" -> cmpVar <= it.cmpBy
            "<" -> cmpVar < it.cmpBy
            "!=" -> cmpVar != it.cmpBy
            "==" -> cmpVar == it.cmpBy
            else -> error("impossible")
        }
        if (crit) {
            val new = regs[it.name]!! + when (it.op) {
                "dec" -> -it.by
                "inc" -> it.by
                else -> error("impossible")
            }
            history += new
            regs[it.name] = new
        }
    }
    submit { regs.maxOf { (_, v) -> v } }
    submit { history.toSortedSet().last() }
}