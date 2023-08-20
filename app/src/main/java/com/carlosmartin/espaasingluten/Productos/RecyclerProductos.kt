package com.carlosmartin.espaasingluten.Productos

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carlosmartin.espaasingluten.R
import com.carlosmartin.espaasingluten.Componentes.ModoInversivo
import com.carlosmartin.espaasingluten.Mapa.ZonasMenuMapa
import com.carlosmartin.espaasingluten.MenuPrincipal
import com.google.firebase.firestore.FirebaseFirestore

class RecyclerProductos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductosAdapter
    private val productosList: ArrayList<String> = ArrayList()
    private var filteredList: ArrayList<String> = ArrayList()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_productos)

        ModoInversivo.setImmersiveMode(this)

        val lytBotonesRapidos: ConstraintLayout = findViewById(R.id.lytBotones)
        val btnRapidoHome: ImageButton = findViewById(R.id.btnRapidoPrincipal)
        val btnRapidoProducto: ImageButton = findViewById(R.id.btnRapidoProducto)
        val btnRapidoMapa: ImageButton = findViewById(R.id.btnRapidoMapa)

        val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)
        val opcionSeleccionada = preferences.getString("opcionSeleccionada", "")
        if(opcionSeleccionada!!.contains("Acromatía")){
            lytBotonesRapidos.setBackgroundColor(getColor(R.color.negro_claro))
            btnRapidoProducto.setBackgroundColor(getColor(R.color.white))
            btnRapidoProducto.setImageDrawable(resources.getDrawable(R.drawable.ic_icono_producto_negro))
            btnRapidoHome.setBackgroundColor(getColor(R.color.white))
            btnRapidoHome.setImageDrawable(resources.getDrawable(R.drawable.ic_icono_inicio_negro))
            btnRapidoMapa.setBackgroundColor(getColor(R.color.white))
            btnRapidoMapa.setImageDrawable(resources.getDrawable(R.drawable.ic_icono_mapa_negro))
        }else {
            lytBotonesRapidos.setBackgroundColor(getColor(R.color.azul_claro))
            btnRapidoProducto.setBackgroundColor(getColor(R.color.azul_fuerte))
            btnRapidoProducto.setImageDrawable(resources.getDrawable(R.drawable.ic_icono_producto))
            btnRapidoHome.setBackgroundColor(getColor(R.color.azul_fuerte))
            btnRapidoHome.setImageDrawable(resources.getDrawable(R.drawable.ic_icono_inicio))
            btnRapidoMapa.setBackgroundColor(getColor(R.color.azul_fuerte))
            btnRapidoMapa.setImageDrawable(resources.getDrawable(R.drawable.ic_icono_mapa))
        }


        btnRapidoHome.setOnClickListener {
            val intent = Intent (this, MenuPrincipal::class.java)
            finish()
            this.startActivity(intent)
        }

        btnRapidoProducto.setOnClickListener {

        }

        btnRapidoMapa.setOnClickListener {
            val intent = Intent (this, ZonasMenuMapa::class.java)
            finish()
            this.startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ProductosAdapter(productosList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = FirebaseFirestore.getInstance()
        val productosRef = db.collection("Productos")

        productosRef.get().addOnSuccessListener { result ->
            for (document in result) {
                val nombre = document.getString("Nombre")
                val nombreEmpresa = document.getString("NombreEmpresa")
                val tipoProducto = document.getString("tipoProducto")
                if (nombre != null && nombreEmpresa != null && tipoProducto != null) {
                    val producto = "$tipoProducto: $nombre - $nombreEmpresa"
                    productosList.add(producto)
                }
            }
            adapter.notifyDataSetChanged()
            filteredList.addAll(productosList)
        }.addOnFailureListener { exception ->
            // Manejo de errores en caso de que la consulta falle
        }

        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementación aquí
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filtrar la lista de productos en función del texto ingresado
                filterProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // No se necesita implementación aquí
            }
        })

        adapter.setOnItemClickListener { nombreEmpresa, nombre, ok ->
            showCustomDialog(nombreEmpresa, nombre, ok)
        }

    }

    private fun filterProducts(query: String) {
        filteredList.clear()
        for (product in productosList) {
            if (product.contains(query, ignoreCase = true)) {
                filteredList.add(product)
            }
        }
        adapter.filterList(filteredList)
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
            val opcionSeleccionada = preferences.getString("opcionSeleccionada", "")
            if(opcionSeleccionada!!.contains("Acromatía")){
                dialog.window?.setBackgroundDrawableResource(R.color.negro_claro)
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.WHITE)
            }else {
                dialog.window?.setBackgroundDrawableResource(R.color.azul)
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.BLACK)
            }
        }

        dialog.show()
    }
}

