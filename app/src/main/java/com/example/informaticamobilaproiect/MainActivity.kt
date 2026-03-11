package com.example.informaticamobilaproiect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Buton Task 1
        val btnTask1 = findViewById<Button>(R.id.btnTask1)
        btnTask1.setOnClickListener {
            val intent = Intent(this, Task1Activity::class.java)
            startActivity(intent)
        }

        // Buton Task 2
        val btnTask2 = findViewById<Button>(R.id.btnTask2)
        btnTask2.setOnClickListener {
            val intent = Intent(this, Task2Activity::class.java)
            startActivity(intent)
        }

        // Buton Task 3 (viitor)
        val btnTask3 = findViewById<Button>(R.id.btnTask3)
        btnTask3.isEnabled = true
        btnTask3.alpha = 1.0f
        btnTask3.setOnClickListener {
            val intent = Intent(this, Task3Activity::class.java)
            startActivity(intent)
        }

        // Buton Task 4 (viitor)
        val btnTask4 = findViewById<Button>(R.id.btnTask4)
        btnTask4.isEnabled = true
        btnTask4.alpha = 1.0f
        btnTask4.setOnClickListener {
            val intent = Intent(this, Task4Activity::class.java)
            startActivity(intent)
        }

        // Buton Task 5 (viitor)
        val btnTask5 = findViewById<Button>(R.id.btnTask5)
        btnTask5.setOnClickListener {
            // De implementat
        }

        // Buton Task 6 (viitor)
        val btnTask6 = findViewById<Button>(R.id.btnTask6)
        btnTask6.setOnClickListener {
            // De implementat
        }

        // Buton Task 7 (viitor)
        val btnTask7 = findViewById<Button>(R.id.btnTask7)
        btnTask7.setOnClickListener {
            // De implementat
        }

        // Buton Task 8 (viitor)
        val btnTask8 = findViewById<Button>(R.id.btnTask8)
        btnTask8.setOnClickListener {
            // De implementat
        }
    }
}
