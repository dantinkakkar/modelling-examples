package domain

import kotlin.math.max
import kotlin.math.min

class Slab(
    private val base: Long, private val fraction: Long, private val maxTax: Long
) {
    fun compute(income: Long) = max(0, min((income - base) / fraction, maxTax))
}

val belowTenSlab: Slab = Slab(0, 20, 50_000)
val tenToTwentySlab: Slab = Slab(10_00_000, 10, 1_00_000)
val twentyToThirtySlab: Slab = Slab(20_00_000, 5, 2_00_000)
val aboveThirtySlab: Slab = Slab(30_00_000, 100 / 30, Long.MAX_VALUE)
val allSlabs = listOf(belowTenSlab, tenToTwentySlab, twentyToThirtySlab, aboveThirtySlab)

fun List<Slab>.computeTax(income: Long) = this.sumOf { it.compute(income) }