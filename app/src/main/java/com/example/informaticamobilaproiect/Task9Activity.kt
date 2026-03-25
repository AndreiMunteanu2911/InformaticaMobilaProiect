package com.example.informaticamobilaproiect

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.informaticamobilaproiect.data.AppDatabase
import com.example.informaticamobilaproiect.data.Comanda
import com.example.informaticamobilaproiect.data.ComandaRepository
import com.example.informaticamobilaproiect.data.Produs
import com.example.informaticamobilaproiect.data.ProdusRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Task9Activity : AppCompatActivity() {

    private lateinit var rgModul: RadioGroup
    private lateinit var rbModuleProduse: RadioButton
    private lateinit var rbModuleComenzi: RadioButton

    // Campuri pentru produse
    private lateinit var etProdusNume: EditText
    private lateinit var etProdusPret: EditText
    private lateinit var etProdusCategorie: EditText
    private lateinit var etProdusId: EditText
    private lateinit var btnProdusAdauga: Button
    private lateinit var btnProdusActualizeaza: Button
    private lateinit var btnProdusSterge: Button
    private lateinit var spinnerProduse: Spinner

    // Campuri pentru comenzi
    private lateinit var etComandaProdusId: EditText
    private lateinit var etComandaCantitate: EditText
    private lateinit var etComandaStatus: EditText
    private lateinit var etComandaId: EditText
    private lateinit var btnComandaAdauga: Button
    private lateinit var btnComandaActualizeaza: Button
    private lateinit var btnComandaSterge: Button
    private lateinit var spinnerComenzi: Spinner

    // Afisare rezultate
    private lateinit var tvRezultat: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnInapoi: Button
    private lateinit var btnRefresh: Button

    private lateinit var produsRepository: ProdusRepository
    private lateinit var comandaRepository: ComandaRepository

    private var esteModProduse = true

    // Liste pentru spinners
    private val produseList = mutableListOf<Produs>()
    private val comenziList = mutableListOf<Comanda>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task9)

        // Initializare database si repository
        val database = AppDatabase.getDatabase(this)
        produsRepository = ProdusRepository(database.produsDao())
        comandaRepository = ComandaRepository(database.comandaDao())

        // Initializare componente UI
        initializeComponents()

        // Setup listeners
        setupListeners()

        // Incarcare date initiale
        incarcaDate()
    }

    private fun initializeComponents() {
        rgModul = findViewById(R.id.rgModul)
        rbModuleProduse = findViewById(R.id.rbModuleProduse)
        rbModuleComenzi = findViewById(R.id.rbModuleComenzi)

        // Produse
        etProdusNume = findViewById(R.id.etProdusNume)
        etProdusPret = findViewById(R.id.etProdusPret)
        etProdusCategorie = findViewById(R.id.etProdusCategorie)
        etProdusId = findViewById(R.id.etProdusId)
        btnProdusAdauga = findViewById(R.id.btnProdusAdauga)
        btnProdusActualizeaza = findViewById(R.id.btnProdusActualizeaza)
        btnProdusSterge = findViewById(R.id.btnProdusSterge)
        spinnerProduse = findViewById(R.id.spinnerProduse)

        // Comenzi
        etComandaProdusId = findViewById(R.id.etComandaProdusId)
        etComandaCantitate = findViewById(R.id.etComandaCantitate)
        etComandaStatus = findViewById(R.id.etComandaStatus)
        etComandaId = findViewById(R.id.etComandaId)
        btnComandaAdauga = findViewById(R.id.btnComandaAdauga)
        btnComandaActualizeaza = findViewById(R.id.btnComandaActualizeaza)
        btnComandaSterge = findViewById(R.id.btnComandaSterge)
        spinnerComenzi = findViewById(R.id.spinnerComenzi)

        // Rezultate
        tvRezultat = findViewById(R.id.tvRezultat)
        tvStatus = findViewById(R.id.tvStatus)
        btnInapoi = findViewById(R.id.btnInapoi)
        btnRefresh = findViewById(R.id.btnRefresh)

        // Setare vizibilitate initiala
        actualizeazaVizibilitateModule()
    }

    private fun setupListeners() {
        rgModul.setOnCheckedChangeListener { _, checkedId ->
            esteModProduse = checkedId == R.id.rbModuleProduse
            actualizeazaVizibilitateModule()
            incarcaDate()
        }

        // Buton adaugare produs
        btnProdusAdauga.setOnClickListener {
            adaugaProdus()
        }

        // Buton actualizare produs
        btnProdusActualizeaza.setOnClickListener {
            actualizeazaProdus()
        }

        // Buton stergere produs
        btnProdusSterge.setOnClickListener {
            stergeProdus()
        }

        // Buton adaugare comanda
        btnComandaAdauga.setOnClickListener {
            adaugaComanda()
        }

        // Buton actualizare comanda
        btnComandaActualizeaza.setOnClickListener {
            actualizeazaComanda()
        }

        // Buton stergere comanda
        btnComandaSterge.setOnClickListener {
            stergeComanda()
        }

        // Buton inapoi
        btnInapoi.setOnClickListener {
            finish()
        }

        // Buton refresh
        btnRefresh.setOnClickListener {
            incarcaDate()
            Toast.makeText(this, "Date reîncărcate", Toast.LENGTH_SHORT).show()
        }

        // Spinner listener pentru produse
        spinnerProduse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                if (position > 0 && produseList.isNotEmpty()) {
                    val produs = produseList[position - 1]
                    etProdusId.setText(produs.id.toString())
                    etProdusNume.setText(produs.nume)
                    etProdusPret.setText(produs.pret.toString())
                    etProdusCategorie.setText(produs.categorie)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Spinner listener pentru comenzi
        spinnerComenzi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                if (position > 0 && comenziList.isNotEmpty()) {
                    val comanda = comenziList[position - 1]
                    etComandaId.setText(comanda.id.toString())
                    etComandaProdusId.setText(comanda.produsId.toString())
                    etComandaCantitate.setText(comanda.cantitate.toString())
                    etComandaStatus.setText(comanda.status)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun actualizeazaVizibilitateModule() {
        val vizibilitateProduse = if (esteModProduse) android.view.View.VISIBLE else android.view.View.GONE
        val vizibilitateComenzi = if (esteModProduse) android.view.View.GONE else android.view.View.VISIBLE

        etProdusNume.visibility = vizibilitateProduse
        etProdusPret.visibility = vizibilitateProduse
        etProdusCategorie.visibility = vizibilitateProduse
        etProdusId.visibility = vizibilitateProduse
        btnProdusAdauga.visibility = vizibilitateProduse
        btnProdusActualizeaza.visibility = vizibilitateProduse
        btnProdusSterge.visibility = vizibilitateProduse
        spinnerProduse.visibility = vizibilitateProduse

        etComandaProdusId.visibility = vizibilitateComenzi
        etComandaCantitate.visibility = vizibilitateComenzi
        etComandaStatus.visibility = vizibilitateComenzi
        etComandaId.visibility = vizibilitateComenzi
        btnComandaAdauga.visibility = vizibilitateComenzi
        btnComandaActualizeaza.visibility = vizibilitateComenzi
        btnComandaSterge.visibility = vizibilitateComenzi
        spinnerComenzi.visibility = vizibilitateComenzi
    }

    private fun incarcaDate() {
        if (esteModProduse) {
            incarcaProduse()
        } else {
            incarcaComenzi()
        }
    }

    // ==================== OPERATIUNI PRODUSE ====================

    private fun adaugaProdus() {
        val nume = etProdusNume.text.toString().trim()
        val pretStr = etProdusPret.text.toString().trim()
        val categorie = etProdusCategorie.text.toString().trim()

        if (nume.isEmpty()) {
            tvStatus.text = "Status: Eroare - Numele este obligatoriu!"
            Toast.makeText(this, "Introduceți numele produsului", Toast.LENGTH_SHORT).show()
            return
        }

        if (pretStr.isEmpty()) {
            tvStatus.text = "Status: Eroare - Prețul este obligatoriu!"
            Toast.makeText(this, "Introduceți prețul produsului", Toast.LENGTH_SHORT).show()
            return
        }

        val pret = pretStr.toDoubleOrNull()
        if (pret == null) {
            tvStatus.text = "Status: Eroare - Preț invalid!"
            Toast.makeText(this, "Preț invalid", Toast.LENGTH_SHORT).show()
            return
        }

        if (categorie.isEmpty()) {
            tvStatus.text = "Status: Eroare - Categoria este obligatorie!"
            Toast.makeText(this, "Introduceți categoria produsului", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            tvStatus.text = "Status: Se adaugă produsul..."
            try {
                val id = produsRepository.insert(nume, pret, categorie)
                tvStatus.text = "Status: Produs adăugat cu succes! ID: $id"
                tvRezultat.text = "Produs '$nume' adăugat cu ID-ul $id"
                Toast.makeText(this@Task9Activity, "Produs adăugat", Toast.LENGTH_SHORT).show()
                golireCampuriProduse()
            } catch (e: Exception) {
                tvStatus.text = "Status: Eroare la adăugare - ${e.message}"
                tvRezultat.text = "Eroare: ${e.message}"
            }
        }
    }

    private fun actualizeazaProdus() {
        val idStr = etProdusId.text.toString().trim()

        if (idStr.isEmpty()) {
            tvStatus.text = "Status: Eroare - ID-ul este obligatoriu!"
            Toast.makeText(this, "Selectați un produs din listă", Toast.LENGTH_SHORT).show()
            return
        }

        val id = idStr.toLongOrNull() ?: run {
            tvStatus.text = "Status: Eroare - ID invalid!"
            Toast.makeText(this, "ID invalid", Toast.LENGTH_SHORT).show()
            return
        }

        val nume = etProdusNume.text.toString().trim()
        val pretStr = etProdusPret.text.toString().trim()
        val categorie = etProdusCategorie.text.toString().trim()

        val pret = pretStr.toDoubleOrNull() ?: run {
            tvStatus.text = "Status: Eroare - Preț invalid!"
            Toast.makeText(this, "Preț invalid", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            tvStatus.text = "Status: Se actualizează produsul..."
            try {
                produsRepository.update(id, nume, pret, categorie)
                tvStatus.text = "Status: Produs actualizat cu succes!"
                tvRezultat.text = "Produs cu ID $id actualizat"
                Toast.makeText(this@Task9Activity, "Produs actualizat", Toast.LENGTH_SHORT).show()
                incarcaProduse()
            } catch (e: Exception) {
                tvStatus.text = "Status: Eroare la actualizare - ${e.message}"
                tvRezultat.text = "Eroare: ${e.message}"
            }
        }
    }

    private fun stergeProdus() {
        val idStr = etProdusId.text.toString().trim()

        if (idStr.isEmpty()) {
            tvStatus.text = "Status: Eroare - ID-ul este obligatoriu!"
            Toast.makeText(this, "Selectați un produs din listă", Toast.LENGTH_SHORT).show()
            return
        }

        val id = idStr.toLongOrNull() ?: run {
            tvStatus.text = "Status: Eroare - ID invalid!"
            Toast.makeText(this, "ID invalid", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            tvStatus.text = "Status: Se șterge produsul..."
            try {
                produsRepository.delete(id)
                tvStatus.text = "Status: Produs șters cu succes!"
                tvRezultat.text = "Produs cu ID $id șters"
                Toast.makeText(this@Task9Activity, "Produs șters", Toast.LENGTH_SHORT).show()
                golireCampuriProduse()
            } catch (e: Exception) {
                tvStatus.text = "Status: Eroare la ștergere - ${e.message}"
                tvRezultat.text = "Eroare: ${e.message}"
            }
        }
    }

    private fun incarcaProduse() {
        lifecycleScope.launch {
            tvStatus.text = "Status: Se încarcă produsele..."
            try {
                produsRepository.allProduse.collectLatest { lista ->
                    produseList.clear()
                    produseList.addAll(lista)

                    if (lista.isEmpty()) {
                        tvStatus.text = "Status: Nu există produse în baza de date"
                        tvRezultat.text = "(lista goală)"
                    } else {
                        tvStatus.text = "Status: ${lista.size} produse încărcate"
                        tvRezultat.text = formatareProduse(lista)
                    }

                    // Actualizare spinner
                    val numeProduse = lista.map { it.nume }
                    val adapter = ArrayAdapter(this@Task9Activity, android.R.layout.simple_spinner_item,
                        listOf("Selectează produs...") + numeProduse)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerProduse.adapter = adapter
                }
            } catch (e: Exception) {
                tvStatus.text = "Status: Eroare la încărcare - ${e.message}"
                tvRezultat.text = "Eroare: ${e.message}"
            }
        }
    }

    private fun formatareProduse(lista: List<Produs>): String {
        val sb = StringBuilder()
        sb.appendLine("=== PRODUSE ===\n")

        for ((index, produs) in lista.withIndex()) {
            sb.appendLine("${index + 1}. ID: ${produs.id}")
            sb.appendLine("   Nume: ${produs.nume}")
            sb.appendLine("   Preț: ${produs.pret}")
            sb.appendLine("   Categorie: ${produs.categorie}")
            sb.appendLine()
        }

        return sb.toString().trimEnd()
    }

    private fun golireCampuriProduse() {
        etProdusId.text.clear()
        etProdusNume.text.clear()
        etProdusPret.text.clear()
        etProdusCategorie.text.clear()
    }

    // ==================== OPERATIUNI COMENZI ====================

    private fun adaugaComanda() {
        val produsIdStr = etComandaProdusId.text.toString().trim()
        val cantitateStr = etComandaCantitate.text.toString().trim()
        val status = etComandaStatus.text.toString().trim()

        if (produsIdStr.isEmpty()) {
            tvStatus.text = "Status: Eroare - ID produs este obligatoriu!"
            Toast.makeText(this, "Introduceți ID-ul produsului", Toast.LENGTH_SHORT).show()
            return
        }

        val produsId = produsIdStr.toLongOrNull() ?: run {
            tvStatus.text = "Status: Eroare - ID produs invalid!"
            Toast.makeText(this, "ID produs invalid", Toast.LENGTH_SHORT).show()
            return
        }

        if (cantitateStr.isEmpty()) {
            tvStatus.text = "Status: Eroare - Cantitatea este obligatorie!"
            Toast.makeText(this, "Introduceți cantitatea", Toast.LENGTH_SHORT).show()
            return
        }

        val cantitate = cantitateStr.toIntOrNull() ?: run {
            tvStatus.text = "Status: Eroare - Cantitate invalidă!"
            Toast.makeText(this, "Cantitate invalidă", Toast.LENGTH_SHORT).show()
            return
        }

        if (status.isEmpty()) {
            tvStatus.text = "Status: Eroare - Statusul este obligatoriu!"
            Toast.makeText(this, "Introduceți statusul comenzii", Toast.LENGTH_SHORT).show()
            return
        }

        // Generare data curenta
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dataComanda = dateFormat.format(Date())

        lifecycleScope.launch {
            tvStatus.text = "Status: Se adaugă comanda..."
            try {
                val id = comandaRepository.insert(produsId, cantitate, dataComanda, status)
                tvStatus.text = "Status: Comandă adăugată cu succes! ID: $id"
                tvRezultat.text = "Comandă pentru produsul $produsId adăugată cu ID-ul $id"
                Toast.makeText(this@Task9Activity, "Comandă adăugată", Toast.LENGTH_SHORT).show()
                golireCampuriComenzi()
            } catch (e: Exception) {
                tvStatus.text = "Status: Eroare la adăugare - ${e.message}"
                tvRezultat.text = "Eroare: ${e.message}"
            }
        }
    }

    private fun actualizeazaComanda() {
        val idStr = etComandaId.text.toString().trim()

        if (idStr.isEmpty()) {
            tvStatus.text = "Status: Eroare - ID-ul este obligatoriu!"
            Toast.makeText(this, "Selectați o comandă din listă", Toast.LENGTH_SHORT).show()
            return
        }

        val id = idStr.toLongOrNull() ?: run {
            tvStatus.text = "Status: Eroare - ID invalid!"
            Toast.makeText(this, "ID invalid", Toast.LENGTH_SHORT).show()
            return
        }

        val produsIdStr = etComandaProdusId.text.toString().trim()
        val cantitateStr = etComandaCantitate.text.toString().trim()
        val status = etComandaStatus.text.toString().trim()

        val produsId = produsIdStr.toLongOrNull() ?: run {
            tvStatus.text = "Status: Eroare - ID produs invalid!"
            Toast.makeText(this, "ID produs invalid", Toast.LENGTH_SHORT).show()
            return
        }

        val cantitate = cantitateStr.toIntOrNull() ?: run {
            tvStatus.text = "Status: Eroare - Cantitate invalidă!"
            Toast.makeText(this, "Cantitate invalidă", Toast.LENGTH_SHORT).show()
            return
        }

        if (status.isEmpty()) {
            tvStatus.text = "Status: Eroare - Statusul este obligatoriu!"
            Toast.makeText(this, "Introduceți statusul comenzii", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            tvStatus.text = "Status: Se actualizează comanda..."
            try {
                comandaRepository.update(id, produsId, cantitate, "", status)
                tvStatus.text = "Status: Comandă actualizată cu succes!"
                tvRezultat.text = "Comandă cu ID $id actualizată"
                Toast.makeText(this@Task9Activity, "Comandă actualizată", Toast.LENGTH_SHORT).show()
                incarcaComenzi()
            } catch (e: Exception) {
                tvStatus.text = "Status: Eroare la actualizare - ${e.message}"
                tvRezultat.text = "Eroare: ${e.message}"
            }
        }
    }

    private fun stergeComanda() {
        val idStr = etComandaId.text.toString().trim()

        if (idStr.isEmpty()) {
            tvStatus.text = "Status: Eroare - ID-ul este obligatoriu!"
            Toast.makeText(this, "Selectați o comandă din listă", Toast.LENGTH_SHORT).show()
            return
        }

        val id = idStr.toLongOrNull() ?: run {
            tvStatus.text = "Status: Eroare - ID invalid!"
            Toast.makeText(this, "ID invalid", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            tvStatus.text = "Status: Se șterge comanda..."
            try {
                comandaRepository.delete(id)
                tvStatus.text = "Status: Comandă ștearsă cu succes!"
                tvRezultat.text = "Comandă cu ID $id ștearsă"
                Toast.makeText(this@Task9Activity, "Comandă ștearsă", Toast.LENGTH_SHORT).show()
                golireCampuriComenzi()
            } catch (e: Exception) {
                tvStatus.text = "Status: Eroare la ștergere - ${e.message}"
                tvRezultat.text = "Eroare: ${e.message}"
            }
        }
    }

    private fun incarcaComenzi() {
        lifecycleScope.launch {
            tvStatus.text = "Status: Se încarcă comenzile..."
            try {
                comandaRepository.allComenzi.collectLatest { lista ->
                    comenziList.clear()
                    comenziList.addAll(lista)

                    if (lista.isEmpty()) {
                        tvStatus.text = "Status: Nu există comenzi în baza de date"
                        tvRezultat.text = "(lista goală)"
                    } else {
                        tvStatus.text = "Status: ${lista.size} comenzi încărcate"
                        tvRezultat.text = formatareComenzi(lista)
                    }

                    // Actualizare spinner
                    val numeComenzi = lista.map { "Comanda #${it.id}" }
                    val adapter = ArrayAdapter(this@Task9Activity, android.R.layout.simple_spinner_item,
                        listOf("Selectează comandă...") + numeComenzi)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerComenzi.adapter = adapter
                }
            } catch (e: Exception) {
                tvStatus.text = "Status: Eroare la încărcare - ${e.message}"
                tvRezultat.text = "Eroare: ${e.message}"
            }
        }
    }

    private fun formatareComenzi(lista: List<Comanda>): String {
        val sb = StringBuilder()
        sb.appendLine("=== COMENZI ===\n")

        for ((index, comanda) in lista.withIndex()) {
            sb.appendLine("${index + 1}. ID: ${comanda.id}")
            sb.appendLine("   Produs ID: ${comanda.produsId}")
            sb.appendLine("   Cantitate: ${comanda.cantitate}")
            sb.appendLine("   Data: ${comanda.dataComanda}")
            sb.appendLine("   Status: ${comanda.status}")
            sb.appendLine()
        }

        return sb.toString().trimEnd()
    }

    private fun golireCampuriComenzi() {
        etComandaId.text.clear()
        etComandaProdusId.text.clear()
        etComandaCantitate.text.clear()
        etComandaStatus.text.clear()
    }
}
