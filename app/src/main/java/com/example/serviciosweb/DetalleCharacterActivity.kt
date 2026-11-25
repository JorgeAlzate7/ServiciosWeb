package com.example.serviciosweb

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley

class DetalleCharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_character)

        // obtenemos las referencias visuales
        val ivDetalleImagen = findViewById<ImageView>(R.id.ivDetalleImagen)
        val tvDetalleNombre = findViewById<TextView>(R.id.tvDetalleNombre)
        val tvDetalleEspecie = findViewById<TextView>(R.id.tvDetalleEspecie)

        // leemos los datos enviados a traves del intent
        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val especie = intent.getStringExtra("especie") ?: "Sin especie"
        val imagenUrl = intent.getStringExtra("imagenUrl") ?: ""

        // asignamos los datos de texto
        tvDetalleNombre.text = nombre
        val textoEspecie = "Especie: " + especie
        tvDetalleEspecie.text = textoEspecie

        // descargamos la imagen en alta calidad si existe la url
        if (imagenUrl.isNotEmpty()) {
            descargarImagenGrande(imagenUrl, ivDetalleImagen)
        }
    }

    private fun descargarImagenGrande(url: String, ivDetalleImagen: ImageView) {
        val queue = Volley.newRequestQueue(this)

        val imageRequest = ImageRequest(
            url,
            { bitmap ->
                ivDetalleImagen.setImageBitmap(bitmap)
            },
            0,
            0,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,
            { error ->
                // si ocurre un error en la descarga
            }
        )

        queue.add(imageRequest)
    }
}
