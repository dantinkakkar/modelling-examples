package dsl

import domain.Deduction
import domain.FixedCompensation
import domain.Slab
import domain.computeTax

val Long.monthly
    get() = this / 12

enum class Frequency {
    monthly, yearly
}

infix fun Long.taxed(frequency: Frequency): Pair<Long, Frequency> = Pair(this, frequency)

infix fun Pair<Long, Frequency>.by(slabs: List<Slab>): Long = when(this.second) {
    Frequency.monthly -> slabs.computeTax(this.first).monthly
    else -> slabs.computeTax(this.first)
}

infix fun Int.`% of`(compensation: Long) = (this * compensation) / 100

infix fun FixedCompensation.without(exemptedDeductions: List<Deduction<FixedCompensation>>) =
    this.annualIncome - exemptedDeductions.map { it.applyDeduction(this) }.sumOf { it.second }
