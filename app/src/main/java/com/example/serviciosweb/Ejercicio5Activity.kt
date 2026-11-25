package com.example.serviciosweb

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class Ejercicio5Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio5)

        // obtenemos referencias de los elementos visuales
        val btnCargar = findViewById<Button>(R.id.btnCargarRick)
        val ivPersonaje = findViewById<ImageView>(R.id.ivPersonaje)
        val tvNombreEspecie = findViewById<TextView>(R.id.tvNombreEspecie)

        // asignamos el listener al boton
        btnCargar.setOnClickListener {
            cargarPersonaje(ivPersonaje, tvNombreEspecie)
        }
    }

    private fun cargarPersonaje(ivPersonaje: ImageView, tvNombreEspecie: TextView) {
        val url = "https://rickandmortyapi.com/api/character"
        val queue = Volley.newRequestQueue(this)

        // peticion jsonobjectrequest para obtener los datos del personaje
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    // obtenemos el primer objeto del arreglo de resultados
                    val resultados = response.getJSONArray("results")
                    val primerPersonaje = resultados.getJSONObject(0)

                    val nombre = primerPersonaje.getString("name")
                    val especie = primerPersonaje.getString("species")
                    val urlImagen = primerPersonaje.getString("image")

                    // mostramos el nombre y la especie en el textview
                    val textoInfo = "Nombre: " + nombre + "\nEspecie: " + especie
                    tvNombreEspecie.text = textoInfo

                    // cargamos la imagen utilizando imagerequest de volley
                    descargarImagen(urlImagen, ivPersonaje)
                } catch (e: Exception) {
                    tvNombreEspecie.text = "Error al parsear datos de Rick y Morty"
                }
            },
            { error ->
                tvNombreEspecie.text = "Error de conexion " + error.message
            }
        )

        queue.add(jsonObjectRequest)
    }

    private fun descargarImagen(urlImagen: String, ivPersonaje: ImageView) {
        val queue = Volley.newRequestQueue(this)

        // creamos una imagerequest para descargar el bitmap
        val imageRequest = ImageRequest(
            urlImagen,
            { bitmap ->
                // asignamos el bitmap descargado al imageview
                ivPersonaje.setImageBitmap(bitmap)
            },
            0,
            0,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,
            { error ->
                // si ocurre un error al descargar la imagen
            }
        )

        queue.add(imageRequest)
    }
}
