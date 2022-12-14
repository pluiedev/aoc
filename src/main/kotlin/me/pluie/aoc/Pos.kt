package me.pluie.aoc

import kotlin.math.abs
import kotlin.math.sqrt

interface AbstractPos<Self: AbstractPos<Self>> {
    val x: Int
    val y: Int
    val xRange: IntRange
    val yRange: IntRange

    fun new(x: Int, y: Int): Self

    fun move(x: Int, y: Int): Self? {
        val newX = this.x + x
        val newY = this.y + y
        return if (newX in xRange && newY in yRange)
            new(x = newX, y = newY)
        else
            null
    }
    fun move(offset: Pair<Int, Int>): Self? = move(offset.first, offset.second)

    fun move(dir: Direction, by: Int = 1): Self? = when (dir) {
        Direction.North -> n(by)
        Direction.East -> e(by)
        Direction.South -> s(by)
        Direction.West -> w(by)
    }

    fun n(by: Int = 1): Self? = norths().take(by).lastOrNull()
    fun s(by: Int = 1): Self? = souths().take(by).lastOrNull()
    fun w(by: Int = 1): Self? = wests().take(by).lastOrNull()
    fun e(by: Int = 1): Self? = easts().take(by).lastOrNull()
    fun ne(by: Int = 1): Self? = northeasts().take(by).lastOrNull()
    fun sw(by: Int = 1): Self? = southwests().take(by).lastOrNull()
    fun nw(by: Int = 1): Self? = northwests().take(by).lastOrNull()
    fun se(by: Int = 1): Self? = southeasts().take(by).lastOrNull()

    fun norths(): Sequence<Self> = (y - 1 downTo yRange.first).toS().m { new(x, it) }
    fun souths(): Sequence<Self> = (y + 1..yRange.last).toS().m { new(x, it) }
    fun wests(): Sequence<Self> = (x - 1 downTo xRange.first).toS().m { new(it, y) }
    fun easts(): Sequence<Self> = (x + 1..xRange.last).toS().m { new(it, y) }
    fun northeasts(): Sequence<Self> =
        (x + 1..xRange.last).toS().zip((y - 1 downTo yRange.first).toS(), ::new)
    fun southwests(): Sequence<Self> =
        (x - 1 downTo xRange.first).toS().zip((y + 1..yRange.last).toS(), ::new)
    fun northwests(): Sequence<Self> =
        (x - 1 downTo xRange.first).toS().zip((y - 1 downTo yRange.first).toS(), ::new)
    fun southeasts(): Sequence<Self> =
        (x + 1..xRange.last).toS().zip((y + 1..yRange.last).toS(), ::new)

    fun cardinals(): Sequence<Sequence<Self>> = sequenceOf(norths(), easts(), souths(), wests())
    fun ordinals(): Sequence<Sequence<Self>> = sequenceOf(northeasts(), southeasts(), southwests(), northwests())

    fun cardinalNeighbors(): Sequence<Self> = sequenceOf(n(), e(), s(), w()).filterNotNull()
    fun ordinalNeighbors(): Sequence<Self> = sequenceOf(ne(), se(), sw(), nw()).filterNotNull()
    fun neighbors(): Sequence<Self> = sequenceOf(n(), ne(), e(), se(), s(), sw(), w(), nw()).filterNotNull()

    fun manhattanDistance(o: Self): Int = abs(x - o.x) + abs(y - o.y)
    fun euclideanDistance(o: Self): Float = sqrt(
        (x.toFloat() - o.x.toFloat()).let { it * it } +
                (y.toFloat() - o.y.toFloat()).let { it * it }
    )
}

data class Pos(override val x: Int, override val y: Int): AbstractPos<Pos> {
    override val xRange = Int.MIN_VALUE..Int.MAX_VALUE
    override val yRange = Int.MIN_VALUE..Int.MAX_VALUE

    override fun new(x: Int, y: Int) = copy(x = x, y = y)
    override fun toString() = "($x, $y)"

    companion object {
        fun origin() = Pos(0, 0)
    }
}

data class GridPos<T>(override val x: Int, override val y: Int, val grid: Grid<T>): AbstractPos<GridPos<T>> {
    override val xRange = grid.xRange
    override val yRange = grid.yRange

    var value: T
        get() = grid[x, y]
        set(value) { grid[x, y] = value }

    override fun new(x: Int, y: Int) = copy(x = x, y = y)
    override fun toString() = "($x, $y)"
}

enum class Direction {
    North, East, South, West;

    fun cw() = when(this) {
        North -> East
        East -> South
        South -> West
        West -> North
    }
    fun ccw() = when(this) {
        North -> West
        East -> North
        South -> East
        West -> South
    }
}