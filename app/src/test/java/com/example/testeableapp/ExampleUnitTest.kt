package com.example.testeableapp

import com.example.testeableapp.ui.Screens.calculateTip
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun unitTestOne() {
        val amount = 200.75
        val tipPercent = 37
        val roundUp = true
        val result = calculateTip(amount, tipPercent, roundUp)
        val delta = 0.01

        assertEquals(75.0, result, delta)
    }

    @Test
    fun secondUnitTest() {

        val amount = -50.0
        val tipPercent = 20
        val roundUp = false
        val result = calculateTip(amount, tipPercent, roundUp)
        val delta = 0.01


        assertEquals("La propina debe ser 0.0 para montos negativos", 0.0, result, delta)
    }

    @Test
    fun thirdUnitTest() {
        val amount = 200.0
        val tipPercent = 37
        val roundUp = true
        val numberOfPeople = 4
        val tip = calculateTip(amount, tipPercent, roundUp)
        val totalAmount = amount + tip
        val expectedTotalPerPerson = totalAmount / numberOfPeople
        val result = (amount + tip) / numberOfPeople
        val delta = 0.01
        assertEquals(expectedTotalPerPerson, result, delta)
    }

    @Test
    fun testEmptyBillAmount() {
        val billAmount = ""
        val amount = billAmount.toDoubleOrNull() ?: 0.0
        assertEquals(0.0, amount, 0.01)
    }

    @Test
    fun testNonNumericBillAmountReturnsZero() {
        val billAmount = "abc"
        val amount = billAmount.toDoubleOrNull() ?: 0.0
        assertEquals(0.0, amount, 0.01)
    }



}









