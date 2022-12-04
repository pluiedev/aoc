package me.pluie.aoc.c2022
import me.pluie.aoc.*

fun main() = challenge(2022, 4) {
    val pairs = l { it.csv().fm { s -> s.hyphens().ints() } }

    submit {
        pairs.count { (a, b, c, d) ->
            c in a..b && d in a..b || a in c..d && b in c..d
        }
    }
    submit {
        pairs.count { (a, b, c, d) -> ((a..b) % (c..d)).isNotEmpty() }
    }
}