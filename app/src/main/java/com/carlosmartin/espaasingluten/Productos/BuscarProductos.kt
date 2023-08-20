package com.carlosmartin.espaasingluten.Productos

import android.content.Intent
import android.os.Bundle
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.carlosmartin.espaasingluten.R
import com.carlosmartin.espaasingluten.databinding.ActivityBuscarProductosBinding
import com.carlosmartin.espaasingluten.Componentes.ModoInversivo
import com.carlosmartin.espaasingluten.TicketsYNoticias.Ticket
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class BuscarProductos : AppCompatActivity() {

    private lateinit var binding: ActivityBuscarProductosBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ModoInversivo.setImmersiveMode(this)
        binding = ActivityBuscarProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnScanner.setOnClickListener { initScanner() }

        val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)

        when (preferences.getString("opcionSeleccionada", "")) {
            " Acromatía" -> {
                binding.btnScanner.setBackgroundColor(getColor(R.color.negro_claro))
            }
            else -> {
                binding.btnScanner.setBackgroundColor(getColor(R.color.azul))
            }
        }
    }

    private fun initScanner() {
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null && !result.contents.isNullOrEmpty()) {
                val content = result.contents
                searchProduct(content)
            } else {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showCustomDialog(nombreEmpresa: String?, nombre: String?, ok: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Producto Encontrado")

        val message = "\n\nEmpresa: $nombreEmpresa\n\nNombre del Producto: $nombre\n\nLibre de Gluten: $ok\n\n"
        val spannableMessage = SpannableString(message)
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableMessage.setSpan(boldSpan, 2, message.length - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        builder.setMessage(spannableMessage)

        val dialog = builder.create()

        dialog.setOnShowListener {
            val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)
            when (preferences.getString("opcionSeleccionada", "")) {
                " Acromatía" -> {
                    dialog.window?.setBackgroundDrawableResource(R.color.negro_claro)
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.WHITE)
                }
                else -> {
                    dialog.window?.setBackgroundDrawableResource(R.color.azul)
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.BLACK)
                }
            }
        }

        dialog.show()
    }

    private fun showCustomDialogError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Producto No Encontrado")

        val message = "\n\nEl producto que ha escaneado contiene GLUTEN o no lo tenemos reflejado en la base de datos, de no contener gluten, cree un ticket con la informacion del producto.\n\n"
        val spannableMessage = SpannableString(message)
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableMessage.setSpan(boldSpan, 2, message.length - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        builder.setMessage(spannableMessage)

        builder.setPositiveButton("Crear Ticket") { dialog, _ ->
            // Acción a realizar al pulsar el botón "Crear Ticket"
            val intent = Intent (this, Ticket::class.java)
            this.startActivity(intent)
            dialog.dismiss() // Cerrar el diálogo
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss() // Cerrar el diálogo al pulsar el botón "Cancelar"
        }

        val dialog = builder.create()

        dialog.setOnShowListener {
            val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)
            when (preferences.getString("opcionSeleccionada", "")) {
                " Acromatía" -> {
                    dialog.window?.setBackgroundDrawableResource(R.color.negro_claro)
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.WHITE)
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(Color.WHITE)
                }
                else -> {
                    dialog.window?.setBackgroundDrawableResource(R.color.azul)
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.BLACK)
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(Color.BLACK)
                }
            }
        }

        dialog.show()
    }




    private fun searchProduct(content: String) {
        val productsCollection = db.collection("Productos")
        productsCollection.document(content)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val nombreEmpresa = document.getString("NombreEmpresa")
                    val nombre = document.getString("Nombre")
                    val ok = document.getString("Ok")
                    showCustomDialog(nombreEmpresa, nombre, ok)
                } else {
                    showCustomDialogError()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al buscar el producto: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }
}
