package com.example.informaticamobilaproiect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class Task5Activity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etNume: EditText
    private lateinit var rgTipFisier: RadioGroup
    private lateinit var rbFisierIntern: RadioButton
    private lateinit var rbFisierExtern: RadioButton
    private lateinit var tvCaleFisier: TextView
    private lateinit var btnCreareFisier: Button
    private lateinit var btnScriereFisier: Button
    private lateinit var btnCitireFisier: Button
    private lateinit var btnAdaugareFisier: Button
    private lateinit var btnStergereFisier: Button
    private lateinit var tvRezultat: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnInapoi: Button

    private val numeFisier = "date_personale.txt"
    private var esteFisierIntern = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task5)

        // Referinte catre componente
        etId = findViewById(R.id.etId)
        etNume = findViewById(R.id.etNume)
        rgTipFisier = findViewById(R.id.rgTipFisier)
        rbFisierIntern = findViewById(R.id.rbFisierIntern)
        rbFisierExtern = findViewById(R.id.rbFisierExtern)
        tvCaleFisier = findViewById(R.id.tvCaleFisier)
        btnCreareFisier = findViewById(R.id.btnCreareFisier)
        btnScriereFisier = findViewById(R.id.btnScriereFisier)
        btnCitireFisier = findViewById(R.id.btnCitireFisier)
        btnAdaugareFisier = findViewById(R.id.btnAdaugareFisier)
        btnStergereFisier = findViewById(R.id.btnStergereFisier)
        tvRezultat = findViewById(R.id.tvRezultat)
        tvStatus = findViewById(R.id.tvStatus)
        btnInapoi = findViewById(R.id.btnInapoiTask5)

        // Actualizare cale la schimbarea selectiei
        rgTipFisier.setOnCheckedChangeListener { _, checkedId ->
            esteFisierIntern = checkedId == R.id.rbFisierIntern
            actualizeazaCaleFisier()
        }

        // Afisare cale initiala
        actualizeazaCaleFisier()

        // Buton inapoi
        btnInapoi.setOnClickListener {
            finish()
        }

        // Buton Creare Fisier
        btnCreareFisier.setOnClickListener {
            creareFisier()
        }

        // Buton Scriere in Fisier
        btnScriereFisier.setOnClickListener {
            scriereInFisier()
        }

        // Buton Citire din Fisier
        btnCitireFisier.setOnClickListener {
            citireDinFisier()
        }

        // Buton Adaugare in Fisier
        btnAdaugareFisier.setOnClickListener {
            adaugareInFisier()
        }

        // Buton Stergere din Fisier
        btnStergereFisier.setOnClickListener {
            stergereDinFisier()
        }
    }

    private fun actualizeazaCaleFisier() {
        val cale = if (esteFisierIntern) {
            "Intern: ${filesDir.absolutePath}/$numeFisier"
        } else {
            val dirExtern = getExternalFilesDir(null)
            "Extern: ${dirExtern?.absolutePath}/$numeFisier"
        }
        tvCaleFisier.text = "Cale: $cale"
    }

    private fun obtineFisier(): File {
        return if (esteFisierIntern) {
            File(filesDir, numeFisier)
        } else {
            val dirExtern = getExternalFilesDir(null)
            File(dirExtern, numeFisier)
        }
    }

    private fun creareFisier() {
        try {
            val fisier = obtineFisier()
            if (fisier.exists()) {
                tvStatus.text = "Fișierul există deja!"
                Toast.makeText(this, "Fișierul există deja", Toast.LENGTH_SHORT).show()
                return
            }

            // Creare fisier gol
            fisier.writeText("=== Fișier creat ===\n")

            tvStatus.text = "Fișier creat cu succes!"
            tvRezultat.text = "Fișierul a fost creat la:\n${fisier.absolutePath}"
            Toast.makeText(this, "Fișier creat!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            tvStatus.text = "Eroare la creare: ${e.message}"
            Toast.makeText(this, "Eroare: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scriereInFisier() {
        val id = etId.text.toString().trim()
        val nume = etNume.text.toString().trim()

        if (id.isEmpty() || nume.isEmpty()) {
            tvStatus.text = "Completați ambele câmpuri!"
            Toast.makeText(this, "Completați toate câmpurile", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificare ID este numar intreg
        val idInt = id.toIntOrNull()
        if (idInt == null) {
            tvStatus.text = "ID-ul trebuie să fie un număr întreg!"
            Toast.makeText(this, "ID invalid", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val fisier = obtineFisier()
            val continut = "ID: $idInt\nNume: $nume\n"
            fisier.writeText(continut)

            tvStatus.text = "Date scrise cu succes!"
            tvRezultat.text = continut
            Toast.makeText(this, "Date scrise!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            tvStatus.text = "Eroare la scriere: ${e.message}"
            Toast.makeText(this, "Eroare: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun citireDinFisier() {
        try {
            val fisier = obtineFisier()
            if (!fisier.exists()) {
                tvStatus.text = "Fișierul nu există!"
                tvRezultat.text = "Fișierul nu a fost încă creat."
                Toast.makeText(this, "Fișierul nu există", Toast.LENGTH_SHORT).show()
                return
            }

            val continut = fisier.readText()

            tvStatus.text = "Citire efectuată cu succes!"
            tvRezultat.text = if (continut.isEmpty()) "(fișier gol)" else continut
            Toast.makeText(this, "Date citite!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            tvStatus.text = "Eroare la citire: ${e.message}"
            Toast.makeText(this, "Eroare: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun adaugareInFisier() {
        val id = etId.text.toString().trim()
        val nume = etNume.text.toString().trim()

        if (id.isEmpty() || nume.isEmpty()) {
            tvStatus.text = "Completați ambele câmpuri!"
            Toast.makeText(this, "Completați toate câmpurile", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificare ID este numar intreg
        val idInt = id.toIntOrNull()
        if (idInt == null) {
            tvStatus.text = "ID-ul trebuie să fie un număr întreg!"
            Toast.makeText(this, "ID invalid", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val fisier = obtineFisier()
            if (!fisier.exists()) {
                tvStatus.text = "Fișierul nu există! Creați-l mai întâi."
                Toast.makeText(this, "Creați fișierul mai întâi", Toast.LENGTH_SHORT).show()
                return
            }

            val continutExistent = fisier.readText()

            // Verificare conditie: nu adaugam daca ID-ul exista deja
            if (continutExistent.contains("ID: $idInt")) {
                tvStatus.text = "ID-ul '$idInt' există deja!"
                tvRezultat.text = "Nu se poate adăuga: ID duplicat."
                Toast.makeText(this, "ID-ul există deja", Toast.LENGTH_SHORT).show()
                return
            }

            // Adaugare noua intrare
            val nouaIntrare = "\n---\nID: $idInt\nNume: $nume\n"
            val continutActualizat = continutExistent + nouaIntrare

            fisier.writeText(continutActualizat)

            tvStatus.text = "Date adăugate cu succes!"
            tvRezultat.text = continutActualizat
            Toast.makeText(this, "Date adăugate!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            tvStatus.text = "Eroare la adăugare: ${e.message}"
            Toast.makeText(this, "Eroare: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stergereDinFisier() {
        val id = etId.text.toString().trim()

        if (id.isEmpty()) {
            tvStatus.text = "Introduceți ID-ul de șters!"
            Toast.makeText(this, "Introduceți ID-ul", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificare ID este numar intreg
        val idInt = id.toIntOrNull()
        if (idInt == null) {
            tvStatus.text = "ID-ul trebuie să fie un număr întreg!"
            Toast.makeText(this, "ID invalid", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val fisier = obtineFisier()
            if (!fisier.exists()) {
                tvStatus.text = "Fișierul nu există!"
                Toast.makeText(this, "Fișierul nu există", Toast.LENGTH_SHORT).show()
                return
            }

            val continutExistent = fisier.readText()

            // Verificare conditie: ID-ul exista?
            if (!continutExistent.contains("ID: $idInt")) {
                tvStatus.text = "ID-ul '$idInt' nu a fost găsit!"
                tvRezultat.text = continutExistent
                Toast.makeText(this, "ID-ul nu există în fișier", Toast.LENGTH_SHORT).show()
                return
            }

            // Stergere intrare
            val linii = continutExistent.split("\n")
            val liniiFiltrate = mutableListOf<String>()
            var skipUrmatoarea = false

            for (linie in linii) {
                if (linie.contains("ID: $idInt")) {
                    skipUrmatoarea = true
                    continue
                }
                if (skipUrmatoarea && (linie.contains("Nume:") || linie == "---")) {
                    skipUrmatoarea = false
                    continue
                }
                liniiFiltrate.add(linie)
            }

            val continutActualizat = liniiFiltrate.joinToString("\n")
            fisier.writeText(continutActualizat)

            tvStatus.text = "Date șterse cu succes!"
            tvRezultat.text = if (continutActualizat.isEmpty()) "(fișier gol)" else continutActualizat
            Toast.makeText(this, "Date șterse!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            tvStatus.text = "Eroare la ștergere: ${e.message}"
            Toast.makeText(this, "Eroare: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
