package domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream
import domain.DeductionType.*
import org.junit.jupiter.params.provider.MethodSource

class GenericDeductionTest {

    companion object {
        @JvmStatic
        fun args(): Stream<Arguments> = Stream.of(
            Arguments.of(Math.random().toLong(), PROVIDENT_FUND),
            Arguments.of(Math.random().toLong(), INCOME_TAX),
            Arguments.of(Math.random().toLong(), PROFESSIONAL_TAX),
            Arguments.of(Math.random().toLong(), PROVIDENT_FUND)
        )
    }

    @ParameterizedTest
    @MethodSource("args")
    fun `should delegate computing of deductions to pre-specified functions`(amount: Long, type: DeductionType) {
        val compensation = object : Compensation { override val grossMonthlySalary = Math.random().toLong() }
        val computeDeductionFn: (Compensation) -> Long = { amount }
        val genericDeduction = GenericDeduction(type, computeDeductionFn)
        val expected = DeductionLineItem(type, amount)

        val actual = genericDeduction.applyDeduction(compensation)

        assertEquals(expected, actual)
    }
}