package com.carlosmartin.espaasingluten.Mapa

import android.content.Context
import android.content.Intent
import com.carlosmartin.espaasingluten.MapaInteractivo.MapaInteractivo

data class Ciudad(
    val nombre: String
)
fun iniciarMapaInteractivo(context: Context, ciudad: Ciudad) {
    when (ciudad.nombre) {
        "Malaga" -> {
            val intent = Intent(context, MapaInteractivo::class.java)
            intent.putExtra("latitud",36.721273 )
            intent.putExtra("longitud",-4.421399 )
            context.startActivity(intent)
        }
        "Granada" -> {
            val intent = Intent(context, MapaInteractivo::class.java)
            intent.putExtra("latitud",37.1881700 )
            intent.putExtra("longitud",-3.6066700 )
            context.startActivity(intent)
        }

        else -> "Otra Zona"
    }
}