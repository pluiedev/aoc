package me.pluie.aoc

fun Iterable<Float>.product(): Float = reduce(Float::times)
fun Iterable<Double>.product(): Double = reduce(Double::times)
fun Iterable<Long>.product(): Long = reduce(Long::times)
fun Iterable<Int>.product(): Int = reduce(Int::times)

fun Sequence<Float>.product(): Float = reduce(Float::times)
fun Sequence<Double>.product(): Double = reduce(Double::times)
fun Sequence<Long>.product(): Long = reduce(Long::times)
fun Sequence<Int>.product(): Int = reduce(Int::times)

fun <T> Iterable<T>.productOf(f: (T) -> Float): Float = fold(1.0f) { a, v -> a * f(v) }
fun <T> Iterable<T>.productOf(f: (T) -> Double): Double = fold(1.0) { a, v -> a * f(v) }
fun <T> Iterable<T>.productOf(f: (T) -> Long): Long = fold(1L) { a, v -> a * f(v) }
fun <T> Iterable<T>.productOf(f: (T) -> Int): Int = fold(1) { a, v -> a * f(v) }

fun <T> Sequence<T>.productOf(f: (T) -> Float): Float = fold(1.0f) { a, v -> a * f(v) }
fun <T> Sequence<T>.productOf(f: (T) -> Double): Double = fold(1.0) { a, v -> a * f(v) }
fun <T> Sequence<T>.productOf(f: (T) -> Long): Long = fold(1L) { a, v -> a * f(v) }
fun <T> Sequence<T>.productOf(f: (T) -> Int): Int = fold(1) { a, v -> a * f(v) }