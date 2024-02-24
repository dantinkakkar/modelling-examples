package domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SlabTest {

    @Test
    fun `test compute when income is less than base`() {
        val slab = Slab(base = 50000, fraction = 10000, maxTax = 5000)
        val result = slab.compute(30000)
        assertEquals(0, result)
    }

    @Test
    fun `test compute when income is equal to base`() {
        val slab = Slab(base = 50000, fraction = 10000, maxTax = 5000)
        val result = slab.compute(50000)
        assertEquals(0, result)
    }

    @Test
    fun `test compute when income is between base and maxTax`() {
        val slab = Slab(base = 50000, fraction = 10000, maxTax = 5000)
        val result = slab.compute(60000)
        assertEquals(1, result)
    }

    @Test
    fun `test compute when income is greater than maxTax`() {
        val slab = Slab(base = 50000, fraction = 5, maxTax = 5000)
        val result = slab.compute(100000)
        assertEquals(5000, result)
    }
}