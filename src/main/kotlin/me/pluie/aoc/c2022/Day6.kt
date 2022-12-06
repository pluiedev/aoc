package me.pluie.aoc.c2022
import me.pluie.aoc.*

fun main() = challenge(2022, 6) {
    listOf(4, 14).map { it + windowed(it).indexOfFirst { s -> s.toSet().size == it } }.massSubmit()
}