package ca.georgiancollege.assignment2;

import android.widget.Button;
import ca.georgiancollege.assignment2.databinding.ActivityMainBinding;


class Calculator(val binding: ActivityMainBinding) {
    /**
     * Calculator.kt
     * Author: Sakila Lama
     * StudentID: 200548805
     * Date: 15th, June 2024
     * App Description: This class handles the logic for the calculator operations including
     *  number input, operator input, and calculation execution.
     * Version: 1.0
     */

    private var result: String = "0"
    private var currentOperand: String = " "
    private var currentOperator: String = " "
    private val maxInputLength = 22

    init {

        // Add listeners for number and decimal buttons
        val numberButtons = listOf(
            binding.zeroButton, binding.oneButton, binding.twoButton, binding.threeButton,
            binding.fourButton, binding.fiveButton, binding.sixButton, binding.sevenButton,
            binding.eightButton, binding.nineButton, binding.decimalButton, binding.plusMinus
        )
        for (button in numberButtons) {
            button.setOnClickListener { handleNumberButtonClick(button) }
        }

        // Add listeners for operator buttons
        val operatorButtons = listOf(
            binding.delete, binding.percentageButton, binding.multiply,
            binding.backspace, binding.minus, binding.plus, binding.divide, binding.equalsButton
        )
        for (button in operatorButtons) {
            button.setOnClickListener { handleOperatorButtonClick(button) }
        }
    }

    /**
     * Handles number button clicks including the decimal point and the plus/minus toggle.
     *
     * @param button The button that was clicked.
     */
    private fun handleNumberButtonClick(button: Button) {
        val currentText = binding.resultTextView.text.toString()

        // Handle plusMinus button functionality
        if (button == binding.plusMinus) {
            result = if (currentText != "0") {
                if (currentText.startsWith("-")) {
                    currentText.substring(1)  // Remove the leading "-"
                } else {
                    "-$currentText"
                }
            } else {
                currentText
            }
            binding.resultTextView.text = result
            return
        }

        // Ensure the decimal button can only be pressed once
        if (button.text == "." && currentText.contains(".")) {
            return
        }
        // Limit input length
        if (currentText.length >= maxInputLength) {
            return
        }
        result = if (currentText == "0") {
            button.text.toString()  // If current text is "0", replace it with button text
        } else {
            currentText + button.text.toString()  // Otherwise, append button text to current text
        }

        binding.resultTextView.text = result
    }

    /**
     * Handles operator button clicks including the delete, percentage, arithmetic operators,
     * equals, and backspace functionalities.
     *
     * @param button The button that was clicked.
     */
    private fun handleOperatorButtonClick(button: Button) {
        val operator = button.tag.toString()
        var currentText = binding.resultTextView.text.toString()


        when (operator) {
            "delete" -> {
                result = "0"
                currentOperand = ""
                currentOperator = ""
            }

            "%" -> {
                val currentNumber = currentText.toDouble()
                result = formatNumber(currentNumber / 100)
            }

            "*", "/", "-", "+", "=" -> {
                if (currentOperator.isNotEmpty()) {
                    result = formatNumber(performCalculation(
                        currentOperand.toDouble(),
                        currentText.toDouble(),
                        currentOperator
                    ))
                }
                currentOperator = if (operator == "=") "" else operator
                currentOperand = result
                if (operator == "=") {
                    currentOperator = ""
                    currentOperand = ""
                } else {
                    result = ""
                }

            }

            "C" -> {
                result = if (currentText.isNotEmpty()) {
                    currentText.dropLast(1)
                } else {
                    currentText
                }
                if (result.isEmpty()) {
                    result = "0"
                }
            }

            else -> {
                result = currentText
            }
        }

        binding.resultTextView.text = result
    }

    /**
     * Performs the calculation based on the given operands and operator.
     *
     * @param operand1 The first operand.
     * @param operand2 The second operand.
     * @param operator The operator to be applied.
     * @return The result of the calculation.
     */
    private fun performCalculation(operand1: Double, operand2: Double, operator: String): Double {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN
            else -> operand2
        }
    }

    /**
     * Formats the number to ensure integers are displayed without decimal places and
     * floating point numbers are accurate to at least 8 decimal places.
     *
     * @param number The number to be formatted.
     * @return The formatted number as a string.
     */
    private fun formatNumber(number: Double): String {
        return if (number % 1 == 0.0) {
            number.toInt().toString()
        } else {
            String.format("%.8f", number).trimEnd('0').trimEnd('.')
        }
    }
}
