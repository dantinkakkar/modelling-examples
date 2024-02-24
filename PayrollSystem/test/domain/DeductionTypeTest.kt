package domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DeductionTypeTest {

    @Test
    fun `should return a deduction of type fixed compensation using willBe`() {
        val deductionType = DeductionType.PROVIDENT_FUND
        val methodToCalculate: (FixedCompensation) -> Long = { 1000L }
        val expectedDeduction: GenericDeduction<FixedCompensation> = GenericDeduction(deductionType, methodToCalculate)

        val actualDeduction = deductionType willBe methodToCalculate

        assertEquals(expectedDeduction, actualDeduction)
    }
}