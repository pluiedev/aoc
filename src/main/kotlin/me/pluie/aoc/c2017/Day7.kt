package me.pluie.aoc.c2017

import me.pluie.aoc.*

fun main() = challenge(2017, 7) {
    // TODO
    data class Program(
        val name: String,
        val weight: Int,
        var parent: Program? = null,
        val children: MutableList<Program> = mutableListOf()
    ) {
        val totalWeight: Int by lazy { weight + children.sumOf { it.totalWeight } }

        fun calcRequiredBalance(): Int {
            val expected = children.firstOrNull()?.totalWeight ?: return 0
            val rulebreaker = children.find { it.totalWeight != expected } ?: return 0
            val calibrated = expected - rulebreaker.children.sumOf { it.totalWeight }
            return calibrated
        }
    }


    val nodes = mutableMapOf<String, Program>()
    val structure = l {
        val result = Regex("([a-z]+) \\((\\d+)\\)(?: -> (.+))?")
            .find(it)!!
        val (name, weight) = result.destructured
        val children = result.groupValues.getOrNull(3)?.spacedCsv()?.toL() ?: listOf()

        nodes[name] = Program(name, weight.toInt())
        name to children.filter { s -> s.isNotBlank() }
    }.toMap()

    structure.forEach { (name, children) ->
        val thisNode = nodes[name]!!
        children.mapTo(thisNode.children) { child ->
            nodes[child]!!.also { it.parent = thisNode }
        }
    }

    var top = nodes.entries.first().value
    while (top.parent != null) top = top.parent!!

    submit { top.name }

    submit {
        nodes.values.map(Program::calcRequiredBalance).find { it > 0 }!!.toString()
    }



}