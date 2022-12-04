package me.pluie.aoc.c2015
import me.pluie.aoc.*

// TODO
fun main() = challenge(2015, 8) {
    val sum = l().sumOf { s ->
        val transformed = s
            .replace(Regex("\"(.*)\""), "$1")
            .replace(Regex("\\\\x([0-9a-f]{2})")) {
                "${it.groupValues[1].toInt(16).toChar()}"
            }
            .replace(Regex("\\\\\\\\"), "\\\\")
            .replace(Regex("\\\\\\\""), "\"")

        s.dbg().length - transformed.dbg().length
    }
    submit { sum }




}