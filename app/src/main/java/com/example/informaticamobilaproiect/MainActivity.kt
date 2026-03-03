package com.example.informaticamobilaproiect

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referință către TextView-ul din XML
        val tvAfisare = findViewById<TextView>(R.id.tvAfisareDate)

        // 1. Crearea celor 5 obiecte (inițializare)
        val p1 = Produs("Smartphone", 5, 2499.9, true, 'A')
        val p2 = Produs("Căști BT", 12, 150.0, true, 'B')
        val p3 = Produs("Husa Silicon", 0, 45.5, false, 'C')
        val p4 = Produs("Încărcător", 20, 89.0, true, 'B')
        val p5 = Produs("Baterie Externă", 3, 199.9, false, 'A')

        val listaMea = listOf(p1, p2, p3, p4, p5)

        // 2. Construirea textului pentru afișare
        val textFinal = StringBuilder()

        for (item in listaMea) {
            textFinal.append("Nume: ${item.nume}\n")
            textFinal.append("Stoc: ${item.cantitate} | Preț: ${item.pret} lei\n")
            textFinal.append("Nou: ${if(item.esteNou) "DA" else "NU"} | Cat: ${item.categorie}\n")
            textFinal.append("---------------------------------\n\n")
        }

        // 3. Afișarea efectivă în interfață
        tvAfisare.text = textFinal.toString()
    }
}