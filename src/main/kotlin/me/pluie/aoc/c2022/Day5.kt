package me.pluie.aoc.c2022
import me.pluie.aoc.*

data class Ins(val cnt: Int, val from: Int, val to: Int)

fun main() = challenge(2022, 5) {
    val crates = MutableList(10) { mutableListOf<Char>() }

    val (cratesRaw, insRaw) = blocks().toL()

    cratesRaw.forEach {
        val c = it.chunked(4).withIndex()
        c.forEach { s ->
            if (s.value.isNotBlank() && s.value[0] == '[') {
                crates[s.index].add(0, s.value[1])
            }
        }
    }

    val ins = insRaw.map {
        val (cnt, from, to) = Regex("move (\\d+) from (\\d+) to (\\d+)").find(it)!!.destructured
        Ins(cnt.toInt(), from.toInt() - 1, to.toInt() - 1)
    }.toMutableList()


    submit {
        val c = crates.copy()
        for (i in ins) {
            c[i.to].addAll(c[i.from].dropLast(i.cnt).asReversed())
        }
        c.mapNotNull { it.firstOrNull() }.join()
    }
    submit {
        val c = crates.copy()
        for (i in ins) {
            c[i.to].addAll(c[i.from].dropLast(i.cnt))
        }
        c.mapNotNull { it.firstOrNull() }.join()
    }
}