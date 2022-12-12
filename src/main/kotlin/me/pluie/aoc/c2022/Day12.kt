package me.pluie.aoc.c2022

import me.pluie.aoc.*
import org.jgrapht.alg.shortestpath.BFSShortestPath
import org.jgrapht.graph.DefaultWeightedEdge
import org.jgrapht.graph.SimpleDirectedWeightedGraph

inline fun <V, reified E> SimpleDirectedWeightedGraph() =
    SimpleDirectedWeightedGraph<V, E>(E::class.java)

fun main() = challenge(2022, 12) {
    data class Space(val c: Char) {
        val height: Int = when (c) {
            'S' -> 'a'
            'E' -> 'z'
            else -> c
        } - 'a'
    }

    val graph = SimpleDirectedWeightedGraph<GridPos<Space>, DefaultWeightedEdge>()
    val grid = grid { Space(it) }

    grid.forEach { graph.addVertex(it) }
    val sink = graph.vertexSet().find { it.value.c == 'E' }!!

    for (v in grid) {
        for (n in v.cardinalNeighbors()) {
            val diff = n.value.height - v.value.height
            if (diff <= 1) graph.setEdgeWeight(graph.addEdge(v, n), diff.toDouble())
        }
    }

    submit {
        val source = graph.vertexSet().find { it.value.c == 'S' }!!
        BFSShortestPath(graph).getPath(source, sink).length
    }
    submit {
        graph.vertexSet()
            .filter { it.value.height == 0 }
            .m { BFSShortestPath(graph).getPath(it, sink) }
            .filterNotNull()
            .minOf { it.length }
    }
}