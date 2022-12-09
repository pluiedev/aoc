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

fun CharSequence.match(r: String) = Regex(r).find(this)?.destructured
fun <R> CharSequence.match(r: String, transform: (MatchResult.Destructured) -> R) =
    match(r)?.let(transform)


fun int(s: String, radix: Int = 10): Int = s.trim().toInt(radix)
@JvmName("int2")
fun CharSequence.int(radix: Int = 10): Int = toString().trim().toInt(radix)
fun Iterable<String>.ints(radix: Int = 10): List<Int> = map { it.toInt(radix) }
fun Sequence<String>.ints(radix: Int = 10): Sequence<Int> = map { it.toInt(radix) }

