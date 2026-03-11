package com.example.informaticamobilaproiect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Task7Activity : AppCompatActivity(), Task7Fragment.OnTrimiteDateListener {

    private lateinit var btnInapoi: Button
    private lateinit var tvDatePrimiteInapoi: TextView

    private lateinit var fragment: Task7Fragment

    companion object {
        const val EXTRA_MESAJ = "com.example.informaticamobilaproiect.EXTRA_MESAJ"
        const val EXTRA_NUMAR = "com.example.informaticamobilaproiect.EXTRA_NUMAR"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task7)

        // Referințe către componente
        btnInapoi = findViewById(R.id.btnInapoiTask7)
        tvDatePrimiteInapoi = findViewById(R.id.tvDatePrimiteInapoi)

        // Creare și adăugare Fragment
        if (savedInstanceState == null) {
            fragment = Task7Fragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        } else {
            fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? Task7Fragment
                ?: Task7Fragment()
        }

        // Buton inapoi la meniu
        btnInapoi.setOnClickListener {
            finish()
        }

        // Procesare date primite (din onCreate sau onNewIntent)
        procesareDatePrimite(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Când Activity-ul este relansat cu CLEAR_TOP
        procesareDatePrimite(intent)
    }

    private fun procesareDatePrimite(intent: Intent) {
        val mesajPrimit = intent.getStringExtra(EXTRA_MESAJ)
        val numarPrimit = intent.getIntExtra(EXTRA_NUMAR, 0)
        val datePrimite = intent.getBooleanExtra("DATE_PRIMITE", false)

        if (datePrimite && !mesajPrimit.isNullOrEmpty()) {
            tvDatePrimiteInapoi.text = "Mesaj: '$mesajPrimit'\nNumăr: $numarPrimit"
            tvDatePrimiteInapoi.setBackgroundColor(getColor(android.R.color.holo_green_light))
        }
    }

    // Implementare interfață - apelată când Fragmentul trimite date
    // În Fereastra 1: deschidem Fereastra 2 cu datele
    override fun onTrimiteDate(mesaj: String, numar: Int) {
        // Deschidem Fereastra 2 cu datele din fragment
        val intent = Intent(this, Task7SecondActivity::class.java)
        intent.putExtra(EXTRA_MESAJ, mesaj)
        intent.putExtra(EXTRA_NUMAR, numar)
        startActivity(intent)
    }
}
