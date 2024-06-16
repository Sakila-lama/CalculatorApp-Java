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
    /**
     * MainActivity.kt
     * Author: Sakila Lama
     * StudentID: 200548805
     * Date: 15th, June 2024
     * App Description: This is a calculator app that performs basic arithmetic operations
     * using number buttons, decimal point button, and operator buttons. The app displays the
     * results of any calculation the user desires in a result label.
     * Version: 1.0
     */

    private lateinit var binding: ActivityMainBinding
    private lateinit var calculator: Calculator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calculator = Calculator(binding)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}
