import domain.DeductionType.*
import domain.*
import dsl.*
import dsl.Frequency.*

class Employee<T : Compensation>(val id: Long, val name: String, private val payStructure: PayStructure<T>) {
    fun payslip() = payStructure.payslip(id, name)
}

fun fullTimeEmployee(id: Long, name: String, basic: Long, variable: Long): Employee<FixedCompensation> {
    val exemptDeductions: List<Deduction<FixedCompensation>> = listOf(
        PROVIDENT_FUND willBe { 12 `% of` it.basicSalary },
        PROFESSIONAL_TAX willBe { 2 `% of` it.basicSalary }
    )
    return Employee(
        id, name, PayStructure(
            FixedCompensation(basic, variable),
            listOf(
                PROVIDENT_FUND willBe { 12 `% of` it.basicSalaryPerMonth },
                PROFESSIONAL_TAX willBe { 2 `% of` it.basicSalaryPerMonth },
                MEDICAL_INSURANCE willBe { 1000L },
                INCOME_TAX willBe { it without exemptDeductions taxed monthly by allSlabs }
            )
        )
    )
}

fun partTimeEmployee(id: Long, name: String, hourlyRate: Long, hoursWorked: Int) = Employee(
    id, name, PayStructure(
        HourlyCompensation(hourlyRate, hoursWorked),
        listOf(
            INCOME_TAX willBe { 10 `% of` it.grossMonthlySalary },
            PROFESSIONAL_TAX willBe { 2 `% of` it.grossMonthlySalary },
            MEDICAL_INSURANCE willBe { 1000L })
    )
)