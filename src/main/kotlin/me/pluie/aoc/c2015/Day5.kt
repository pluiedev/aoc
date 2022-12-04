package me.pluie.aoc.c2015
import me.pluie.aoc.*

fun main() = challenge(2015, 5) {

    submit {
        l { s ->
            s.windowed(2).fold(false) { b, t ->
                when (t) {
                    "ab", "cd", "pq", "xy" -> return@l false
                }
                b || t[0] == t[1]
            } && s.count { it in "aeiou" } >= 3
        }.count { it }
    }
    submit {
        l { s ->
            val pairs = s.windowed(2).withIndex().toList()

            pairs.any { pair ->
                pairs.subList(pair.index, pairs.size)
                    .any {
                        it.value == pair.value && it.index >= pair.index + 2
                    }
            } &&
            s.windowed(3).any { it[0] == it[2] }
        }.count { it }
    }
}