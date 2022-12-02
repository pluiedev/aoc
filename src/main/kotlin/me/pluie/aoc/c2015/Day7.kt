package me.pluie.aoc.c2015
import me.pluie.aoc.*

sealed interface Operand {
    fun evaluate(wires: Map<String, Wire>): UShort

    companion object {
        fun parse(s: String): Operand = try {
            Const(s.toUShort())
        } catch (ex: NumberFormatException) {
            Var(s)
        }
    }

    @JvmInline
    value class Const(private val u: UShort): Operand {
        override fun evaluate(wires: Map<String, Wire>): UShort = u
    }
    @JvmInline
    value class Var(private val name: String): Operand {
        override fun evaluate(wires: Map<String, Wire>): UShort = wires[name]!!.evaluate(wires)
    }
}

sealed class Wire {
    var value: UShort? = null

    fun evaluate(wires: Map<String, Wire>): UShort = value ?: run {
        val value = evaluateInternal(wires)
        this.value = value
        value
    }
    fun reset() {
        value = null
    }

    protected abstract fun evaluateInternal(wires: Map<String, Wire>): UShort

    data class From(val v: Operand): Wire() {
        override fun evaluateInternal(wires: Map<String, Wire>): UShort
            = v.evaluate(wires)
    }
    data class And(val lhs: Operand, val rhs: Operand): Wire() {
        override fun evaluateInternal(wires: Map<String, Wire>): UShort
            = lhs.evaluate(wires) and rhs.evaluate(wires)
    }
    data class Or(val lhs: Operand, val rhs: Operand): Wire() {
        override fun evaluateInternal(wires: Map<String, Wire>): UShort
            = lhs.evaluate(wires) or rhs.evaluate(wires)
    }
    data class Shl(val lhs: Operand, val by: Operand): Wire() {
        override fun evaluateInternal(wires: Map<String, Wire>): UShort
            = lhs.evaluate(wires) shl by.evaluate(wires)
    }
    data class Shr(val lhs: Operand, val by: Operand): Wire() {
        override fun evaluateInternal(wires: Map<String, Wire>): UShort
            = lhs.evaluate(wires) shr by.evaluate(wires)
    }
    data class Not(val v: Operand): Wire() {
        override fun evaluateInternal(wires: Map<String, Wire>): UShort
            = v.evaluate(wires).inv()
    }
}

fun main() = challenge(2015, 7) {
    val wires = Regex(
        "(?:(\\d+|[a-z]+)|([a-z]+|\\d+) (AND|OR|[LR]SHIFT) ([a-z]+|\\d+)|NOT ([a-z]+|\\d+)) -> ([a-z]+)"
        )
        .findAll(this)
        .associate {
            val (const, lhs, op, rhs, not, dest) = it.destructured
            val wire = when {
                const.isNotEmpty() -> Wire.From(Operand.parse(const))
                not.isNotEmpty() -> Wire.Not(Operand.parse(not))
                op == "AND" -> Wire.And(Operand.parse(lhs), Operand.parse(rhs))
                op == "OR" -> Wire.Or(Operand.parse(lhs), Operand.parse(rhs))
                op == "LSHIFT" -> Wire.Shl(Operand.parse(lhs), Operand.parse(rhs))
                op == "RSHIFT" -> Wire.Shr(Operand.parse(lhs), Operand.parse(rhs))
                else -> error("impossible")
            }
            dest to wire
        }
        .toSortedMap()

    val a = wires["a"]!!.evaluate(wires)

    submit { a.toInt() }
    wires.forEach { (_, v) -> v.reset() }
    wires["b"]!!.value = a

    val newA = wires["a"]!!.evaluate(wires)
    submit { newA.toInt() }

}