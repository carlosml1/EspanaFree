package com.carlosmartin.espaasingluten.Mapa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carlosmartin.espaasingluten.R

class ZonasAdapter(private val zonas: Array<String>) : RecyclerView.Adapter<ZonasAdapter.ZonaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZonaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val preferences = parent.context.getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)
        val view = when (preferences.getString("opcionSeleccionada", "")) {
            " Acromatía" -> inflater.inflate(R.layout.card_recycler_acromatia, parent, false)
            else -> inflater.inflate(R.layout.card_recycler, parent, false)
        }

        return ZonaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ZonaViewHolder, position: Int) {
        val zona = zonas[position]
        holder.bind(zona)


        holder.textView.setOnClickListener {

            when (position) {
                0 -> {
                    val mostrarZonaRecyclerIntent = Intent(holder.itemView.context, MostrarZonaRecycler::class.java)
                    val variableString = "Centro"
                    mostrarZonaRecyclerIntent.putExtra("nombreZona", variableString)
                    holder.itemView.context.startActivity(mostrarZonaRecyclerIntent)
                }
                1 -> {
                    val mostrarZonaRecyclerIntent = Intent(holder.itemView.context, MostrarZonaRecycler::class.java)
                    val variableString = "MalagaEste"
                    mostrarZonaRecyclerIntent.putExtra("nombreZona", variableString)
                    holder.itemView.context.startActivity(mostrarZonaRecyclerIntent)
                }
                2 -> {
                    val mostrarZonaRecyclerIntent = Intent(holder.itemView.context, MostrarZonaRecycler::class.java)
                    val variableString = "PuertoDeLaTorre"
                    mostrarZonaRecyclerIntent.putExtra("nombreZona", variableString)
                    holder.itemView.context.startActivity(mostrarZonaRecyclerIntent)
                }
                3 -> {
                    val mostrarZonaRecyclerIntent = Intent(holder.itemView.context, MostrarZonaRecycler::class.java)
                    val variableString = "CruzDeHumilladero"
                    mostrarZonaRecyclerIntent.putExtra("nombreZona", variableString)
                    holder.itemView.context.startActivity(mostrarZonaRecyclerIntent)
                }
                4 -> {
                    val mostrarZonaRecyclerIntent = Intent(holder.itemView.context, MostrarZonaRecycler::class.java)
                    val variableString = "CarreteraDeCadiz"
                    mostrarZonaRecyclerIntent.putExtra("nombreZona", variableString)
                    holder.itemView.context.startActivity(mostrarZonaRecyclerIntent)
                }
                5 -> {
                    val mostrarZonaRecyclerIntent = Intent(holder.itemView.context, MostrarZonaRecycler::class.java)
                    val variableString = "Churriana"
                    mostrarZonaRecyclerIntent.putExtra("nombreZona", variableString)
                    holder.itemView.context.startActivity(mostrarZonaRecyclerIntent)
                }
                6 -> {
                    val mostrarZonaRecyclerIntent = Intent(holder.itemView.context, MostrarZonaRecycler::class.java)
                    val variableString = "Campanilla"
                    mostrarZonaRecyclerIntent.putExtra("nombreZona", variableString)
                    holder.itemView.context.startActivity(mostrarZonaRecyclerIntent)
                }
                7 -> {
                    val mostrarZonaRecyclerIntent = Intent(holder.itemView.context, MostrarZonaRecycler::class.java)
                    val variableString = "Teatinos"
                    mostrarZonaRecyclerIntent.putExtra("nombreZona", variableString)
                    holder.itemView.context.startActivity(mostrarZonaRecyclerIntent)
                }
                8 -> {
                    val mostrarZonaRecyclerIntent = Intent(holder.itemView.context, MostrarZonaRecycler::class.java)
                    val variableString = "MontesDeMalaga"
                    mostrarZonaRecyclerIntent.putExtra("nombreZona", variableString)
                    holder.itemView.context.startActivity(mostrarZonaRecyclerIntent)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return zonas.size
    }

    inner class ZonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.zonas)

        fun bind(zona: String) {
            textView.text = zona
        }
    }
}
