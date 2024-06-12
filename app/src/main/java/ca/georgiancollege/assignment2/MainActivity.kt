package ca.georgiancollege.assignment2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ca.georgiancollege.assignment2.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
    private fun handleNumberButtonClick(button: Button) {
        val currentText = binding.resultTextView.text.toString()
        // Ensure the decimal button can only be pressed once
        if (button.text == "." && currentText.contains(".")) {
            return
        }

        val newText = if (currentText == "0") {
            button.text.toString()  // If current text is "0", replace it with button text
        } else {
            currentText + button.text.toString()  // Otherwise, append button text to current text
        }

        binding.resultTextView.text = newText
    }

    private fun handleOperatorButtonClick(button: Button) {
        val currentText = binding.resultTextView.text.toString()

        // Variable to store the new text that will be set to the result text view
        var newText: String

        when (button.tag.toString()) {
            "delete" -> {
                // Clear the result text view
                newText = "0"
            }
            "+/-" -> {
                // Toggle the sign of the current number
                newText = "-$currentText"
            }
            "%" -> {
                // Convert the current number to a percentage
                val currentNumber = currentText.toDouble()
                newText = (currentNumber / 100).toString()
            }
            "*" -> {
                // Add multiplication operator to the current text
                newText = "$currentText * "
            }
            "/" -> {
                // Add division operator to the current text
                newText = "$currentText / "
            }
            "-" -> {
                // Add subtraction operator to the current text
                newText = "$currentText - "
            }
            "+" -> {
                // Add addition operator to the current text
                newText = "$currentText + "
            }
            "=" -> {
                // Placeholder for calculation result
                newText = currentText
            }
            "C" -> {
                // Removes the last character from the current text
                newText = if (currentText.isNotEmpty()) {
                    currentText.dropLast(1)
                } else {
                    currentText
                }
                // If the result text view is empty, sets it to zero
                if (newText.isEmpty()) {
                    newText = "0"
                }
            }
            else -> {
                newText = currentText
            }
        }

        // Set the new text to the result text view
        binding.resultTextView.text = newText
    }
}
