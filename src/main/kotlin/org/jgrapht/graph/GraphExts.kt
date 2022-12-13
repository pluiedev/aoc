package org.jgrapht.graph

inline fun <V, reified E> SimpleDirectedWeightedGraph() =
    SimpleDirectedWeightedGraph<V, E>(E::class.java)
