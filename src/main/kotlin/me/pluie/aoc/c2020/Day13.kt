package me.pluie.aoc.c2020

import me.pluie.aoc.*


fun main() = challenge(2020, 13) {
    val (numRaw, busesRaw) = l().toL()
    val num = numRaw.int()
    val buses = busesRaw.csv().filter { it != "x" }.ints().toList()

    submit {
        val (x, y) = buses.map { it to it - num % it }.minBy { it.second }
        x * y
    }

    // _Yes_, I know you can use the Chinese Remainder Theorem to calculate
    // the correct value x that satisfies this modular equation:
    //
    // x
    // ≡ 0 (mod b_0)
    // ≡ b_1 - 1 (mod b_1)
    // ≡ b_2 - 2 (mod b_2)
    // ...
    // ≡ b_n - n (mod b_n)
    //
    // where b_n is the nth bus that is not ignored.
    //
    // But I'm just way too lazy to do that. Here's a bruteforce scanning method
    // that works just fine:

    submit {
        (0..Int.MAX_VALUE).map {  }
    }
}