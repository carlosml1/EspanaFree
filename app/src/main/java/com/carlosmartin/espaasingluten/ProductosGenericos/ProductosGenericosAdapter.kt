package com.carlosmartin.espaasingluten.ProductosGenericos

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carlosmartin.espaasingluten.R

class ProductosGenericosAdapter(private val productos: List<ProductosGenericos>) :
    RecyclerView.Adapter<ProductosGenericosAdapter.ProductoGenericoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoGenericoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_recycler_producto_genericos, parent, false)
        return ProductoGenericoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductoGenericoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    inner class ProductoGenericoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtCardProductosGenericos: TextView =
            itemView.findViewById(R.id.txtCardProductosGenericos)
        private val imgProductoGenerico: ImageView =
            itemView.findViewById(R.id.imgProductosGenericos)
        private val txtProductosGenericos: TextView =
            itemView.findViewById(R.id.txtInfoProductoGenerico)
        private val btnProductosGenericos: ImageButton =
            itemView.findViewById(R.id.btnInfoProductoGenerico)

        fun bind(producto: ProductosGenericos) {
            txtCardProductosGenericos.text = producto.nombre
            txtProductosGenericos.text = producto.texto

            // Establecer la imagen en imgProductoGenerico desde la URL utilizando Glide
            Glide.with(itemView.context)
                .load(producto.imagenResId)
                .into(imgProductoGenerico)

            btnProductosGenericos.setOnClickListener {
                val dialog = Dialog(itemView.context)
                dialog.setContentView(R.layout.dialog_info_productos_genericos)
                dialog.setCancelable(true)

                val txtInfo = dialog.findViewById<TextView>(R.id.txtDialogProductosGenericos)
                val btnPGenericos = dialog.findViewById<Button>(R.id.btnProductosGenericos)

                txtInfo.text = producto.info

                btnPGenericos.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }
}
