package me.pluie.aoc

import java.util.*

interface Grid<T>: Iterable<Pos<T>> {
    val width: Int
    val height: Int

    operator fun get(x: Int, y: Int): T
    operator fun set(x: Int, y: Int, value: T)

    override fun iterator(): Iterator<Pos<T>> =
        (0 until width).toS().cartesian((0 until height).toS()).m { (x, y) ->
            Pos(x, y, this)
        }.iterator()
}

data class Pos<T>(val x: Int, val y: Int, val grid: Grid<T>) {
    var value: T
        get() = grid[x, y]
        set(value) { grid[x, y] = value }

    override fun toString(): String = "($x, $y)"

    fun n(): Pos<T>? = norths().firstOrNull()
    fun s(): Pos<T>? = souths().firstOrNull()
    fun w(): Pos<T>? = wests().firstOrNull()
    fun e(): Pos<T>? = easts().firstOrNull()
    fun ne(): Pos<T>? = northeasts().firstOrNull()
    fun sw(): Pos<T>? = southwests().firstOrNull()
    fun nw(): Pos<T>? = northwests().firstOrNull()
    fun se(): Pos<T>? = southeasts().firstOrNull()

    fun norths(): Sequence<Pos<T>> = (y - 1 downTo 0).toS().m { Pos(x, it, grid) }
    fun souths(): Sequence<Pos<T>> = (y + 1 until grid.height).toS().m { Pos(x, it, grid) }
    fun wests(): Sequence<Pos<T>> = (x - 1 downTo 0).toS().m { Pos(it, y, grid) }
    fun easts(): Sequence<Pos<T>> = (x + 1 until grid.width).toS().m { Pos(it, y, grid) }
    fun northeasts(): Sequence<Pos<T>> =
        (x + 1 until grid.width).toS().zip((y - 1 downTo 0).toS()) { x, y -> Pos(x, y, grid) }
    fun southwests(): Sequence<Pos<T>> =
        (x - 1 downTo 0).toS().zip((y + 1 until grid.height).toS()) { x, y -> Pos(x, y, grid) }
    fun northwests(): Sequence<Pos<T>> =
        (x - 1 downTo 0).toS().zip((y - 1 downTo 0).toS()) { x, y -> Pos(x, y, grid) }
    fun southeasts(): Sequence<Pos<T>> =
        (x + 1 until grid.width).toS().zip((y + 1 until grid.height).toS()) { x, y -> Pos(x, y, grid) }

    fun cardinals(): Sequence<Sequence<Pos<T>>> = sequenceOf(norths(), easts(), souths(), wests())
    fun ordinals(): Sequence<Sequence<Pos<T>>> = sequenceOf(northeasts(), southeasts(), southwests(), northwests())

    fun cardinalNeighbors(): Sequence<Pos<T>> = sequenceOf(n(), e(), s(), w()).filterNotNull()
    fun ordinalNeighbors(): Sequence<Pos<T>> = sequenceOf(ne(), se(), sw(), nw()).filterNotNull()
    fun neighbors(): Sequence<Pos<T>> = sequenceOf(n(), ne(), e(), se(), s(), sw(), w(), nw()).filterNotNull()
}

data class BitGrid(
    override val width: Int,
    override val height: Int,
    val data: BitSet = BitSet(width * height)
): Grid<Boolean> {
    override fun get(x: Int, y: Int) = data.get(y * width + x)
    override fun set(x: Int, y: Int, value: Boolean) {
        data.set(y * width + x, value)
    }

    fun cardinality(): Int = data.cardinality()
}

data class ListGrid<T>(
    override val width: Int,
    override val height: Int,
    val data: MutableList<T>,
): Grid<T> {
    override operator fun get(x: Int, y: Int): T = data[y * width + x]
    override operator fun set(x: Int, y: Int, value: T) {
        data[y * width + x] = value
    }
}

fun CharSequence.grid(): ListGrid<Char> = grid { it }
fun <R> CharSequence.grid(map: (Char) -> R): ListGrid<R> {
    val width = l().first().length
    val height = l().count()

    return ListGrid(
        width,
        height,
        l().fm { it.toS().m(map) }.toMutableList(),
    )
}

fun CharSequence.bitGrid(map: (Char) -> Boolean): BitGrid {
    val width = l().first().length
    val height = l().count()
    val set = BitSet()

    l().fm { it.asSequence() }.forEachIndexed { index, c ->
        set[index] = map(c)
    }
    return BitGrid(width, height, set)
}