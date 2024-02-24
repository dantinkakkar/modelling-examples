package domain

import dsl.monthly

interface Compensation {
    val grossMonthlySalary: Long
}

class FixedCompensation(val basicSalary: Long, private val variablePay: Long) : Compensation {
    override val grossMonthlySalary: Long
        get() = (basicSalary + variablePay).monthly

    val annualIncome
        get() = basicSalary + variablePay

    val basicSalaryPerMonth
        get() = basicSalary/12
}

class HourlyCompensation(private val hourlyRate: Long, private val hoursWorked: Int) : Compensation {
    override val grossMonthlySalary: Long
        get() = hourlyRate * hoursWorked
}