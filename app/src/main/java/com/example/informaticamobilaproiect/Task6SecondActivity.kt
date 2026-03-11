package com.example.informaticamobilaproiect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Task6SecondActivity : AppCompatActivity() {

    private lateinit var tvNumePrimit: TextView
    private lateinit var tvVarstaPrimita: TextView
    private lateinit var tvMesajConfirmare: TextView
    private lateinit var btnInapoiLaPrima: Button
    private lateinit var btnInapoiLaMeniu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task6_second)

        // Referinte catre componente
        tvNumePrimit = findViewById(R.id.tvNumePrimit)
        tvVarstaPrimita = findViewById(R.id.tvVarstaPrimita)
        tvMesajConfirmare = findViewById(R.id.tvMesajConfirmare)
        btnInapoiLaPrima = findViewById(R.id.btnInapoiLaPrima)
        btnInapoiLaMeniu = findViewById(R.id.btnInapoiLaMeniu)

        // Primire date de la Intent
        val nume = intent.getStringExtra(Task6Activity.EXTRA_NUME) ?: "Necunoscut"
        val varsta = intent.getIntExtra(Task6Activity.EXTRA_VARSTA, 0)

        // Afisare date primite
        tvNumePrimit.text = nume
        tvVarstaPrimita.text = "$varsta ani"

        // Buton inapoi la prima fereastra
        btnInapoiLaPrima.setOnClickListener {
            finish()
        }

        // Buton inapoi la meniu
        btnInapoiLaMeniu.setOnClickListener {
            // Navigare la MainActivity cu clear task
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
