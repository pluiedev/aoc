package me.pluie.aoc.c2015
import me.pluie.aoc.*

fun main() = challenge(2015, 3) {
    val strings = lineSequence()

    submit {
        strings.map {
            it.windowed(2)
                .fold(0 to false) { (vowels, doubleConsonants), s ->
                    when (s) {
//                        "ab", "cd", "pq", "xy" ->
                    }
                    vowels to doubleConsonants

                }

        }.count()
    }
}