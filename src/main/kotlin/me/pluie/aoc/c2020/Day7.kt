package me.pluie.aoc.c2020

import me.pluie.aoc.*

fun main() = challenge(2020, 7) {
    data class Bag(val bags: Map<String, Bag>, val subBags: List<Pair<Int, String>>) {
        val hasShinyGold: Boolean by lazy {
            subBags.any { (_, type) ->
                type == "shiny gold" || bags[type]!!.hasShinyGold
            }
        }
        val size: Int by lazy {
            1 + subBags.sumOf { (cnt, type) -> cnt * bags[type]!!.size }
        }
    }

    val bags = mutableMapOf<String, Bag>()
    l().associateTo(bags) {
        val (type, listRaw) = it.match("(.+) bags contain (no other bags|.+)\\.")!!
        val list = if (listRaw == "no other bags")
            emptyList()
        else
            listRaw.splitToSequence(", ").m { s ->
                s.match("(\\d+) (.+) bags?") { (n, subtype) ->
                    n.int() to subtype
                }!!
            }.toL()

        type to Bag(bags, list)
    }

    submit { bags.values.count { it.hasShinyGold } }
    submit { bags["shiny gold"]!!.size - 1 }
}