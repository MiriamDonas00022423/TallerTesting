package com.example.testeableapp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import androidx.compose.ui.test.swipeRight
import com.example.testeableapp.ui.Screens.TipCalculatorScreen

import org.junit.Rule
import org.junit.Test

class TipCalculatorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAproxCheckbox() {

        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        // Haremos la prueba con $110.00 en el campo de la cuenta
        val billAmountField = composeTestRule.onNodeWithTag("BillAmountInput")
        billAmountField.performTextInput("110.00")

        // Ajustaremos el slider de la propina al 15%
        val slider = composeTestRule.onNodeWithTag("TipPercentageSlider")
        slider.performTouchInput { swipeRight(startX= 0.0f, endX = 0.2f, durationMillis = 1000)
        }

        // Verificamos que sin redondeo nos debe dar $16.50 de propina
        composeTestRule.onNodeWithTag("TipText").assertTextEquals("Propina: $16.50")


        // Marcamos la opcion de redondeo
        val roundUpCheckBox = composeTestRule.onNodeWithTag("TipCheckbox")
        roundUpCheckBox.performClick()

        // Verificamos que ahora nos debe dar $17.00 de propina
        composeTestRule.onNodeWithTag("TipText").assertTextEquals("Propina: $17.00")
    }

    @Test
    fun testSliderManagement(){

        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        // Haremos la prueba con $100.00 en el campo de la cuenta
        val billAmountField = composeTestRule.onNodeWithTag("BillAmountInput")
        billAmountField.performTextInput("100.00")

        // Ajustaremos el slider de la propina al 15%
        val slider = composeTestRule.onNodeWithTag("TipPercentageSlider")
        slider.performTouchInput{
            swipeRight(startX= 0f, endX = 0.2f, durationMillis = 1000)
        }

        // Verificamos que el texto del porcentaje de propina sea correcto
        composeTestRule.onNodeWithTag("TipText").assertTextEquals("Propina: $15.00")

        // Verificamos que el total por persona sea correcto, asumiendo 1 persona
        composeTestRule.onNodeWithTag("TotalPersonText").assertTextEquals("Total por persona: $115.00")
    }

    @Test
    fun testUIElements(){

        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        /*

        Verificamos por medio de su tag y la funcion isDisplayed(), que los elementos
        requeridos se encuentren visibles en la pantalla

        */

        composeTestRule.onNodeWithTag("BillAmountInput").isDisplayed()
        composeTestRule.onNodeWithTag("TipPercentageSlider").isDisplayed()
        composeTestRule.onNodeWithTag("NumberOfPeople").isDisplayed()
    }

    // Pruebas adicionales

    //Interaccion con el boton de numero de personas

    @Test
    fun testPersonCount(){

        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        // Haremos la prueba con $100.00 en el campo de la cuenta
        val billAmountField = composeTestRule.onNodeWithTag("BillAmountInput")
        billAmountField.performTextInput("100.00")

        // Ajustaremos el slider de la propina al 15%
        val slider = composeTestRule.onNodeWithTag("TipPercentageSlider")
        slider.performTouchInput{
            swipeRight(startX= 0f, endX = 0.2f, durationMillis = 1000)
        }

        // Verificamos que el numero de personas sea 1 por defecto
        composeTestRule.onNodeWithTag("NumberOfPeople").assertTextEquals("Número de personas: 1")

        // Interactuamos, y comprobamos el incremento de personas
        composeTestRule.onNodeWithTag("IncreasePeopleButton").performClick()
        composeTestRule.onNodeWithTag("NumberOfPeople").assertTextEquals("Número de personas: 2")

        // Verificamos el total por persona, en este caso debe ser $57.50
        composeTestRule.onNodeWithTag("TotalPersonText").assertTextEquals("Total por persona: $57.50")

    }

    // Prueba de comportamiento ante cantidades muy pequeñas, probalemente no pase el test
    @Test
    fun testSmallAmount(){

        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        // Haremos la prueba con $0.01 en el campo de la cuenta
        val billAmountField = composeTestRule.onNodeWithTag("BillAmountInput")
        billAmountField.performTextInput("0.01")

        // Ajustaremos el slider de la propina al 15%
        val slider = composeTestRule.onNodeWithTag("TipPercentageSlider")
        slider.performTouchInput{
            swipeRight(startX= 0f, endX = 0.2f, durationMillis = 1000)
        }

        /* Verificamos que antes del redondeo nos debe dar $0.0015 de propina, siendo esto
        el 15% de $0.01, lo cual es una cantidad muy pequeña

         */

        composeTestRule.onNodeWithTag("TipText").assertTextEquals("Propina: $0.0015")

    }

}