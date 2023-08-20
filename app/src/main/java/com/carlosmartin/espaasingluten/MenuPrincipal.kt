package com.carlosmartin.espaasingluten

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.carlosmartin.espaasingluten.R
import com.carlosmartin.espaasingluten.Componentes.ModoInversivo
import com.carlosmartin.espaasingluten.Componentes.Sponsored
import com.carlosmartin.espaasingluten.Componentes.Suscripcion
import com.carlosmartin.espaasingluten.Mapa.MostrarZonaRecycler
import com.carlosmartin.espaasingluten.Mapa.ZonasMenuMapa
import com.carlosmartin.espaasingluten.MapaInteractivo.MapaInteractivo
import com.carlosmartin.espaasingluten.ModoAdmin.ModoAdmin
import com.carlosmartin.espaasingluten.Productos.BuscarProductos
import com.carlosmartin.espaasingluten.Productos.MenuProductos
import com.carlosmartin.espaasingluten.Productos.RecyclerProductos
import com.carlosmartin.espaasingluten.TicketsYNoticias.MenuTicketsYNoticias
import com.carlosmartin.espaasingluten.TicketsYNoticias.Ticket
import java.security.MessageDigest
import java.text.Normalizer

import java.util.*


class MenuPrincipal : AppCompatActivity() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 123
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        ModoInversivo.setImmersiveMode(this)

        val btnIrMapa: Button = findViewById(R.id.btnMapa)
        val btnIrProducto: Button = findViewById(R.id.btnProductos)
        val btnIrResNoti: Button = findViewById(R.id.btnReseNoti)
        val btnRapidoBus: ImageView = findViewById(R.id.btnRapidoBuscar)
        val btnRapidoScn: ImageView = findViewById(R.id.btnRapidoScanner)
        val lytBuscar: ConstraintLayout = findViewById(R.id.lytBtnBuscar)
        val lytScanner: ConstraintLayout = findViewById(R.id.lytBtnScanner)
        val img: ImageView = findViewById(R.id.imageView)
        val btnMenu: ImageButton = findViewById(R.id.menu)
        val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)
        val opcionSeleccionada = preferences.getString("opcionSeleccionada", "")
        val preferencesAdmin = getSharedPreferences("modoAdminPreferencias", Context.MODE_PRIVATE)
        val opcionSeleccionadaAdmin = preferencesAdmin.getBoolean("modoAdmin", false)
        val btn1: Button = findViewById(R.id.btnModoAdmin1)
        val btn2: Button = findViewById(R.id.btnModoAdmin2)

        var btn1Clicked = false

        btn1.setOnClickListener {
            btn1Clicked = true
        }

        btn2.setOnClickListener {
            if (btn1Clicked) {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_modo_admin)
                dialog.setCancelable(true)

                val contraseña = dialog.findViewById<EditText>(R.id.ContraseñaAdmin)
                val btnAdmin: Button = dialog.findViewById(R.id.btnAdmin)

                btnAdmin.setOnClickListener {
                    val hashedPassword = sha256Hash(contraseña.text.toString())

                    if (hashedPassword == "8cdf914431e41b74815303918e3cbf6276a57d0f58161a18388f324c02a98d27") {
                        val preferencesAdmin = getSharedPreferences("modoAdminPreferencias", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = preferencesAdmin.edit()
                        editor.putString("hashedPassword", hashedPassword) // Guardar el hash en lugar de la contraseña original
                        editor.putBoolean("modoAdmin", true)
                        editor.apply()
                        recreate()
                        dialog.dismiss()
                    } else {
                        val preferencesAdmin = getSharedPreferences("modoAdminPreferencias", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = preferencesAdmin.edit()
                        editor.putString("hashedPassword", hashedPassword) // Guardar el hash en lugar de la contraseña original
                        editor.putBoolean("modoAdmin", false)
                        editor.apply()
                        recreate()
                        dialog.dismiss()
                    }
                }

                dialog.show()
            } else {
            }
        }




        // Solicitar permiso de ubicación al hacer clic en el botón del mapa
        btnIrMapa.setOnClickListener {
            requestLocationPermission()
            /*val intent = Intent (this, ZonasMenuMapa::class.java)
            this.startActivity(intent)*/
        }

        btnIrProducto.setOnClickListener {
            val intent = Intent(this, MenuProductos::class.java)
            startActivity(intent)
        }

        btnIrResNoti.setOnClickListener {

            if(opcionSeleccionadaAdmin){
                val intent = Intent(this, ModoAdmin::class.java)
                startActivity(intent)
            }else{
                val intent = Intent (this, Ticket::class.java)
                this.startActivity(intent)
            }
        }

        btnRapidoBus.setOnClickListener {
            val intent = Intent(this, RecyclerProductos::class.java)
            startActivity(intent)
        }

        btnRapidoScn.setOnClickListener {
            val intent = Intent(this, BuscarProductos::class.java)
            startActivity(intent)
        }

        btnMenu.setOnClickListener { showPopupMenu(it) }


        when (opcionSeleccionada) {
            " Acromatía" -> {
                btnIrMapa.setBackgroundColor(getColor(R.color.negro_claro))
                btnIrProducto.setBackgroundColor(getColor(R.color.negro_claro))
                btnIrResNoti.setBackgroundColor(getColor(R.color.negro_claro))
                lytBuscar.setBackgroundColor(getColor(R.color.black))
                lytScanner.setBackgroundColor(getColor(R.color.black))
                img.setImageDrawable(getDrawable(R.drawable.ic_fondo_icono_negro))
                btnRapidoBus.setImageDrawable(getDrawable(R.drawable.ic_icono_lupa_blanco))
                btnRapidoBus.setBackgroundColor(getColor(R.color.negro_claro))
                btnRapidoScn.setImageDrawable(getDrawable(R.drawable.barcode_blanco))
                btnMenu.setImageDrawable(getDrawable(R.drawable.ic_icono_menu_desp_negro))
            }
            else -> {
                btnIrMapa.setBackgroundColor(getColor(R.color.rojo))
                btnIrProducto.setBackgroundColor(getColor(R.color.rojo))
                btnIrResNoti.setBackgroundColor(getColor(R.color.rojo))
                lytBuscar.setBackgroundColor(getColor(R.color.rojo))
                lytScanner.setBackgroundColor(getColor(R.color.rojo))
                img.setImageDrawable(getDrawable(R.drawable.ic_fondo_icono))
                btnRapidoBus.setBackgroundColor(getColor(R.color.white))
                btnRapidoBus.setImageDrawable(getDrawable(R.drawable.ic_icono_lupa))
                btnRapidoScn.setImageDrawable(getDrawable(R.drawable.barcode))
                btnMenu.setImageDrawable(getDrawable(R.drawable.ic_icono_menu_desp))
            }
        }
    }

    private fun showPopupMenu(anchorView: android.view.View) {
        val popupMenu = PopupMenu(ContextThemeWrapper(this, R.style.MyPopupMenuStyle), anchorView)
        popupMenu.inflate(R.menu.menu_principal)

        val preferences = getSharedPreferences("PreferenciaDaltonico", Context.MODE_PRIVATE)


        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.opcionDaltonico -> {
                    val opcionesDaltonico = arrayListOf(" Normal", " Protanopia", " Deuteranopia", " Tritanopia", " Acromatía")
                    val builder = AlertDialog.Builder(this, R.style.AlertDialogStyle) // Aplicar el estilo personalizado
                    builder.setTitle("Selecciona una opción")
                    builder.setItems(opcionesDaltonico.toTypedArray()) { _, which ->
                        val selectedOption = opcionesDaltonico[which]
                        val editor: SharedPreferences.Editor = preferences.edit()
                        editor.putString("opcionSeleccionada", selectedOption)
                        editor.apply()
                        recreate()
                    }
                    builder.setNegativeButton("Cancelar", null)

                    val dialog = builder.create()
                    dialog.setCanceledOnTouchOutside(false) // No se puede hacer clic fuera del diálogo
                    dialog.show()
                    true
                }
                R.id.Sponsored -> {
                    val intent = Intent (this, Sponsored::class.java)
                    this.startActivity(intent)
                    true
                }
                /*R.id.Suscripcion -> {

                    val intent = Intent (this, Suscripcion::class.java)
                    this.startActivity(intent)
                    true
                    <item android:id="@+id/Suscripcion"
        android:title="Suscripción" />
                }*/
                R.id.Donativo ->{
                    val dialog = Dialog(this)
                    dialog.setContentView(R.layout.dialog_menu_donativo)
                    dialog.setCancelable(true)

                    val txtDonativo = dialog.findViewById<TextView>(R.id.txtDonativo)
                    val btnDonar = dialog.findViewById<Button>(R.id.btnDonar)
                    val opcionSeleccionada = preferences.getString("opcionSeleccionada", "")
                    if(opcionSeleccionada!!.contains("Acromatía")){
                        txtDonativo.setTextColor(getColor(R.color.negro_claro))
                        btnDonar.setBackgroundColor(getColor(R.color.negro_claro))
                    }else{
                        txtDonativo.setTextColor(getColor(R.color.azul))
                        btnDonar.setBackgroundColor(getColor(R.color.azul))
                    }

                    txtDonativo.text = getString(R.string.msg_donativvo)

                    btnDonar.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("https://www.paypal.com/donate/?hosted_button_id=WDUXV5YXXHGSU")
                        startActivity(intent);
                        dialog.dismiss()
                    }

                    dialog.show()
                    true
                }
                R.id.Informacion -> {
                    val dialog = Dialog(this)
                    dialog.setContentView(R.layout.dialog_menu_informacion)
                    dialog.setCancelable(true)

                    val txtInformacion = dialog.findViewById<TextView>(R.id.txtInformacion)
                    val btnAceptar = dialog.findViewById<Button>(R.id.btnAceptar)
                    val opcionSeleccionada = preferences.getString("opcionSeleccionada", "")
                    if(opcionSeleccionada!!.contains("Acromatía")){
                        txtInformacion.setTextColor(getColor(R.color.negro_claro))
                        btnAceptar.setBackgroundColor(getColor(R.color.negro_claro))
                    }else{
                        txtInformacion.setTextColor(getColor(R.color.azul))
                        btnAceptar.setBackgroundColor(getColor(R.color.azul))
                    }


                    btnAceptar.setOnClickListener {
                        dialog.dismiss()
                    }

                    dialog.show()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

        fun sha256Hash(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            val result = StringBuilder()

            for (byte in bytes) {
                val hexString = Integer.toHexString(0xFF and byte.toInt())
                if (hexString.length == 1) {
                    result.append('0')
                }
                result.append(hexString)
            }

            return result.toString()
        }

        // Método para solicitar el permiso de ubicación en tiempo de ejecución
        private fun requestLocationPermission() {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                checkLocationPermission()
            }
        }

        // Método para verificar si se tiene el permiso de ubicación y obtener la ubicación actual
        @SuppressLint("SetTextI18n", "SuspiciousIndentation")
        private fun checkLocationPermission() {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // El permiso de ubicación fue denegado
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            } else {
                val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val country = addresses[0].countryName
                        if (country == "España") {
                            val city = addresses[0].subAdminArea
                            val intent = Intent(this, ZonasMenuMapa::class.java)
                            intent.putExtra("nombreZona", city)
                            this.startActivity(intent)
                        } else {
                            // No está en España
                            Toast.makeText(this, "No funciona fuera de España", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // No se pudo obtener la ubicación
                        Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    // No se pudo obtener la ubicación
                    Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            }
        }

    fun removeAccents(input: String): String {
        val normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD)
        val pattern = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        return pattern.replace(normalizedString, "")
    }


        // Método para manejar la respuesta de la solicitud de permiso
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkLocationPermission()
                } else {
                    Toast.makeText(
                        this,
                        "Permiso de ubicación denegado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
}