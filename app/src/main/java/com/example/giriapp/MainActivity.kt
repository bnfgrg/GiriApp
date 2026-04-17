package com.example.giriapp

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var count = 0
    private lateinit var countText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mantiene lo schermo sempre acceso mentre l'app è in primo piano
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        countText = findViewById(R.id.countText)
        val btnIncrement = findViewById<Button>(R.id.btnIncrement)
        val btnDecrement = findViewById<Button>(R.id.btnDecrement)
        val btnReset = findViewById<Button>(R.id.btnReset)

        updateCountDisplay()

        btnIncrement.setOnClickListener { increment() }
        btnDecrement.setOnClickListener { decrement() }
        btnReset.setOnClickListener { reset() }
    }

    private fun increment() {
        count++
        updateCountDisplay()
    }

    private fun decrement() {
        if (count > 0) count--
        updateCountDisplay()
    }

    private fun reset() {
        count = 0
        updateCountDisplay()
    }

    private fun updateCountDisplay() {
        countText.text = count.toString()
    }
}
