package ca.georgiancollege.assignment_comp3025

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {


    private lateinit var resultTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the resultTextView
        resultTextView = findViewById(R.id.resultTextView)

        // Add listeners for number buttons
        val numberButtons = listOf(
            R.id.zero_button, R.id.one_button, R.id.two_button, R.id.three_button,
            R.id.four_button, R.id.five_button, R.id.six_button, R.id.seven_button,
            R.id.eight_button, R.id.nine_button, R.id.decimal_button
        )
        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener { handleNumberButtonClick(it as Button) }
        }

        // Add listeners for operator buttons
        val operatorButtons = listOf(
            R.id.delete, R.id.plus_minus, R.id.percentage_button, R.id.multiply,
            R.id.backspace, R.id.minus, R.id.plus, R.id.divide, R.id.equals_button
        )
        for (id in operatorButtons) {
            findViewById<Button>(id).setOnClickListener { handleOperatorButtonClick(it as Button) }
        }
    }

    private fun handleNumberButtonClick(button: Button) {
        val currentText = resultTextView.text.toString()
        val newText = currentText + button.tag.toString()
        resultTextView.text = newText
    }

    private fun handleOperatorButtonClick(button: Button) {
        val currentText = resultTextView.text.toString()
        val newText = currentText + " " + button.tag.toString() + " "
        resultTextView.text = newText
    }
}
