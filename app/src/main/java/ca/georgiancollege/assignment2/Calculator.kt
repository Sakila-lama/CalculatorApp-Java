package ca.georgiancollege.assignment2;

import android.widget.Button;
import ca.georgiancollege.assignment2.databinding.ActivityMainBinding;

class Calculator(val binding: ActivityMainBinding) {

    private var result: String = "0"
    private var currentOperand: String = " "
    private var currentOperator: String = " "

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

    private fun handleNumberButtonClick(button:Button) {
        val currentText = binding.resultTextView.text.toString()

        // Handle plusMinus button functionality
        if (button == binding.plusMinus) {
            result = if (currentText != "0") {
                if (currentText.startsWith("-")) {
                    currentText.substring(1)  // Remove the leading "-"
                } else {
                    "-$currentText"  // Add a leading "-"
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
        result = if (currentText == "0") {
            button.text.toString()  // If current text is "0", replace it with button text
        } else {
            currentText + button.text.toString()  // Otherwise, append button text to current text
        }

        binding.resultTextView.text = result
    }

    private fun handleOperatorButtonClick(button: Button) {
        val operator = button.tag.toString()
        var currentText=  binding.resultTextView.text.toString()

        when (operator) {
            "delete" -> {
                result = "0"
                currentOperand = ""
                currentOperator = ""
            }

            "%" -> {
                val currentNumber = currentText.toDouble()
                result = (currentNumber / 100).toString()
            }

            "*", "/", "-", "+", "=" -> {
            if (currentOperator.isNotEmpty()) {
                result = performCalculation(currentOperand.toDouble(), currentText.toDouble(), currentOperator).toString()
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
    private fun performCalculation(operand1: Double, operand2: Double, operator: String): Double {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN
            else -> operand2
        }
    }
}