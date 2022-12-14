package me.pluie.aoc

import java.util.*
import kotlin.math.*
import kotlin.streams.asSequence

operator fun <T> Iterable<T>.rem(other: Iterable<T>): Set<T> = this intersect other.toSet()

inline fun <reified T> T.dbg() = also(::println)
inline fun <reified T> Iterable<T>.dbgAll() = forEach { it.dbg() }
inline fun <reified T> Sequence<T>.dbgAll() = forEach { it.dbg() }

@Suppress("NOTHING_TO_INLINE")
inline fun p(vararg v: Any) = v.forEach { it.dbg() }

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> List<T>.component6(): T {
    return get(5)
}

infix fun UShort.shl(by: UShort) = (toInt() shl by.toInt()).toUShort()
infix fun UShort.shr(by: UShort) = (toInt() shr by.toInt()).toUShort()

fun <T> List<T>.splitAt(idx: Int): Pair<List<T>, List<T>> = subList(0, idx) to subList(idx, size)
fun <T> List<T>.splitInTwain(): Pair<List<T>, List<T>> = splitAt(size / 2)

// pair bullshit

fun <T> Pair<T, T>.s(): Sequence<T> = listOf(first, second).asSequence()
inline fun <T, R> Pair<T, T>.m(f: (T) -> R): Pair<R, R> = f(first) to f(second)

fun <T> Triple<T, T, T>.s(): Sequence<T> = listOf(first, second, third).asSequence()
inline fun <T, R> Triple<T, T, T>.m(f: (T) -> R): Triple<R, R, R> = Triple(f(first), f(second), f(third))

fun BitSet.toS() = stream().asSequence()
// java sucks
fun BitSet.copy() = clone() as BitSet

fun <T> List<T>.copy() = toMutableList()
@JvmName("copy2")
fun <T> List<List<T>>.copy() = map { it.toMutableList() }.toMutableList()

fun <T> MutableList<T>.drain(): MutableList<T> {
    val old = toMutableList()
    clear()
    return old
}

fun impossible(): Nothing = error("impossible")