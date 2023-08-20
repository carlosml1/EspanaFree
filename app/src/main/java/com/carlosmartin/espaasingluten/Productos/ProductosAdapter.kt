package com.carlosmartin.espaasingluten.Productos

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.carlosmartin.espaasingluten.R

class ProductosAdapter(private var productosList: List<String>) : RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productosList[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int {
        return productosList.size
    }

    fun filterList(filteredList: List<String>) {
        productosList = filteredList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (String?, String?, String?) -> Unit) {
        this.onItemClickListener = object : ProductosAdapter.OnItemClickListener {
            override fun onItemClick(nombreEmpresa: String?, nombre: String?, ok: String?) {
                listener(nombreEmpresa, nombre, ok)
            }
        }
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lytProducto: ConstraintLayout = itemView.findViewById(R.id.lytItemProducto)
        private val productoTextView: TextView = itemView.findViewById(R.id.productoTextView)


        @SuppressLint("SuspiciousIndentation")
        fun bind(producto: String) {
            val sharedPreferences = itemView.context.getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)
            val opcionSeleccionada = sharedPreferences.getString("opcionSeleccionada", "")
            if(opcionSeleccionada!!.contains("Acromatía")){
                lytProducto.setBackgroundColor(itemView.resources.getColor(R.color.negro_claro))
                productoTextView.setTextColor(itemView.resources.getColor(R.color.white))
            }else {
                lytProducto.setBackgroundColor(itemView.resources.getColor(R.color.azul_claro))
                productoTextView.setTextColor(itemView.resources.getColor(R.color.black))
            }
            productoTextView.text = producto

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val productoInfo = productosList[position].split(" - ")
                    val nombre: String? = productoInfo.getOrNull(0)
                    val nombreEmpresa: String? = productoInfo.getOrNull(1)
                    val ok: String? = "Este producto no contiene gluten"

                        onItemClickListener?.onItemClick(nombreEmpresa, nombre, ok)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(nombreEmpresa: String?, nombre: String?, ok: String?)
    }
}
