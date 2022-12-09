package me.pluie.aoc.c2022
import me.pluie.aoc.*

sealed class Node {
    abstract val name: String
    abstract val fs: Map<String, MutableList<Node>>

    val totalSize: Int by lazy { calculateSize() }

    protected abstract fun calculateSize(): Int

    data class File(
        override val name: String,
        val size: Int,
        override val fs: Map<String, MutableList<Node>>
    ): Node() {
        override fun calculateSize() = size
    }
    data class Directory(
        override val name: String,
        override val fs: Map<String, MutableList<Node>>
    ): Node() {
        override fun calculateSize() = fs[name.dbg()]!!.sumOf { it.totalSize }
    }
}

fun main() = challenge(2022, 7) {
    val fs = mutableMapOf<String, MutableList<Node>>()
    val cwd = mutableListOf<String>()

    l().forEach { l ->
        val cmdMatch = l.match("\\$ (?:(cd) (/|[a-z]+|\\.\\.)|ls)")
        if (cmdMatch != null) {
            val (cd, path) = cmdMatch

            if (cd != "") when(path) {
                ".." -> cwd.removeLast()
                "/" -> cwd.clear()
                else -> cwd.add(path)
            }
        } else {
            val (typeOrSize, name) = l.split(" ", limit = 2)
            val path = cwd.joinToString("/", prefix = "/")
            val list = fs.getOrPut(path) { mutableListOf() }

            list.add(if (typeOrSize == "dir") {
                fs[name] = mutableListOf()

                Node.Directory(cwd.joinToString("/", postfix = "/$name"), fs)
            } else {
                Node.File(name, typeOrSize.int(), fs)
            })
        }
    }

    val sizes = fs.values.m { v -> v.sumOf { it.totalSize } }

    submit { sizes.filter { it <= 100000L }.sum() }

    val unusedSpace = 70000000 - fs["/"]!!.sumOf { it.totalSize }
    val requiredSpace = 30000000 - unusedSpace
    submit { sizes.filter { it >= requiredSpace }.min() }
}