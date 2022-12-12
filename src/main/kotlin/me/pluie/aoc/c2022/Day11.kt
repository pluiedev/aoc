package me.pluie.aoc.c2022
import me.pluie.aoc.*

fun main() = challenge(2022, 11) {
    data class Monkey(
        val items: MutableList<Long>,
        val op: (Long) -> Long,
        val test: Long,
        val tr: Int,
        val fl: Int,
        var inspections: Long = 0
    )

    val ms = matchAll("""
        Monkey \d+:
          Starting items: (.+)
          Operation: new = old ([*+]) (.+)
          Test: divisible by (\d+)
            If true: throw to monkey (\d+)
            If false: throw to monkey (\d+)
    """.trimIndent()){ (items, op, by, test, tr, fl) ->
        val b = when (by) { "old" -> null; else -> by.long() }
        Monkey(
            items.spacedCsv(::long).toMutableList(),
            when (op) {
                "*" -> { a -> a * (b ?: a) }
                "+" -> { a -> a + (b ?: a) }
                else -> impossible()
            },
            test.long(),
            tr.int(),
            fl.int()
        )
    }.toL()

    fun calcMonkeyBusiness(rounds: Int, relief: Boolean): Long {
        val monkeys = MutableList(ms.size) {
            val old = ms[it]
            old.copy(items = old.items.copy())
        }
        val prodOfAllTests = monkeys.productOf { it.test }

        for (i in 0 until rounds) {
            for (m in monkeys) {
                for (item in m.items.drain()) {
                    var worry = m.op(item) % prodOfAllTests
                    if (relief) worry /= 3

                    val idx = if (worry % m.test == 0L) m.tr else m.fl
                    monkeys[idx].items.add(worry)
                    m.inspections++
                }
            }
        }
        monkeys.sortByDescending { it.inspections }
        return monkeys.take(2).productOf { it.inspections }
    }

    submit { calcMonkeyBusiness( 20, true) }
    submit { calcMonkeyBusiness(10000, false) }
}