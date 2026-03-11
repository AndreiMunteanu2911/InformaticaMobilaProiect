package com.example.informaticamobilaproiect

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class Task8Activity : AppCompatActivity() {

    private lateinit var rgLocatieFisier: RadioGroup
    private lateinit var rbFisierIntern: RadioButton
    private lateinit var rbFisierExtern: RadioButton
    private lateinit var rgTipFisier: RadioGroup
    private lateinit var rbJson: RadioButton
    private lateinit var rbXml: RadioButton
    private lateinit var etNumeElement: EditText
    private lateinit var etValoareNumerica: EditText
    private lateinit var cbActiv: CheckBox
    private lateinit var etSubelementNume: EditText
    private lateinit var etSubelementValoare: EditText
    private lateinit var btnAdaugaSubelement: Button
    private lateinit var tvSubelementeAdaugate: TextView
    private lateinit var btnAdaugaElement: Button
    private lateinit var etNumeStergere: EditText
    private lateinit var btnStergeElement: Button
    private lateinit var btnCiteste: Button
    private lateinit var btnStergeTot: Button
    private lateinit var tvCaleFisier: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvRezultat: TextView
    private lateinit var btnInapoiTask8: Button

    private val numeFisierJson = "date_task8.json"
    private val numeFisierXml = "date_task8.xml"
    private var esteFisierIntern = true
    private var esteJson = true

    // Lista temporara de subelemente (pana se adauga elementul principal)
    private val subelementeTemporare = mutableListOf<Subelement>()

    // Structura de date:
    // Lista de Elemente, fiecare Element conține:
    // 1. O lista (subelemente)
    // 2. O alta structura (detalii)
    // 3. Un element boolean (activ)

    data class Subelement(
        val nume: String,
        val valoare: Double
    )

    data class Detalii(
        val descriere: String,
        val categorie: String
    )

    data class Element(
        val nume: String,
        val valoarePrincipala: Double,
        val esteActiv: Boolean,
        val subelemente: MutableList<Subelement>,
        val detalii: Detalii
    )

    private val listaElemente = mutableListOf<Element>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task8)

        // Initializare componente
        rgLocatieFisier = findViewById(R.id.rgLocatieFisier)
        rbFisierIntern = findViewById(R.id.rbFisierIntern)
        rbFisierExtern = findViewById(R.id.rbFisierExtern)
        rgTipFisier = findViewById(R.id.rgTipFisier)
        rbJson = findViewById(R.id.rbJson)
        rbXml = findViewById(R.id.rbXml)
        etNumeElement = findViewById(R.id.etNumeElement)
        etValoareNumerica = findViewById(R.id.etValoareNumerica)
        cbActiv = findViewById(R.id.cbActiv)
        etSubelementNume = findViewById(R.id.etSubelementNume)
        etSubelementValoare = findViewById(R.id.etSubelementValoare)
        btnAdaugaSubelement = findViewById(R.id.btnAdaugaSubelement)
        tvSubelementeAdaugate = findViewById(R.id.tvSubelementeAdaugate)
        btnAdaugaElement = findViewById(R.id.btnAdaugaElement)
        etNumeStergere = findViewById(R.id.etNumeStergere)
        btnStergeElement = findViewById(R.id.btnStergeElement)
        btnCiteste = findViewById(R.id.btnCiteste)
        btnStergeTot = findViewById(R.id.btnStergeTot)
        tvCaleFisier = findViewById(R.id.tvCaleFisier)
        tvStatus = findViewById(R.id.tvStatus)
        tvRezultat = findViewById(R.id.tvRezultat)
        btnInapoiTask8 = findViewById(R.id.btnInapoiTask8)

        // Actualizare cale la schimbarea locatiei
        rgLocatieFisier.setOnCheckedChangeListener { _, checkedId ->
            esteFisierIntern = checkedId == R.id.rbFisierIntern
            actualizeazaCaleFisier()
        }

        // Actualizare la schimbarea formatului
        rgTipFisier.setOnCheckedChangeListener { _, checkedId ->
            esteJson = checkedId == R.id.rbJson
            actualizeazaCaleFisier()
        }

        // Afisare cale initiala
        actualizeazaCaleFisier()
        actualizeazaTvSubelementeAdaugate()

        // Buton Adauga Subelement
        btnAdaugaSubelement.setOnClickListener {
            adaugaSubelement()
        }

        // Buton Adauga Element Principal
        btnAdaugaElement.setOnClickListener {
            adaugaElement()
        }

        // Buton Sterge Element
        btnStergeElement.setOnClickListener {
            stergeElement()
        }

        // Buton Citeste
        btnCiteste.setOnClickListener {
            citesteDinFisier()
        }

        // Buton Sterge Tot
        btnStergeTot.setOnClickListener {
            stergeTotContinutul()
        }

        // Buton Inapoi
        btnInapoiTask8.setOnClickListener {
            finish()
        }
    }

    private fun actualizeazaCaleFisier() {
        val numeFisier = if (esteJson) numeFisierJson else numeFisierXml
        val cale = if (esteFisierIntern) {
            "Intern: ${filesDir.absolutePath}/$numeFisier"
        } else {
            val dirExtern = getExternalFilesDir(null)
            "Extern: ${dirExtern?.absolutePath}/$numeFisier"
        }
        tvCaleFisier.text = "Cale fișier: $cale"
    }

    private fun obtineFisier(): File {
        val numeFisier = if (esteJson) numeFisierJson else numeFisierXml
        return if (esteFisierIntern) {
            File(filesDir, numeFisier)
        } else {
            val dirExtern = getExternalFilesDir(null)
            File(dirExtern, numeFisier)
        }
    }

    private fun adaugaSubelement() {
        val nume = etSubelementNume.text.toString().trim()
        val valoareStr = etSubelementValoare.text.toString().trim()

        if (nume.isEmpty()) {
            Toast.makeText(this, "Introduceți numele subelementului", Toast.LENGTH_SHORT).show()
            return
        }

        if (valoareStr.isEmpty()) {
            Toast.makeText(this, "Introduceți valoarea subelementului", Toast.LENGTH_SHORT).show()
            return
        }

        val valoare = valoareStr.toDoubleOrNull()
        if (valoare == null) {
            Toast.makeText(this, "Valoare invalidă", Toast.LENGTH_SHORT).show()
            return
        }

        // Adaugam in lista temporara
        subelementeTemporare.add(Subelement(nume, valoare))

        // Actualizam afisajul
        actualizeazaTvSubelementeAdaugate()

        // Golim campurile
        etSubelementNume.text.clear()
        etSubelementValoare.text.clear()
        etSubelementNume.requestFocus()

        Toast.makeText(this, "Subelement adăugat (${subelementeTemporare.size} total)", Toast.LENGTH_SHORT).show()
    }

    private fun actualizeazaTvSubelementeAdaugate() {
        if (subelementeTemporare.isEmpty()) {
            tvSubelementeAdaugate.text = "Subelemente adăugate: (niciunul)"
        } else {
            val text = buildString {
                append("Subelemente adăugate (${subelementeTemporare.size}):\n")
                subelementeTemporare.forEachIndexed { index, sub ->
                    append("  ${index + 1}. ${sub.nume}: ${sub.valoare}\n")
                }
            }
            tvSubelementeAdaugate.text = text.trimEnd()
        }
    }

    private fun adaugaElement() {
        val nume = etNumeElement.text.toString().trim()
        val valoareStr = etValoareNumerica.text.toString().trim()
        val esteActiv = cbActiv.isChecked

        if (nume.isEmpty()) {
            tvStatus.text = "Status: Eroare - Numele este obligatoriu!"
            Toast.makeText(this, "Introduceți numele elementului", Toast.LENGTH_SHORT).show()
            return
        }

        val valoarePrincipala = valoareStr.toDoubleOrNull() ?: 0.0

        // Citim continutul actual din fisier
        val fisier = obtineFisier()
        if (fisier.exists()) {
            if (esteJson) {
                citesteDinJson()
            } else {
                citesteDinXml()
            }
        }

        // Verificare duplicat dupa nume
        if (listaElemente.any { it.nume.equals(nume, ignoreCase = true) }) {
            tvStatus.text = "Status: Eroare - Elementul cu acest nume există deja!"
            tvRezultat.text = "Nu se poate adăuga: duplicat pentru '$nume'"
            Toast.makeText(this, "Elementul există deja", Toast.LENGTH_SHORT).show()
            return
        }

        // Copiem lista de subelemente temporare
        val subelementeCopie = mutableListOf<Subelement>()
        subelementeCopie.addAll(subelementeTemporare)

        // Creare alta structura (detalii)
        val detalii = Detalii(
            descriere = "Descriere pentru $nume",
            categorie = if (valoarePrincipala > 100) "Premium" else "Standard"
        )

        // Adaugam elementul in lista
        listaElemente.add(Element(nume, valoarePrincipala, esteActiv, subelementeCopie, detalii))

        // Scriem in fisier
        if (esteJson) {
            scrieInJson()
        } else {
            scrieInXml()
        }

        tvStatus.text = "Status: Element adăugat cu succes!"
        tvRezultat.text = afiseazaContinutul()
        Toast.makeText(this, "Element adăugat", Toast.LENGTH_SHORT).show()

        // Golim campurile
        etNumeElement.text.clear()
        etValoareNumerica.text.clear()
        cbActiv.isChecked = false
        subelementeTemporare.clear()
        actualizeazaTvSubelementeAdaugate()
    }

    private fun stergeElement() {
        val nume = etNumeStergere.text.toString().trim()

        if (nume.isEmpty()) {
            tvStatus.text = "Status: Eroare - Introduceți numele elementului de șters!"
            Toast.makeText(this, "Introduceți numele elementului", Toast.LENGTH_SHORT).show()
            return
        }

        // Citim continutul actual
        val fisier = obtineFisier()
        if (!fisier.exists()) {
            tvStatus.text = "Status: Eroare - Fișierul nu există!"
            tvRezultat.text = "Fișierul nu a fost încă creat."
            Toast.makeText(this, "Fișierul nu există", Toast.LENGTH_SHORT).show()
            return
        }

        if (esteJson) {
            citesteDinJson()
        } else {
            citesteDinXml()
        }

        // Verificare existenta
        if (listaElemente.none { it.nume.equals(nume, ignoreCase = true) }) {
            tvStatus.text = "Status: Eroare - Elementul nu a fost găsit!"
            tvRezultat.text = afiseazaContinutul()
            Toast.makeText(this, "Elementul '$nume' nu există", Toast.LENGTH_SHORT).show()
            return
        }

        // Stergem elementul
        listaElemente.removeAll { it.nume.equals(nume, ignoreCase = true) }

        // Scriem in fisier
        if (esteJson) {
            scrieInJson()
        } else {
            scrieInXml()
        }

        tvStatus.text = "Status: Element șters cu succes!"
        tvRezultat.text = afiseazaContinutul()
        Toast.makeText(this, "Element șters", Toast.LENGTH_SHORT).show()

        etNumeStergere.text.clear()
    }

    private fun citesteDinFisier() {
        val fisier = obtineFisier()
        if (!fisier.exists()) {
            tvStatus.text = "Status: Fișierul nu există!"
            tvRezultat.text = "Fișierul nu a fost încă creat."
            Toast.makeText(this, "Fișierul nu există", Toast.LENGTH_SHORT).show()
            return
        }

        if (esteJson) {
            citesteDinJson()
        } else {
            citesteDinXml()
        }

        tvStatus.text = "Status: Citire efectuată cu succes!"
        tvRezultat.text = afiseazaContinutul()
        Toast.makeText(this, "Date citite", Toast.LENGTH_SHORT).show()
    }

    private fun stergeTotContinutul() {
        val fisier = obtineFisier()
        if (!fisier.exists()) {
            tvStatus.text = "Status: Fișierul nu există!"
            Toast.makeText(this, "Fișierul nu există", Toast.LENGTH_SHORT).show()
            return
        }

        // Golim lista
        listaElemente.clear()

        // Scriem fisier gol
        if (esteJson) {
            fisier.writeText("[]")
        } else {
            fisier.writeText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<elemente></elemente>")
        }

        tvStatus.text = "Status: Tot conținutul a fost șters!"
        tvRezultat.text = "(fișier gol)"
        Toast.makeText(this, "Conținut șters", Toast.LENGTH_SHORT).show()
    }

    private fun citesteDinJson() {
        val fisier = obtineFisier()
        if (!fisier.exists()) return

        try {
            val continut = fisier.readText()
            if (continut.isBlank() || continut == "[]") {
                listaElemente.clear()
                return
            }

            val jsonArray = JSONArray(continut)
            listaElemente.clear()

            for (i in 0 until jsonArray.length()) {
                val elemObj = jsonArray.getJSONObject(i)

                // Citim numele, valoarea si booleanul
                val nume = elemObj.getString("nume")
                val valoarePrincipala = elemObj.getDouble("valoarePrincipala")
                val esteActiv = elemObj.getBoolean("esteActiv")

                // Citim lista de subelemente
                val subelemente = mutableListOf<Subelement>()
                if (elemObj.has("subelemente")) {
                    val subArray = elemObj.getJSONArray("subelemente")
                    for (j in 0 until subArray.length()) {
                        val subObj = subArray.getJSONObject(j)
                        val subNume = subObj.getString("nume")
                        val subValoare = subObj.getDouble("valoare")
                        subelemente.add(Subelement(subNume, subValoare))
                    }
                }

                // Citim alta structura (detalii)
                var detalii = Detalii("", "")
                if (elemObj.has("detalii")) {
                    val detaliiObj = elemObj.getJSONObject("detalii")
                    val descriere = detaliiObj.optString("descriere", "")
                    val categorie = detaliiObj.optString("categorie", "")
                    detalii = Detalii(descriere, categorie)
                }

                listaElemente.add(Element(nume, valoarePrincipala, esteActiv, subelemente, detalii))
            }
        } catch (e: Exception) {
            tvStatus.text = "Status: Eroare la citirea JSON: ${e.message}"
            listaElemente.clear()
        }
    }

    private fun scrieInJson() {
        val fisier = obtineFisier()
        try {
            val jsonArray = JSONArray()

            for (element in listaElemente) {
                val elemObj = JSONObject()

                // String si numar
                elemObj.put("nume", element.nume)
                elemObj.put("valoarePrincipala", element.valoarePrincipala)

                // Boolean
                elemObj.put("esteActiv", element.esteActiv)

                // Lista de subelemente
                val subArray = JSONArray()
                for (sub in element.subelemente) {
                    val subObj = JSONObject()
                    subObj.put("nume", sub.nume)
                    subObj.put("valoare", sub.valoare)
                    subArray.put(subObj)
                }
                elemObj.put("subelemente", subArray)

                // Alta structura (detalii)
                val detaliiObj = JSONObject()
                detaliiObj.put("descriere", element.detalii.descriere)
                detaliiObj.put("categorie", element.detalii.categorie)
                elemObj.put("detalii", detaliiObj)

                jsonArray.put(elemObj)
            }

            fisier.writeText(jsonArray.toString(2))
        } catch (e: Exception) {
            tvStatus.text = "Status: Eroare la scrierea JSON: ${e.message}"
        }
    }

    private fun citesteDinXml() {
        val fisier = obtineFisier()
        if (!fisier.exists()) return

        try {
            val continut = fisier.readText()
            listaElemente.clear()

            // Parsare elemente principale
            val elementRegex = Regex("<element>.*?</element>", RegexOption.DOT_MATCHES_ALL)
            for (match in elementRegex.findAll(continut)) {
                val elementXml = match.value

                // Citim datele principale
                val numeRegex = Regex("<nume>(.*?)</nume>")
                val valoareRegex = Regex("<valoarePrincipala>(.*?)</valoarePrincipala>")
                val activRegex = Regex("<esteActiv>(.*?)</esteActiv>")

                val numeMatch = numeRegex.find(elementXml)
                val valoareMatch = valoareRegex.find(elementXml)
                val activMatch = activRegex.find(elementXml)

                val nume = numeMatch?.groupValues?.get(1) ?: continue
                val valoarePrincipala = valoareMatch?.groupValues?.get(1)?.toDoubleOrNull() ?: 0.0
                val esteActiv = activMatch?.groupValues?.get(1)?.toBoolean() ?: false

                // Citim lista de subelemente
                val subelemente = mutableListOf<Subelement>()
                val subelementRegex = Regex("<subelement>.*?</subelement>", RegexOption.DOT_MATCHES_ALL)
                for (subMatch in subelementRegex.findAll(elementXml)) {
                    val subXml = subMatch.value
                    val subNumeRegex = Regex("<nume>(.*?)</nume>")
                    val subValoareRegex = Regex("<valoare>(.*?)</valoare>")

                    val subNumeMatch = subNumeRegex.find(subXml)
                    val subValoareMatch = subValoareRegex.find(subXml)

                    if (subNumeMatch != null && subValoareMatch != null) {
                        val subNume = subNumeMatch.groupValues[1]
                        val subValoare = subValoareMatch.groupValues[1].toDoubleOrNull() ?: 0.0
                        subelemente.add(Subelement(subNume, subValoare))
                    }
                }

                // Citim alta structura (detalii)
                var detalii = Detalii("", "")
                val detaliiRegex = Regex("<detalii>.*?</detalii>", RegexOption.DOT_MATCHES_ALL)
                val detaliiMatch = detaliiRegex.find(elementXml)
                if (detaliiMatch != null) {
                    val detaliiXml = detaliiMatch.value
                    val descriereRegex = Regex("<descriere>(.*?)</descriere>")
                    val categorieRegex = Regex("<categorie>(.*?)</categorie>")

                    val descriereMatch = descriereRegex.find(detaliiXml)
                    val categorieMatch = categorieRegex.find(detaliiXml)

                    val descriere = descriereMatch?.groupValues?.get(1) ?: ""
                    val categorie = categorieMatch?.groupValues?.get(1) ?: ""
                    detalii = Detalii(descriere, categorie)
                }

                listaElemente.add(Element(nume, valoarePrincipala, esteActiv, subelemente, detalii))
            }
        } catch (e: Exception) {
            tvStatus.text = "Status: Eroare la citirea XML: ${e.message}"
            listaElemente.clear()
        }
    }

    private fun scrieInXml() {
        val fisier = obtineFisier()
        try {
            val sb = StringBuilder()
            sb.appendLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            sb.appendLine("<elemente>")

            for (element in listaElemente) {
                sb.appendLine("  <element>")
                sb.appendLine("    <nume>${escapeXml(element.nume)}</nume>")
                sb.appendLine("    <valoarePrincipala>${element.valoarePrincipala}</valoarePrincipala>")
                sb.appendLine("    <esteActiv>${element.esteActiv}</esteActiv>")

                // Lista de subelemente
                sb.appendLine("    <subelemente>")
                for (sub in element.subelemente) {
                    sb.appendLine("      <subelement>")
                    sb.appendLine("        <nume>${escapeXml(sub.nume)}</nume>")
                    sb.appendLine("        <valoare>${sub.valoare}</valoare>")
                    sb.appendLine("      </subelement>")
                }
                sb.appendLine("    </subelemente>")

                // Alta structura (detalii)
                sb.appendLine("    <detalii>")
                sb.appendLine("      <descriere>${escapeXml(element.detalii.descriere)}</descriere>")
                sb.appendLine("      <categorie>${escapeXml(element.detalii.categorie)}</categorie>")
                sb.appendLine("    </detalii>")

                sb.appendLine("  </element>")
            }

            sb.appendLine("</elemente>")
            fisier.writeText(sb.toString())
        } catch (e: Exception) {
            tvStatus.text = "Status: Eroare la scrierea XML: ${e.message}"
        }
    }

    private fun escapeXml(text: String): String {
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
    }

    private fun afiseazaContinutul(): String {
        if (listaElemente.isEmpty()) {
            return "(lista goală)"
        }

        val sb = StringBuilder()
        sb.appendLine("=== LISTA DE ELEMENTE ===\n")

        for ((index, element) in listaElemente.withIndex()) {
            sb.appendLine("${index + 1}. Element: ${element.nume}")
            sb.appendLine("   - Valoare principală: ${element.valoarePrincipala}")
            sb.appendLine("   - Este Activ (boolean): ${element.esteActiv}")

            // Afisam lista din interiorul elementului
            sb.appendLine("   - Lista subelemente (${element.subelemente.size}):")
            if (element.subelemente.isEmpty()) {
                sb.appendLine("     (goală)")
            } else {
                for (sub in element.subelemente) {
                    sb.appendLine("     * ${sub.nume}: ${sub.valoare}")
                }
            }

            // Afisam alta structura
            sb.appendLine("   - Altă structură (detalii):")
            sb.appendLine("     * Descriere: ${element.detalii.descriere}")
            sb.appendLine("     * Categorie: ${element.detalii.categorie}")
            sb.appendLine()
        }

        return sb.toString().trimEnd()
    }
}
