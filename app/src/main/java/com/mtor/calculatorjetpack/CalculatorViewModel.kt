package com.mtor.calculatorjetpack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set // can't be set directly out of the constructor

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDeletion()
        }
    }

    private fun performDeletion() {
        when {
            state.number1.isNotBlank() && state.operation == null -> state =
                state.copy(number1 = state.number1.dropLast(1))

            state.number2.isNotBlank() && state.operation != null -> state =
                state.copy(number1 = state.number2.dropLast(1))

            state.operation != null && state.number2.isBlank() -> state =
                state.copy(operation = null)

        }
    }

    private fun performCalculation() {
        if (state.number1.isNotBlank() && state.number2.isNotBlank() && state.operation != null) {
            val result = when (state.operation) {
                CalculatorOperation.Addition -> state.number1.toDouble() + state.number2.toDouble()
                CalculatorOperation.Subtraction -> state.number1.toDouble() - state.number2.toDouble()
                CalculatorOperation.Multiplication -> state.number1.toDouble() * state.number2.toDouble()
                CalculatorOperation.Division -> state.number1.toDouble() / state.number2.toDouble()
                else -> {}
            }
            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",
                operation = null
            )
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            state = state.copy(number1 = state.number1 + number)
        } else {
            state = state.copy(number2 = state.number2 + number)
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && !state.number1.contains('.') && state.number1.isNotBlank()) {
            state = state.copy(number1 = state.number1 + '.')
            return
        }
        if (state.operation != null && !state.number2.contains('.') && state.number2.isNotBlank()) {
            state = state.copy(number1 = state.number2 + '.')
        }
    }


}