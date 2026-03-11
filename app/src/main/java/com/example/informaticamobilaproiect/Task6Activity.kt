package com.example.informaticamobilaproiect

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Task6Activity : AppCompatActivity() {

    private lateinit var etNume: EditText
    private lateinit var etVarsta: EditText
    private lateinit var btnDeschideFereastra: Button
    private lateinit var btnInapoi: Button
    private lateinit var tvPreview: TextView

    companion object {
        const val EXTRA_NUME = "com.example.informaticamobilaproiect.EXTRA_NUME"
        const val EXTRA_VARSTA = "com.example.informaticamobilaproiect.EXTRA_VARSTA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task6)

        // Referinte catre componente
        etNume = findViewById(R.id.etNume)
        etVarsta = findViewById(R.id.etVarsta)
        btnDeschideFereastra = findViewById(R.id.btnDeschideFereastra)
        btnInapoi = findViewById(R.id.btnInapoiTask6)
        tvPreview = findViewById(R.id.tvPreview)

        // Listener pentru actualizare preview
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                actualizeazaPreview()
            }
        }

        etNume.addTextChangedListener(textWatcher)
        etVarsta.addTextChangedListener(textWatcher)

        // Buton deschide fereastra noua
        btnDeschideFereastra.setOnClickListener {
            val nume = etNume.text.toString().trim()
            val varsta = etVarsta.text.toString().trim()

            if (nume.isEmpty() || varsta.isEmpty()) {
                etNume.error = if (nume.isEmpty()) "Câmp obligatoriu" else null
                etVarsta.error = if (varsta.isEmpty()) "Câmp obligatoriu" else null
                return@setOnClickListener
            }

            // Creare Intent pentru a deschide a doua fereastra
            val intent = Intent(this, Task6SecondActivity::class.java)
            
            // Transmitere date prin Intent
            intent.putExtra(EXTRA_NUME, nume)
            intent.putExtra(EXTRA_VARSTA, varsta.toInt())

            // Deschidere fereastra noua
            startActivity(intent)
        }

        // Buton inapoi la meniu
        btnInapoi.setOnClickListener {
            finish()
        }

        // Actualizare initiala preview
        actualizeazaPreview()
    }

    private fun actualizeazaPreview() {
        val nume = etNume.text.toString().trim().ifEmpty { "-" }
        val varsta = etVarsta.text.toString().trim().ifEmpty { "-" }
        tvPreview.text = "Nume: $nume\nVârstă: $varsta"
    }
}
