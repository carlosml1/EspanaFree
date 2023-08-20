package com.carlosmartin.espaasingluten.MapaInteractivo

import MarkerClickListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.carlosmartin.espaasingluten.R
import com.carlosmartin.espaasingluten.Componentes.ModoInversivo
import com.google.firebase.firestore.FirebaseFirestore
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapaInteractivo : AppCompatActivity() {

    private val zoomLevel = 15.0
    private var allMarkers: MutableList<Marker> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa_interactivo)

        ModoInversivo.setImmersiveMode(this)
        val latitud = intent.getDoubleExtra("latitud",0.0)
        val longitud = intent.getDoubleExtra("longitud",0.0)

        val mapView = findViewById<MapView>(R.id.map_view)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.controller.setZoom(zoomLevel)
        mapView.controller.setCenter(GeoPoint(latitud, longitud))

        mapView.mapOrientation = 0f
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        val searchEditText = findViewById<EditText>(R.id.search_edit_text)
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val searchTerm = searchEditText.text.toString()
                filterMarkersBySearchTerm(mapView, searchTerm)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario implementar este método
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchTerm = s?.toString() ?: ""
                filterMarkersBySearchTerm(mapView, searchTerm)
            }

            override fun afterTextChanged(s: Editable?) {
                // No es necesario implementar este método
            }
        })

        // Agregar marcadores según las coordenadas de la base de datos
        addMarkersFromDatabase(mapView)

        Configuration.getInstance().load(applicationContext, getPreferences(MODE_PRIVATE))
    }

    private fun addMarkersFromDatabase(mapView: MapView) {
        val db = FirebaseFirestore.getInstance()

        db.collection("Malaga")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val latitud = document.getString("Latitud")
                    val longitud = document.getString("Longitud")
                    if (latitud != null && longitud != null) {
                        val marker = Marker(mapView)
                        marker.position = GeoPoint(latitud.toDouble(), longitud.toDouble())
                        marker.title = document.id // Use the document ID as the marker's title

                        // Obtener la información relevante del marcador desde la base de datos
                        val horario = document.getString("Horario") ?: ""
                        val establecimiento = document.getString("Establecimiento") ?: ""
                        val numero = document.getLong("Numero") ?: ""

                        // Usar la clase personalizada MarkerClickListener para manejar los clics en el marcador
                        marker.setOnMarkerClickListener(
                            MarkerClickListener(
                                document.id ?: "",
                                horario,
                                establecimiento,
                                numero.toString()
                            )
                        )

                        allMarkers.add(marker) // Add the marker to the list
                    }
                }
                // Add all markers to the map after fetching
                mapView.overlays.addAll(allMarkers)
            }
            .addOnFailureListener { exception ->
                // Manejar errores si la consulta a la base de datos falla
            }
    }

    private fun filterMarkersBySearchTerm(mapView: MapView, searchTerm: String) {
        mapView.overlays.removeAll(allMarkers) // Remove all markers from the map

        // Filter the markers based on the search term
        val filteredMarkers = allMarkers.filter { marker ->
            marker.title?.contains(searchTerm, ignoreCase = true) == true
        }

        // Add the filtered markers back to the map
        mapView.overlays.addAll(filteredMarkers)

        mapView.invalidate() // Refresh the map display
    }

    private fun getClosestMarker(mapView: MapView, point: GeoPoint): Marker? {
        var closestMarker: Marker? = null
        var closestDistance: Double = Double.MAX_VALUE

        for (marker in allMarkers) {
            val distance = point.distanceToAsDouble(marker.position)
            if (distance < closestDistance) {
                closestMarker = marker
                closestDistance = distance
            }
        }

        return closestMarker
    }
}
