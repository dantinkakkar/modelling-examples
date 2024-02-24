package domain

private const val GROSS_SALARY = "Gross Salary"

data class Payslip(val id: Long, val name: String, val lineItems: List<Pair<String, Long>>)

open class PayStructure<T : Compensation>(private val compensation: T, private val deductions: List<Deduction<T>>) {
    fun payslip(id: Long, name: String) = Payslip(
        id, name, grossMonthly(compensation.grossMonthlySalary) + calculateDeductions()
    )

    private fun grossMonthly(grossMonthlySalary: Long) = listOf(Pair(GROSS_SALARY, grossMonthlySalary))

    private fun calculateDeductions(): List<Pair<String, Long>> = deductions
        .map { it.applyDeduction(compensation) }
        .map { Pair(it.first.toString(), it.second * -1) }
}