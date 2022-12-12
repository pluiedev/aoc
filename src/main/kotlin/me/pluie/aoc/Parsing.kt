package me.pluie.aoc

fun CharSequence.l(): Sequence<String> = lineSequence()
fun <R> CharSequence.l(f: (String) -> R): Sequence<R> = l().m(f)

fun CharSequence.blocks(): Sequence<Sequence<String>> =
    splitToSequence("\n\n", "\r\n\r\n").m { it.l() }
fun <R> CharSequence.blocks(f: (Sequence<String>) -> R): Sequence<R> = blocks().m(f)
fun CharSequence.csv(): Sequence<String> = splitToSequence(",")
fun <R> CharSequence.csv(f: (String) -> R): Sequence<R> = csv().m(f)
fun CharSequence.hyphens(): Sequence<String> = splitToSequence("-")
fun <R> CharSequence.hyphens(f: (String) -> R): Sequence<R> = hyphens().m(f)
fun CharSequence.spaces(): Sequence<String> = splitToSequence(" ")
fun <R> CharSequence.spaces(f: (String) -> R): Sequence<R> = spaces().m(f)
fun CharSequence.ws(): Sequence<String> = splitToSequence(Regex("\\s+"))
fun <R> CharSequence.ws(f: (String) -> R): Sequence<R> = ws().m(f)
fun CharSequence.spacedCsv(): Sequence<String> = splitToSequence(", ")
fun <R> CharSequence.spacedCsv(f: (String) -> R): Sequence<R> = spacedCsv().m(f)

fun CharSequence.match(r: String) = Regex(r).find(this)?.destructured
fun <R> CharSequence.match(r: String, transform: (MatchResult.Destructured) -> R) =
    match(r)?.let(transform)

fun CharSequence.matchAll(r: String) = Regex(r).findAll(this).m { it.destructured }
fun <R> CharSequence.matchAll(r: String, transform: (MatchResult.Destructured) -> R) =
    matchAll(r).m(transform)


fun int(s: String, radix: Int = 10): Int = s.trim().toInt(radix)
fun long(s: String, radix: Int = 10): Long = s.trim().toLong(radix)
fun float(s: String): Float = s.trim().toFloat()
fun double(s: String): Double = s.trim().toDouble()
@JvmName("int2")
fun CharSequence.int(radix: Int = 10): Int = int(toString(), radix)
@JvmName("long2")
fun CharSequence.long(radix: Int = 10): Long = long(toString(), radix)
@JvmName("float2")
fun CharSequence.float(): Float = float(toString())
@JvmName("double2")
fun CharSequence.double(): Double = double(toString())

fun Iterable<String>.ints(radix: Int = 10): List<Int> = m { it.toInt(radix) }
fun Iterable<String>.longs(radix: Int = 10): List<Long> = m { it.toLong(radix) }
fun Iterable<String>.floats(): List<Float> = m { it.toFloat() }
fun Iterable<String>.doubles(): List<Double> = m { it.toDouble() }
fun Sequence<String>.ints(radix: Int = 10): Sequence<Int> = m { it.toInt(radix) }
fun Sequence<String>.longs(radix: Int = 10): Sequence<Long> = m { it.toLong(radix) }
fun Sequence<String>.floats(): Sequence<Float> = m { it.toFloat() }
fun Sequence<String>.doubles(): Sequence<Double> = m { it.toDouble() }

fun CharSequence.pos(): Pos = split2(",").let { (a, b) -> Pos(a.int(), b.int()) }