package com.example.serviciosweb

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Ejercicio4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio4)

        // obtenemos referencias de los botones y textos
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val tvResultadoDelete = findViewById<TextView>(R.id.tvResultadoDelete)

        // evento para ejecutar la peticion delete
        btnEliminar.setOnClickListener {
            eliminarRecurso(tvResultadoDelete)
        }
    }

    private fun eliminarRecurso(tvResultado: TextView) {
        val url = "https://jsonplaceholder.typicode.com/posts/1"

        val queue = Volley.newRequestQueue(this)

        // creamos la peticion stringrequest con metodo delete
        val stringRequest = StringRequest(
            Request.Method.DELETE,
            url,
            { response ->
                // si la respuesta es exitosa mostramos el mensaje de confirmacion
                val mensaje = "Usuario eliminado correctamente\n\nRespuesta del servidor: " + response
                tvResultado.text = mensaje
            },
            { error ->
                tvResultado.text = "Error al eliminar usuario " + error.message
            }
        )

        queue.add(stringRequest)
    }
}
