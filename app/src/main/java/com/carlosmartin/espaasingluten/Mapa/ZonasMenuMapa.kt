package com.carlosmartin.espaasingluten.Mapa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carlosmartin.espaasingluten.R
import com.carlosmartin.espaasingluten.Componentes.ModoInversivo
import com.carlosmartin.espaasingluten.MapaInteractivo.MapaInteractivo
import java.text.Normalizer

class ZonasMenuMapa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zonas_menu_mapa)

        ModoInversivo.setImmersiveMode(this)

        val btnMapa: Button = findViewById(R.id.btnMapa)
        val btnMapaInteractivo: Button = findViewById(R.id.btnMapaInteractivo)

        val nombreZona = intent.getStringExtra("nombreZona")
        btnMapa.text = "Mapa de $nombreZona"
        btnMapaInteractivo.text = "Mapa Interactivo de $nombreZona"

        val variableString = nombreZona?.let { removeAccents(it) }

        btnMapaInteractivo.setOnClickListener {
            if (variableString != null) {
                val ciudad = Ciudad(variableString)
                iniciarMapaInteractivo(this, ciudad)
            }
        }

    }

    fun removeAccents(input: String): String {
        val normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD)
        val pattern = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        return pattern.replace(normalizedString, "")
    }
}