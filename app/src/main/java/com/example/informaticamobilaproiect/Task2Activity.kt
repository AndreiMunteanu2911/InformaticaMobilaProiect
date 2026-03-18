package com.example.informaticamobilaproiect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Task2Activity : AppCompatActivity() {

    private lateinit var tvAfisare: TextView
    private lateinit var etStocMaxim: EditText
    private lateinit var spinnerCategorieExclusa: Spinner
    private lateinit var btnFiltreaza: Button
    private lateinit var btnInapoi: Button

    private lateinit var listaProduse: List<Produs>
    private var categorieDeExclus: Char = '*'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task2)

        // Referințe către componentele din XML
        tvAfisare = findViewById(R.id.tvAfisareDate)
        etStocMaxim = findViewById(R.id.etStocMaxim)
        spinnerCategorieExclusa = findViewById(R.id.spinnerCategorieExclusa)
        btnFiltreaza = findViewById(R.id.btnFiltreaza)
        btnInapoi = findViewById(R.id.btnInapoiTask2)

        // Buton inapoi la meniu
        btnInapoi.setOnClickListener {
            finish()
        }

        // 1. Crearea celor 6 obiecte (inițializare)
        val p1 = Produs("Smartphone", 5, 2499.9, true, 'A')
        val p2 = Produs("Căști BT", 12, 150.0, true, 'B')
        val p3 = Produs("Husa Silicon", 0, 45.5, false, 'C')
        val p4 = Produs("Încărcător", 20, 89.0, true, 'B')
        val p5 = Produs("Baterie Externă", 3, 199.9, false, 'A')
        val p6 = Produs("Cablu USB", 8, 25.0, true, 'C')

        listaProduse = listOf(p1, p2, p3, p4, p5, p6)

        // Afișare inițială a tuturor produselor
        afiseazaProduse(listaProduse, "Toate produseele:")

        // Configurare Spinner pentru categorii de exclus
        val categorii = arrayOf("Nicio categorie (Toate incluse)", "Exclude Categoria A", "Exclude Categoria B", "Exclude Categoria C")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorii)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategorieExclusa.adapter = adapter

        // Listener pentru Spinner
        spinnerCategorieExclusa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                categorieDeExclus = when (position) {
                    1 -> 'A'
                    2 -> 'B'
                    3 -> 'C'
                    else -> '*'
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                categorieDeExclus = '*'
            }
        }

        // Listener pentru butonul de filtrare
        btnFiltreaza.setOnClickListener {
            filtreazaProduse()
        }
    }

    private fun filtreazaProduse() {
        // Citire condiție 1: stoc maxim
        val stocMaxim = etStocMaxim.text.toString().toIntOrNull() ?: Int.MAX_VALUE

        // Filtrare după cele 2 condiții
        val produseFiltrate = listaProduse.filter { produs ->
            val indeplinesteConditieStoc = produs.cantitate <= stocMaxim
            val indeplinesteConditieCategorie = categorieDeExclus == '*' || produs.categorie != categorieDeExclus
            indeplinesteConditieStoc && indeplinesteConditieCategorie
        }

        val titlu = "Produse cu stoc <= $stocMaxim" +
            if (categorieDeExclus != '*') " (fără categoria '${categorieDeExclus}')" else ""

        afiseazaProduse(produseFiltrate, titlu)
    }

    private fun afiseazaProduse(produse: List<Produs>, titlu: String) {
        val textFinal = StringBuilder()
        textFinal.append("$titlu (${produse.size} produse)\n\n")

        if (produse.isEmpty()) {
            textFinal.append("Nu există produse care să corespundă condițiilor.\n")
        } else {
            for (item in produse) {
                textFinal.append("Nume: ${item.nume}\n")
                textFinal.append("Stoc: ${item.cantitate} | Preț: ${item.pret} lei\n")
                textFinal.append("Nou: ${if(item.esteNou) "DA" else "NU"} | Cat: ${item.categorie}\n")
                textFinal.append("---------------------------------\n\n")
            }
        }

        tvAfisare.text = textFinal.toString()
    }
}
