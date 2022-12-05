package me.pluie.aoc.c2022
import me.pluie.aoc.*

fun main() = challenge(2022, 2) {
    // good luck figuring out how this ultra-minimized,
    // totally-after-the-fact solution works!
    //
    // i used a big when case for my first attempt and
    // IDEA unfortunately got rid of it cos its undo history was for some reason
    // really limited.
    //
    // oh well! so it goes.

    // { it }

    submit{l().sumOf{it[2]-'W'+((it[2]-it[0]).mod(3)-1).mod(3)*3}}
    submit{l().sumOf{3*it[2].code-263+(it[0]-'A'+(it[2]-'V').mod(3)).mod(3)}}
}