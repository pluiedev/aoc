package me.pluie.aoc.c2020

import me.pluie.aoc.*
import java.util.*

// TODO this actually doesn't work but, eh
fun main() = challenge(2020, 11) {
    val mask = bitGrid { it == 'L' }
    val cells = BitGrid(mask.width, mask.height)

    var lastCells: BitSet? = null

    while (cells.data != lastCells) {
        lastCells = cells.data.copy()

        mask.toS()
            .filter { it.value }
            .m { m ->
                val p = m.copy(grid = cells)
                p to p.neighbors().count { it.value }
            }
            .toL()
            .forEach { (n, cnt) ->
                if (cnt >= 4) n.value = false
                else if (!n.value && cnt == 0) n.value = true
            }
    }
    submit { cells.cardinality() }
}