package com.carlosmartin.espaasingluten.ProductosGenericos

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carlosmartin.espaasingluten.R
import com.carlosmartin.espaasingluten.Componentes.ModoInversivo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProductosGenericosPantalla : AppCompatActivity() {
    private val listaProductos: MutableList<ProductosGenericos> = mutableListOf()
    private lateinit var adapter: ProductosGenericosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos_genericos)

        ModoInversivo.setImmersiveMode(this)

        // Obtener referencia a Firestore
        val firestore = FirebaseFirestore.getInstance()

        // Referencia a la colección "ProductosGenericos"
        val productosRef = firestore.collection("ProductosGenericos")

        // Obtener referencia a Firebase Storage
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("FotosProductosGenericos")

        // Configurar el RecyclerView y el adaptador
        val recyclerProductoGenericos = findViewById<RecyclerView>(R.id.recyclerProductoGenericos)
        adapter = ProductosGenericosAdapter(listaProductos)
        recyclerProductoGenericos.adapter = adapter
        recyclerProductoGenericos.layoutManager = LinearLayoutManager(this)

        // Escuchar los cambios en la colección
        productosRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                listaProductos.clear()

                for (document in snapshot.documents) {
                    // Obtener los datos del documento
                    val nombreDocumento = document.id
                    val info = document.getString("info")?.replace("\\n", "\n") ?: ""
                    val infoBoton = document.getString("infoBoton")?.replace("\\n", "\n") ?: ""
                    val imageUrl = document.getString("idFoto") ?: ""

                    listaProductos.add(ProductosGenericos(nombreDocumento, imageUrl, info, infoBoton))
                }

                for (producto in listaProductos) {
                    val fotoRef = storageRef.child("${producto.imagenResId}.png")
                    fotoRef.downloadUrl.addOnSuccessListener { uri: Uri? ->
                        producto.imagenResId = uri.toString()
                        adapter.notifyDataSetChanged()
                    }.addOnFailureListener { exception: Exception ->
                    }
                }
            }
        }
    }
}
