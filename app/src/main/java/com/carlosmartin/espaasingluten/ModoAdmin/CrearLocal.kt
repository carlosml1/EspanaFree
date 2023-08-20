package com.carlosmartin.espaasingluten.ModoAdmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.carlosmartin.espaasingluten.R
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class CrearLocal : AppCompatActivity() {

    private lateinit var zona: String
    private lateinit var accesible: Switch
    private lateinit var nombre: EditText
    private lateinit var Carta: EditText
    private lateinit var Establecimiento: EditText
    private lateinit var Foto: EditText
    private lateinit var Horario: EditText
    private lateinit var Latitud: EditText
    private lateinit var Longitud: EditText
    private lateinit var Numero: EditText
    private lateinit var Ubicacion: EditText
    private lateinit var id: EditText
    private lateinit var accesibleText: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_local)

        FirebaseApp.initializeApp(this)

        accesible = findViewById(R.id.accesible)
        nombre = findViewById(R.id.nombre)
        Carta = findViewById(R.id.carta)
        Establecimiento = findViewById(R.id.Establecimiento)
        Foto = findViewById(R.id.Foto)
        Horario = findViewById(R.id.Horario)
        Latitud = findViewById(R.id.Latitud)
        Longitud = findViewById(R.id.Longitud)
        Numero = findViewById(R.id.Numero)
        Ubicacion = findViewById(R.id.Ubicacion)
        id = findViewById(R.id.id)

        if (accesible.isChecked){
            accesibleText = "Dispone de accesos para personas con movilidad reducida."
        }else{
            accesibleText = "No dispone de accesos para personas con movilidad reducida."
        }

        val btnAgregarLocal: Button = findViewById(R.id.btnAgregarLocal)
        btnAgregarLocal.setOnClickListener {
            agregarLocal()
        }
    }

    private fun agregarLocal() {
        val db = FirebaseFirestore.getInstance()
        val local = hashMapOf(
            "Accesible" to accesibleText.toString(),
            "Carta" to Carta.text.toString(),
            "Establecimiento" to Establecimiento.text.toString(),
            "Foto" to Foto.text.toString(),
            "Horario" to Horario.text.toString(),
            "Latitud" to Latitud.text.toString(),
            "Longitud" to Longitud.text.toString(),
            "Numero" to Numero.text.toString().toInt(),
            "Ubicacion" to Ubicacion.text.toString()
        )

        val zona = findViewById<EditText>(R.id.zona).text.toString()
        val idNombre = nombre.text.toString() // ID del documento

        db.collection(zona)
            .document(idNombre)
            .set(local)
            .addOnSuccessListener {
                println("Producto agregado con ID: $idNombre")
                Toast.makeText(this,"Producto agregado con ID: $id", Toast.LENGTH_LONG).show()
                Carta.setText("")
                Establecimiento.setText("")
                Foto.setText("")
                Horario.setText("")
                Latitud.setText("")
                Longitud.setText("")
                Numero.setText("")
                Ubicacion.setText("")
            }
            .addOnFailureListener { e ->
                println("Error al agregar el producto: $e")
            }
    }
}