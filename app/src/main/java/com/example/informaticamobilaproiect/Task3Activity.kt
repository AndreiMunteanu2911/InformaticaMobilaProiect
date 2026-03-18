package com.example.informaticamobilaproiect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class Task3Activity : AppCompatActivity() {

    private lateinit var etUrl: EditText
    private lateinit var btnIncarcaDate: Button
    private lateinit var btnDeschideBrowser: Button
    private lateinit var btnInapoi: Button
    private lateinit var tvTitluValoare: TextView
    private lateinit var tvCorpValoare: TextView
    private lateinit var tvPresiuneValoare: TextView
    private lateinit var progressBar: ProgressBar

    // URL default: API vreme pentru Galati cu presiune
    // Open-Meteo: folosim 'current' in loc de 'current_weather' pentru a include presiunea
    private val defaultUrl = "https://api.open-meteo.com/v1/forecast?latitude=45.43&longitude=28.01&current=temperature_2m,relative_humidity_2m,windspeed_10m,winddirection_10m,pressure_msl,surface_pressure,weathercode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task3)

        // Referinte catre componentele din XML
        etUrl = findViewById(R.id.etUrl)
        btnIncarcaDate = findViewById(R.id.btnIncarcaDate)
        btnDeschideBrowser = findViewById(R.id.btnDeschideBrowser)
        btnInapoi = findViewById(R.id.btnInapoiTask3)
        tvTitluValoare = findViewById(R.id.tvTitluValoare)
        tvCorpValoare = findViewById(R.id.tvCorpValoare)
        tvPresiuneValoare = findViewById(R.id.tvPresiuneValoare)
        progressBar = findViewById(R.id.progressBar)

        // Setare URL default
        etUrl.setText(defaultUrl)

        // Buton incarca date de la URL
        btnIncarcaDate.setOnClickListener {
            incarcaDateDeLaUrl()
        }

        // Buton deschide browser
        btnDeschideBrowser.setOnClickListener {
            deschideBrowser()
        }

        // Buton inapoi la meniu
        btnInapoi.setOnClickListener {
            finish()
        }
    }

    private fun incarcaDateDeLaUrl() {
        // Citire URL din EditText
        val urlInput = etUrl.text.toString().trim()
        if (urlInput.isEmpty()) {
            Toast.makeText(this, "Introduceți un URL valid", Toast.LENGTH_SHORT).show()
            return
        }

        // Afisare progress bar
        progressBar.visibility = View.VISIBLE
        tvTitluValoare.text = "Se incarca..."
        tvCorpValoare.text = ""
        tvPresiuneValoare.text = ""

        // Utilizare Coroutine pentru operatiuni de retea (thread separat)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Conectare la URL si citire raspuns
                val url = URL(urlInput)
                val raspuns = url.readText()

                // Parse JSON si extragere informatii vreme
                val json = JSONObject(raspuns)
                val (titlu, corp, presiune) = extrageInformatiiVreme(json)

                // Actualizare UI pe thread-ul principal
                withContext(Dispatchers.Main) {
                    tvTitluValoare.text = titlu
                    tvCorpValoare.text = corp
                    tvPresiuneValoare.text = presiune
                    progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                // Tratare eroare
                withContext(Dispatchers.Main) {
                    tvTitluValoare.text = "Eroare"
                    tvCorpValoare.text = "Nu s-au putut incarca datele: ${e.message}"
                    tvPresiuneValoare.text = "-"
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@Task3Activity, "Eroare la incarcare", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun extrageInformatiiVreme(json: JSONObject): Triple<String, String, String> {
        return try {
            // Incercare format API Open-Meteo (vreme) - folosind 'current'
            val current = json.getJSONObject("current")
            val temperatura = current.getDouble("temperature_2m")
            val umiditate = current.getDouble("relative_humidity_2m")
            val vantViteza = current.getDouble("windspeed_10m")
            val vantDirectie = current.getDouble("winddirection_10m")
            val vremeCod = current.getInt("weathercode")

            // Extrage presiunea din current
            val pressureMsl = current.getDouble("pressure_msl")
            val surfacePressure = current.getDouble("surface_pressure")
            val presiune = "MSL: ${pressureMsl} hPa | Suprafață: ${surfacePressure} hPa"

            val titlu = "Vremea - Galati"
            val corp = "Temperatura: ${temperatura}°C\n" +
                    "Umiditate: ${umiditate}%\n" +
                    "Vânt: ${vantViteza} km/h (direcția: ${vantDirectie}°)\n" +
                    "Cod vreme: $vremeCod"

            Triple(titlu, corp, presiune)
        } catch (e: Exception) {
            // Fallback pentru alte API-uri JSON (ex: jsonplaceholder)
            try {
                val titlu = json.optString("title", "Titlu")
                val corp = json.optString("body", json.toString())
                Triple(titlu, corp, "N/A")
            } catch (e2: Exception) {
                Triple("JSON Response", json.toString(), "N/A")
            }
        }
    }

    private fun deschideBrowser() {
        // Citire URL din EditText
        val urlInput = etUrl.text.toString().trim()
        val url = if (urlInput.isNotEmpty()) urlInput else defaultUrl
        
        // Intent implicit pentru deschiderea browser-ului
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
