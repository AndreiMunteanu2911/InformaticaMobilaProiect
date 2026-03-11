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
        btnTask5.isEnabled = true
        btnTask5.alpha = 1.0f
        btnTask5.setOnClickListener {
            val intent = Intent(this, Task5Activity::class.java)
            startActivity(intent)
        }

        // Buton Task 6 (viitor)
        val btnTask6 = findViewById<Button>(R.id.btnTask6)
        btnTask6.isEnabled = true
        btnTask6.alpha = 1.0f
        btnTask6.setOnClickListener {
            val intent = Intent(this, Task6Activity::class.java)
            startActivity(intent)
        }

        // Buton Task 7 (viitor)
        val btnTask7 = findViewById<Button>(R.id.btnTask7)
        btnTask7.isEnabled = true
        btnTask7.alpha = 1.0f
        btnTask7.setOnClickListener {
            val intent = Intent(this, Task7Activity::class.java)
            startActivity(intent)
        }

        // Buton Task 8
        val btnTask8 = findViewById<Button>(R.id.btnTask8)
        btnTask8.isEnabled = true
        btnTask8.alpha = 1.0f
        btnTask8.setOnClickListener {
            val intent = Intent(this, Task8Activity::class.java)
            startActivity(intent)
        }
    }
}
