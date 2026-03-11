package com.example.informaticamobilaproiect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Task7SecondActivity : AppCompatActivity(), Task7Fragment.OnTrimiteDateListener {

    private lateinit var btnInapoi: Button
    private lateinit var tvConfirmareTrimitere: TextView

    private lateinit var fragment: Task7Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task7_second)

        // Referințe către componente
        btnInapoi = findViewById(R.id.btnInapoiTask7Second)
        tvConfirmareTrimitere = findViewById(R.id.tvConfirmareTrimitere)

        // Primire date de la Fereastra 1
        val mesajPrimit = intent.getStringExtra(Task7Activity.EXTRA_MESAJ) ?: ""
        val numarPrimit = intent.getIntExtra(Task7Activity.EXTRA_NUMAR, 0)

        // Creare și adăugare Fragment (ACELAȘI tip ca în Fereastra 1)
        if (savedInstanceState == null) {
            fragment = Task7Fragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerSecond, fragment)
                .commit()

            // Setăm datele primite în fragment
            if (mesajPrimit.isNotEmpty()) {
                fragment.setareDate(mesajPrimit, numarPrimit)
                tvConfirmareTrimitere.text = "Date primite de la Fereastra 1:\nMesaj: '$mesajPrimit'\nNumăr: $numarPrimit"
            }
        } else {
            fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerSecond) as? Task7Fragment
                ?: Task7Fragment()
        }

        // Buton inapoi la Fereastra 1
        btnInapoi.setOnClickListener {
            finish()
        }
    }

    // Implementare interfață - apelată când Fragmentul trimite date
    // În Fereastra 2: trimitem datele înapoi și închidem fereastra
    override fun onTrimiteDate(mesaj: String, numar: Int) {
        // Trimitem datele înapoi la Fereastra 1 (fără a crea instanță nouă)
        val intent = Intent(this, Task7Activity::class.java)
        intent.putExtra(Task7Activity.EXTRA_MESAJ, mesaj)
        intent.putExtra(Task7Activity.EXTRA_NUMAR, numar)
        intent.putExtra("DATE_PRIMITE", true)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish() // Închidem Fereastra 2
    }
}
