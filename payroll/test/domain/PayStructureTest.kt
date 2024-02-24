package domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class PayStructureTest {
    @Test
    fun `test payslip calculation`() {
        val (sal, amount1, amount2) = buildListAs(3) { Math.random().toLong() }
        val mockCompensation = mock(Compensation::class.java)
        val (deduction1, deduction2) = buildListAs(2) { mock(Deduction::class.java) as Deduction<Compensation> }
        val payStructure = PayStructure(mockCompensation, listOf(deduction1, deduction2))

        `when`(mockCompensation.grossMonthlySalary).thenReturn(sal)
        `when`(deduction1.applyDeduction(mockCompensation)).thenReturn(Pair(DeductionType.PROVIDENT_FUND, amount1))
        `when`(deduction2.applyDeduction(mockCompensation)).thenReturn(Pair(DeductionType.INCOME_TAX, amount2))

        val payslip = payStructure.payslip(1, "John Doe")

        assertEquals(1, payslip.id)
        assertEquals("John Doe", payslip.name)
        assertEquals(listOf(
            Pair("Gross Salary", sal),
            Pair("PROVIDENT_FUND", amount1 * -1),
            Pair("INCOME_TAX", amount2 * -1)
        ), payslip.lineItems)
    }
}

fun <T> buildListAs(size: Int, fn: () -> T): List<T> = buildList(size) {
    for (i in 0..2) { this.add(fn.invoke()) }
}