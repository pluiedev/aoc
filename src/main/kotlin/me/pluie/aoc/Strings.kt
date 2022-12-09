package me.pluie.aoc


fun CharSequence.toS() = asSequence()

fun <R> CharSequence.m(f: (Char) -> R) = map(f)

operator fun String.rem(other: String): Set<Char> = this.toSet() intersect other.toSet()
operator fun Set<Char>.rem(other: String): Set<Char> = this intersect other.toSet()

fun CharSequence.occurrences() = groupingBy { it }.eachCount()

fun CharSequence.splitAt(idx: Int): Pair<String, String> = substring(0..idx) to substring(idx)
fun CharSequence.splitInTwain(): Pair<String, String> = splitAt(length / 2)

fun CharSequence.split2(vararg delimiters: String): Pair<String, String>
        = split(*delimiters, limit = 2).let { it[0] to it[1] }
fun <R> CharSequence.split2(vararg delimiters: String, f: (String) -> R): Pair<R, R>
        = split(*delimiters, limit = 2).let { f(it[0]) to f(it[1]) }

fun Iterable<Char>.join() = joinToString("")
fun Sequence<Char>.join() = joinToString("")
@JvmName("joinString")
fun Iterable<String>.join() = joinToString("")
@JvmName("joinString")
fun Sequence<String>.join() = joinToString("")