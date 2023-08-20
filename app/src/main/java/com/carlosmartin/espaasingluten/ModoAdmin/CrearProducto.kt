package com.carlosmartin.espaasingluten.ModoAdmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.carlosmartin.espaasingluten.R
import com.carlosmartin.espaasingluten.Componentes.ModoInversivo
import com.google.firebase.firestore.FirebaseFirestore

class CrearProducto : AppCompatActivity() {
    private lateinit var editNombre: EditText
    private lateinit var editNombreEmpresa: EditText
    private lateinit var editTipoProducto: EditText
    private lateinit var editNumero: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_producto)

        ModoInversivo.setImmersiveMode(this)

        editNombre = findViewById(R.id.editNombre)
        editNombreEmpresa = findViewById(R.id.editNombreEmpresa)
        editTipoProducto = findViewById(R.id.editTipoProducto)
        editNumero = findViewById(R.id.editNumero)

        val btnAgregarProducto: Button = findViewById(R.id.btnAgregarProducto)
        btnAgregarProducto.setOnClickListener {
            agregarProducto()
        }
    }

    private fun agregarProducto() {
        val db = FirebaseFirestore.getInstance()
        val producto = hashMapOf(
            "Nombre" to editNombre.text.toString(),
            "NombreEmpresa" to editNombreEmpresa.text.toString(),
            "Ok" to "Este producto no contiene gluten",
            "tipoProducto" to editTipoProducto.text.toString()
        )

        val id = editNumero.text.toString() // ID del documento

        db.collection("Productos")
            .document(id.toString())
            .set(producto)
            .addOnSuccessListener {
                println("Producto agregado con ID: $id")
                Toast.makeText(this,"Producto agregado con ID: $id", Toast.LENGTH_LONG).show()
                editNombre.setText("")
                //editNombreEmpresa.setText("")
                //editTipoProducto.setText("")
                editNumero.setText("")
            }
            .addOnFailureListener { e ->
                println("Error al agregar el producto: $e")
            }
    }
}