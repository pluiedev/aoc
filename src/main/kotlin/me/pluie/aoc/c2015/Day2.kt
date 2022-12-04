package me.pluie.aoc.c2015
import me.pluie.aoc.*

fun main() = challenge(2015, 2) {
    val dims = l { it.split('x').ints() }

    submit {
        dims.sumOf { (h, w, l) ->
            val faces = listOf(h * w, w * l, l * h)
            2 * faces.sum() + faces.min()
        }
    }
    submit {
        dims.sumOf { dim ->
            dim.sorted().take(2).sum() * 2 + dim.product()
        }
    }
}