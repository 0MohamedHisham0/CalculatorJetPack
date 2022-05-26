package com.mtor.calculatorjetpack

sealed class CalculatorOperation(val symbol: String) {
    object Addition : CalculatorOperation("+")
    object Subtraction : CalculatorOperation("-")
    object Multiplication : CalculatorOperation("*")
    object Division : CalculatorOperation("/")
}