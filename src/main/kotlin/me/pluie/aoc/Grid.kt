package me.pluie.aoc


data class Grid<T>(
    val data: MutableList<T>,
    val width: Int,
    val height: Int,
): Iterable<Grid.Pos<T>> {
    operator fun get(x: Int, y: Int): Pos<T> = Pos(x, y, this)
    override fun iterator(): Iterator<Pos<T>> =
        (0 until width).toS().cartesian((0 until height).toS()).m { (x, y) ->
            Pos(x, y, this)
        }.iterator()

    data class Pos<T>(val x: Int, val y: Int, private val grid: Grid<T>) {
        var value: T
            get() = grid.data[y * grid.width + x]
            set(value) { grid.data[y * grid.width + x] = value }

        fun n(): Pos<T>? = norths().firstOrNull()
        fun s(): Pos<T>? = souths().firstOrNull()
        fun w(): Pos<T>? = wests().firstOrNull()
        fun e(): Pos<T>? = easts().firstOrNull()
        fun ne(): Pos<T>? = northeasts().firstOrNull()
        fun sw(): Pos<T>? = southwests().firstOrNull()
        fun nw(): Pos<T>? = northwests().firstOrNull()
        fun se(): Pos<T>? = southeasts().firstOrNull()

        fun norths(): Sequence<Pos<T>> = (y - 1 downTo 0).toS().m { grid[x, it] }
        fun souths(): Sequence<Pos<T>> = (y + 1 until grid.height).toS().m { grid[x, it] }
        fun wests(): Sequence<Pos<T>> = (x - 1 downTo 0).toS().m { grid[it, y] }
        fun easts(): Sequence<Pos<T>> = (x + 1 until grid.width).toS().m { grid[it, y] }
        fun northeasts(): Sequence<Pos<T>> =
            (y - 1 downTo 0).toS().zip((x + 1 until grid.width).toS(), grid::get)
        fun southwests(): Sequence<Pos<T>> =
            (y + 1 until grid.height).toS().zip((x - 1 downTo 0).toS(), grid::get)
        fun northwests(): Sequence<Pos<T>> =
            (y - 1 downTo 0).toS().zip((x - 1 downTo 0).toS(), grid::get)
        fun southeasts(): Sequence<Pos<T>> =
            (y + 1 until grid.height).toS().zip((x + 1 until grid.width).toS(), grid::get)

        fun cardinals(): Sequence<Sequence<Pos<T>>> = sequenceOf(norths(), easts(), souths(), wests())
        fun ordinals(): Sequence<Sequence<Pos<T>>> = sequenceOf(northeasts(), southeasts(), southwests(), northwests())
    }
}

fun CharSequence.grid(): Grid<Char> = grid { it }
fun <R> CharSequence.grid(map: (Char) -> R): Grid<R> {
    val width = l().first().length
    val height = l().count()

    return Grid(
        l().fm { it.toS().m(map) }.toMutableList(),
        width,
        height
    )
}