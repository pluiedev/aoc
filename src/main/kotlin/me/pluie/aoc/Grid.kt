package me.pluie.aoc

import java.util.*
import kotlin.math.max
import kotlin.math.min

interface Grid<T>: Iterable<GridPos<T>> {
    val xRange: IntRange
    val yRange: IntRange

    operator fun get(x: Int, y: Int): T
    operator fun set(x: Int, y: Int, value: T)

    fun <P: AbstractPos<P>> fill(p1: P, p2: P, value: T) {
        for (y in min(p1.y, p2.y)..max(p1.y, p2.y))
            for (x in min(p1.x, p2.x)..max(p1.x, p2.x))
                this[x, y] = value
    }

    fun pos(x: Int, y: Int): GridPos<T> = GridPos(x, y, this)
    fun origin(): GridPos<T> = GridPos(0, 0, this)

    override fun iterator(): Iterator<GridPos<T>> =
        yRange.toS().cartesian(xRange.toS()).m { (x, y) ->
            GridPos(x, y, this)
        }.iterator()

    fun display(map: (GridPos<T>) -> String) {
        for (y in yRange) {
            for (x in xRange) {
                print(map(GridPos(x, y, this)))
            }
            println()
        }
    }

    fun index(x: Int, y: Int): Int = (y - yRange.first) * (xRange.last - xRange.first + 1) + (x - xRange.first)
}

data class BitGrid(
    override val xRange: IntRange,
    override val yRange: IntRange,
    val data: BitSet = BitSet((xRange.last - xRange.first + 1) * (yRange.last - yRange.first + 1))
): Grid<Boolean> {
    override fun get(x: Int, y: Int) = data.get(index(x, y))
    override fun set(x: Int, y: Int, value: Boolean) {
        data.set(index(x, y), value)
    }
    override fun <P: AbstractPos<P>> fill(p1: P, p2: P, value: Boolean) {
        for (y in min(p1.y, p2.y)..max(p1.y, p2.y)) {
            setRow(min(p1.x, p2.x)..max(p1.x, p2.x), y, value)
        }
    }
    fun setRow(x: IntRange, y: Int, value: Boolean) {
        data.set(index(x.first, y), index(x.last, y) + 1, value)
    }

    fun cardinality(): Int = data.cardinality()

    companion object {
        fun fromPoints(points: Iterable<Pos>): BitGrid {
            val (xMin, xMax) = points.m { it.x }.minmax() ?: return empty()
            val (yMin, yMax) = points.m { it.y }.minmax() ?: return empty()
            val grid = BitGrid(xMin .. xMax, yMin .. yMax)
            for (p in points) {
                grid[p.x, p.y] = true
            }
            return grid
        }
        fun empty(): BitGrid = BitGrid(IntRange.EMPTY, IntRange.EMPTY)
    }
}

data class ListGrid<T>(
    override val xRange: IntRange,
    override val yRange: IntRange,
    val data: MutableList<T>,
): Grid<T> {
    override operator fun get(x: Int, y: Int): T = data[index(x, y)]
    override operator fun set(x: Int, y: Int, value: T) {
        data[index(x, y)] = value
    }
}

data class SetGrid(
    override val xRange: IntRange,
    override val yRange: IntRange,
    val data: MutableSet<Int> = mutableSetOf()
): Grid<Boolean> {
    override operator fun get(x: Int, y: Int): Boolean = index(x, y) in data
    override operator fun set(x: Int, y: Int, value: Boolean) {
        val index = index(x, y)
        if (value)
            data += index
        else
            data -= index
    }
}

fun CharSequence.grid(offset: Pos = Pos.origin()): ListGrid<Char> = grid(offset) { it }
fun <R> CharSequence.grid(offset: Pos = Pos.origin(), map: (Char) -> R): ListGrid<R> {
    val width = l().first().length
    val height = l().count()

    return ListGrid(
        offset.x until width + offset.x,
        offset.y until height + offset.y,
        l().fm { it.toS().m(map) }.toMutableList(),
    )
}

fun CharSequence.bitGrid(offset: Pos = Pos.origin(), map: (Char) -> Boolean): BitGrid {
    val width = l().first().length
    val height = l().count()
    val set = BitSet()

    l().fm { it.asSequence() }.forEachIndexed { index, c ->
        set[index] = map(c)
    }
    return BitGrid(
        offset.x until width + offset.x,
        offset.y until height + offset.y,
        set
    )
}

fun CharSequence.setGrid(offset: Pos = Pos.origin(), map: (Char) -> Boolean): SetGrid {
    val width = l().first().length
    val height = l().count()
    val set = mutableSetOf<Int>()

    l().fm { it.asSequence() }.forEachIndexed { index, c ->
        if (map(c)) set += index
    }
    return SetGrid(
        offset.x until width + offset.x,
        offset.y until height + offset.y,
        set
    )
}

// extensions

fun Grid<Boolean>.display() = display {
    if (it.value) "██" else "░░"
}