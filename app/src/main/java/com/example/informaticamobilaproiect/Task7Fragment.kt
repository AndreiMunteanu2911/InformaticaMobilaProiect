package com.example.informaticamobilaproiect

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Fragment comun folosit în două Activity-uri diferite
 * Pentru a transmite date între ele
 */
class Task7Fragment : Fragment() {

    private lateinit var etMesajFragment: EditText
    private lateinit var etNumarFragment: EditText
    private lateinit var btnTrimiteDate: Button
    private lateinit var tvStatusFragment: TextView

    // Interfață pentru comunicare cu Activity-ul gazdă
    interface OnTrimiteDateListener {
        fun onTrimiteDate(mesaj: String, numar: Int)
    }

    private var listener: OnTrimiteDateListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verificăm dacă Activity-ul implementează interfața
        if (context is OnTrimiteDateListener) {
            listener = context
        } else {
            throw RuntimeException("$context trebuie să implementeze OnTrimiteDateListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task7, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referințe către componente
        etMesajFragment = view.findViewById(R.id.etMesajFragment)
        etNumarFragment = view.findViewById(R.id.etNumarFragment)
        btnTrimiteDate = view.findViewById(R.id.btnTrimiteDate)
        tvStatusFragment = view.findViewById(R.id.tvStatusFragment)

        // Buton trimite date
        btnTrimiteDate.setOnClickListener {
            val mesaj = etMesajFragment.text.toString().trim()
            val numarText = etNumarFragment.text.toString().trim()

            if (mesaj.isEmpty() || numarText.isEmpty()) {
                Toast.makeText(context, "Completează ambele câmpuri", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val numar = numarText.toIntOrNull() ?: 0

            // Transmitere date către Activity-ul gazdă
            listener?.onTrimiteDate(mesaj, numar)

            // Actualizare status
            tvStatusFragment.text = "Status: Date trimise: '$mesaj', $numar"
            tvStatusFragment.setTextColor(requireContext().getColor(android.R.color.holo_green_dark))
        }
    }

    // Metodă pentru a seta datele din exterior (când primim date de la cealaltă fereastră)
    fun setareDate(mesaj: String, numar: Int) {
        if (::etMesajFragment.isInitialized) {
            etMesajFragment.setText(mesaj)
            etNumarFragment.setText(numar.toString())
            tvStatusFragment.text = "Status: Date primite: '$mesaj', $numar"
            tvStatusFragment.setTextColor(requireContext().getColor(android.R.color.holo_blue_dark))
        }
    }

    // Metode publice pentru a obține datele din Fragment
    fun obtineMesaj(): String {
        return if (::etMesajFragment.isInitialized) {
            etMesajFragment.text.toString().trim()
        } else ""
    }

    fun obtineNumar(): Int {
        return if (::etNumarFragment.isInitialized) {
            etNumarFragment.text.toString().trim().toIntOrNull() ?: 0
        } else 0
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
