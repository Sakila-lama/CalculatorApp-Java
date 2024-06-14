package ca.georgiancollege.assignment2;

import android.widget.Button;
import ca.georgiancollege.assignment2.databinding.ActivityMainBinding;

class Calculator(val binding: ActivityMainBinding) {

    private var result: String = "0"

    init {
        // Add listeners for number and decimal buttons
        val numberButtons = listOf(
                binding.zeroButton, binding.oneButton, binding.twoButton, binding.threeButton,
                binding.fourButton, binding.fiveButton, binding.sixButton, binding.sevenButton,
                binding.eightButton, binding.nineButton, binding.decimalButton
        )
        for (button in numberButtons) {
            button.setOnClickListener { handleNumberButtonClick(button) }
        }

        // Add listeners for operator buttons
        val operatorButtons = listOf(
                binding.delete, binding.plusMinus, binding.percentageButton, binding.multiply,
                binding.backspace, binding.minus, binding.plus, binding.divide, binding.equalsButton
        )
        for (button in operatorButtons) {
            button.setOnClickListener { handleOperatorButtonClick(button) }
        }
    }

    private fun handleNumberButtonClick(button:Button) {
        val currentText = binding.resultTextView.text.toString()
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
        val currentText = binding.resultTextView.text.toString()
        var newText: String = currentText

        when (button.tag.toString()) {
            "delete" -> {
                newText = "0"
            }
            "+/-" -> {
                newText = if (currentText != "0") {
                    if (currentText.startsWith("-")) {
                        currentText.substring(1)  // Remove the leading "-"
                    } else {
                        "-$currentText"  // Add a leading "-"
                    }
                } else {
                    currentText
                }
            }
            "%" -> {
                val currentNumber = currentText.toDouble()
                newText = (currentNumber / 100).toString()
            }
            "*" -> {
                newText = "$currentText * "
            }
            "/" -> {
                newText = "$currentText / "
            }
            "-" -> {
                newText = "$currentText - "
            }
            "+" -> {
                newText = "$currentText + "
            }
            "=" -> {
                // Placeholder for calculation result
                newText = currentText
            }
            "C" -> {
                newText = if (currentText.isNotEmpty()) {
                    currentText.dropLast(1)
                } else {
                    currentText
                }
                if (newText.isEmpty()) {
                    newText = "0"
                }
            }
            else -> {
                newText = currentText
            }
        }

        binding.resultTextView.text = newText
    }
}