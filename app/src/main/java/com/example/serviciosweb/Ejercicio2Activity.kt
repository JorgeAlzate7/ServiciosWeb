package com.example.serviciosweb

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Ejercicio2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio2)

        // obtenemos las referencias de la interfaz
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val btnEnviarPost = findViewById<Button>(R.id.btnEnviarPost)
        val tvResultadoPost = findViewById<TextView>(R.id.tvResultadoPost)

        // evento click para realizar el post
        btnEnviarPost.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()

            if (nombre.isNotEmpty() && correo.isNotEmpty()) {
                enviarPost(nombre, correo, tvResultadoPost)
            } else {
                tvResultadoPost.text = "Por favor ingrese nombre y correo"
            }
        }
    }

    private fun enviarPost(nombre: String, correo: String, tvResultado: TextView) {
        // url de la api jsonplaceholder
        val url = "https://jsonplaceholder.typicode.com/posts"

        // construimos el objeto json a enviar
        val datosJson = JSONObject()
        datosJson.put("name", nombre)
        datosJson.put("email", correo)

        // creamos la cola de volley
        val queue = Volley.newRequestQueue(this)

        // creamos la peticion jsonobjectrequest de tipo post
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            datosJson,
            { response ->
                try {
                    // obtenemos el id asignado por el servidor
                    val idGenerado = response.optString("id", "desconocido")
                    val mensaje = "Usuario creado con ID: " + idGenerado + "\n\nRespuesta JSON:\n" + response.toString()
                    tvResultado.text = mensaje
                } catch (e: Exception) {
                    tvResultado.text = "Error procesando respuesta"
                }
            },
            { error ->
                tvResultado.text = "Error al enviar datos " + error.message
            }
        )

        // agregamos la peticion a la cola
        queue.add(jsonObjectRequest)
    }
}
