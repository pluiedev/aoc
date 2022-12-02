package me.pluie.aoc.c2019

import me.pluie.aoc.*

fun main() = challenge(2019, 4) {
    val (f, t) = splitToSequence("-").ints().toList()

    // make sure it's in range and 6 digits long.
    val from = f.coerceAtLeast(100_000)
    val to = t.coerceAtMost(999_999)

    submit {
        (from..to).count {
            var doubleDigits = false
            var mask = 1

            // two digits in a pair; if first digit is greater than second,
            // the digits must be decreasing at this point - abort.
            while (mask < 100_000) {
                val num = it / mask % 100
                val d1 = num / 10
                val d2 = num % 10

                if (d1 > d2) return@count false
                else if (d1 == d2) doubleDigits = true

                mask *= 10
            }
            doubleDigits
        }
    }
    submit {
        (from..to).count {
            var doubleDigits = false
            var mask = 1
            var repeatingDigit = 0
            var repeat = 0

            // two digits in a pair; if first digit is greater than second,
            // the digits must be decreasing at this point - abort.
            while (mask < 100_000) {
                val num = it / mask % 100
                val d1 = num / 10
                val d2 = num % 10

                if (d1 > d2) {
                    return@count false
                }
                else if (d1 == d2) {
                    if (repeat > 0 && repeatingDigit == d2) {
                        repeat++
                    } else {
                        repeatingDigit = d2
                        repeat = 2
                    }
                }
                else {
                    // repeat streak broken
                    if (repeat == 2) doubleDigits = true
                    repeat = 0
                }

                mask *= 10
            }
            doubleDigits//.also { b->if(b)it.dbg() }
        }
    }
}