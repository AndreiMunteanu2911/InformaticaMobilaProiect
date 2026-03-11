package com.example.informaticamobilaproiect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Locale

class Task4Activity : AppCompatActivity() {

    private lateinit var tvSalut: TextView
    private lateinit var tvMesaj: TextView
    private lateinit var ivSteag: ImageView
    private lateinit var tvTitluTask4: TextView
    private lateinit var btnInapoi: Button
    private lateinit var spinnerLimba: Spinner
    private lateinit var tvLimbaCurenta: TextView

    private val limbi = arrayOf("Română", "English", "Deutsch", "Français")
    private val coduriLimbi = arrayOf("ro", "en", "de", "fr")
    private val steaguri = arrayOf(
        R.drawable.steag_romania,
        R.drawable.steag_marea_britanie,
        R.drawable.steag_germania,
        R.drawable.steag_franta
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task4)

        // Referinte catre componente
        tvTitluTask4 = findViewById(R.id.tvTitluTask4)
        tvSalut = findViewById(R.id.tvSalut)
        tvMesaj = findViewById(R.id.tvMesaj)
        ivSteag = findViewById(R.id.ivSteag)
        btnInapoi = findViewById(R.id.btnInapoiTask4)
        spinnerLimba = findViewById(R.id.spinnerLimba)
        tvLimbaCurenta = findViewById(R.id.tvLimbaCurenta)

        // Configurare Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, limbi)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLimba.adapter = adapter

        // Setare limba curenta in Spinner
        val limbaCurenta = Locale.getDefault().language
        val indexLimba = coduriLimbi.indexOfFirst { it == limbaCurenta }
        val indexInitial = if (indexLimba >= 0) indexLimba else 0
        spinnerLimba.setSelection(indexInitial)
        ivSteag.setImageResource(steaguri[indexInitial])
        actualizeazaTexte()

        // Listener pentru schimbarea limbii
        spinnerLimba.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val codLimba = coduriLimbi[position]
                schimbaLimba(codLimba)
                ivSteag.setImageResource(steaguri[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Buton inapoi
        btnInapoi.setOnClickListener {
            finish()
        }
    }

    private fun schimbaLimba(codLimba: String) {
        val locale = Locale(codLimba)
        Locale.setDefault(locale)

        val config = android.content.res.Configuration()
        config.setLocale(locale)

        // Actualizare configuratie
        val context = this
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // Actualizare texte manual fara recreate()
        actualizeazaTexte()
        tvLimbaCurenta.text = getString(R.string.limba_curenta)
    }

    private fun actualizeazaTexte() {
        tvTitluTask4.text = getString(R.string.task4_title)
        tvSalut.text = getString(R.string.salut)
        tvMesaj.text = getString(R.string.mesaj_bun_venit)
    }
}
