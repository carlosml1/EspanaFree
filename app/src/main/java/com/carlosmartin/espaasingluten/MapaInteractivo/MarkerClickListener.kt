import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.carlosmartin.espaasingluten.Mapa.ZonasMenuMapa
import com.carlosmartin.espaasingluten.R
import com.carlosmartin.espaasingluten.MenuPrincipal
import org.osmdroid.views.overlay.Marker

class MarkerClickListener(
    private val nombre: String,
    private val horario: String,
    private val establecimiento: String,
    private val numero: String,
) : Marker.OnMarkerClickListener {

    override fun onMarkerClick(marker: Marker?, mapView: org.osmdroid.views.MapView?): Boolean {
        val context: Context = mapView!!.context
        val builder = AlertDialog.Builder(context)
        val itemView = LayoutInflater.from(context).inflate(R.layout.card_zonas_map, null)

        builder.setView(itemView)
        val dialog = builder.create()

        val card = itemView.findViewById<LinearLayout>(R.id.lytCardZonasMap)
        val imagen = itemView.findViewById<ImageView>(R.id.imagen_map)
        val txtNombre = itemView.findViewById<TextView>(R.id.mostrarNombre_map)
        val txtHorario = itemView.findViewById<TextView>(R.id.mostrarHorario_map)
        val txtNumero = itemView.findViewById<TextView>(R.id.mostrarNumero_map)
        val btnNumero = itemView.findViewById<ImageButton>(R.id.btnNumero_map)
        val mostrarHorario = itemView.findViewById<TextView>(R.id.txtHorario_map)
        val mostrarNombre = itemView.findViewById<TextView>(R.id.txtNombre_map)
        val mostrarNumero = itemView.findViewById<TextView>(R.id.txtNumero_map)

        txtNombre.text = nombre
        txtHorario.text = horario
        txtNumero.text = numero

        btnNumero.setOnClickListener {
            val phoneNumber = numero
            val dialIntent = Intent(Intent.ACTION_DIAL)
            val menuPrincipalIntent = Intent(context, ZonasMenuMapa::class.java)
            context.startActivity(menuPrincipalIntent)
            dialIntent.data = Uri.parse("tel:$phoneNumber")
            context.startActivity(dialIntent)

        }

        val sharedPreferences = itemView.context.getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)
        val opcionSeleccionada = sharedPreferences.getString("opcionSeleccionada", "")

        when (opcionSeleccionada){
            " Normal" ->{
                when (establecimiento){
                    "Bar" -> {
                        card.setBackgroundColor(itemView.context.getColor(R.color.verde_claro))
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_bar_negro))
                    }
                    "Restaurante" -> {
                        card.setBackgroundColor(itemView.context.getColor(R.color.beige))
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_restaurante_negro))
                    }
                    "Heladeria" -> {
                        card.setBackgroundColor(itemView.context.getColor(R.color.morado_claro))
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_helado_negro))
                    }
                }
                btnNumero.backgroundTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.azul))
            }
            " Protanopia" ->{
                when (establecimiento){
                    "Bar" ->  {
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_bar))
                        card.setBackgroundColor(itemView.context.getColor(R.color.rojo))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.white))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.white))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.white))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.white))
                    }
                    "Restaurante" -> {
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_restaurante_negro))
                        card.setBackgroundColor(itemView.context.getColor(R.color.beige))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.black))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.black))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.black))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.black))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.black))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.black))
                    }
                    "Heladeria" ->  {
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_helado))
                        card.setBackgroundColor(itemView.context.getColor(R.color.azul_fuerte))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.white))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.white))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.white))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.white))
                    }
                }
                btnNumero.backgroundTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.azul))
            }
            " Deuteranopia" ->{
                when (establecimiento){
                    "Bar" ->  {
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_bar))
                        card.setBackgroundColor(itemView.context.getColor(R.color.rojo))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.white))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.white))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.white))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.white))
                    }
                    "Restaurante" -> {
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_restaurante_negro))
                        card.setBackgroundColor(itemView.context.getColor(R.color.beige))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.black))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.black))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.black))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.black))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.black))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.black))
                    }
                    "Heladeria" ->  {
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_helado))
                        card.setBackgroundColor(itemView.context.getColor(R.color.azul_fuerte))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.white))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.white))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.white))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.white))
                    }
                }
                btnNumero.backgroundTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.azul))
            }
            " Tritanopia" ->{
                when (establecimiento){
                    "Bar" ->  {
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_bar))
                        card.setBackgroundColor(itemView.context.getColor(R.color.rojo))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.white))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.white))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.white))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.white))
                    }
                    "Restaurante" -> {
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_restaurante_negro))
                        card.setBackgroundColor(itemView.context.getColor(R.color.beige))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.black))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.black))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.black))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.black))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.black))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.black))
                    }
                    "Heladeria" ->  {
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_helado))
                        card.setBackgroundColor(itemView.context.getColor(R.color.azul_fuerte))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.white))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.white))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.white))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.white))
                    }
                }
                btnNumero.backgroundTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.azul))
            }
            " Acromatía" ->{
                when (establecimiento) {
                    "Bar" -> {
                        card.setBackgroundColor(itemView.context.getColor(R.color.negro_claro))
                        imagen.visibility = View.VISIBLE
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_bar))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.white))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.white))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.white))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.white))
                    }
                    "Restaurante" -> {
                        card.setBackgroundColor(itemView.context.getColor(R.color.negro_claro))
                        imagen.visibility = View.VISIBLE
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_restaurante))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.white))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.white))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.white))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.white))
                    }
                    "Heladeria" -> {
                        card.setBackgroundColor(itemView.context.getColor(R.color.negro_claro))
                        imagen.visibility = View.VISIBLE
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_helado))
                        mostrarHorario.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNombre.setTextColor(itemView.context.getColor(R.color.white))
                        mostrarNumero.setTextColor(itemView.context.getColor(R.color.white))
                        txtNombre.setTextColor(itemView.context.getColor(R.color.white))
                        txtHorario.setTextColor(itemView.context.getColor(R.color.white))
                        txtNumero.setTextColor(itemView.context.getColor(R.color.white))
                    }
                }
                btnNumero.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_telefono_negro))
                btnNumero.backgroundTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.white))
            }
            else ->
            {
                when (establecimiento){
                    "Bar" -> {
                        card.setBackgroundColor(itemView.context.getColor(R.color.verde_claro))
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_bar_negro))
                    }
                    "Restaurante" -> {
                        card.setBackgroundColor(itemView.context.getColor(R.color.beige))
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_restaurante_negro))
                    }
                    "Heladeria" -> {
                        card.setBackgroundColor(itemView.context.getColor(R.color.morado_claro))
                        imagen.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_icono_helado_negro))
                    }
                }
                btnNumero.backgroundTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.azul))
            }
        }

        dialog.show()
        return true
    }
}
