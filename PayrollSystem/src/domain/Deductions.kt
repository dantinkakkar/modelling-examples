package domain

import domain.Compensation

typealias DeductionLineItem = Pair<DeductionType, Long>

enum class DeductionType {
    PROFESSIONAL_TAX, INCOME_TAX, MEDICAL_INSURANCE, PROVIDENT_FUND;
    inline infix fun <reified T : Compensation> willBe(noinline deductionEvaluator: (T) -> Long): GenericDeduction<T> {
        return GenericDeduction(this, deductionEvaluator)
    }
}

interface Deduction<in T : Compensation> {
    fun applyDeduction(compensation: T): DeductionLineItem
}

data class GenericDeduction<T : Compensation>(private val deductionType: DeductionType, private val calcFn: (T) -> Long): Deduction<T> {
    override fun applyDeduction(compensation: T) = DeductionLineItem(deductionType, calcFn.invoke(compensation))
}