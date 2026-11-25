package com.example.serviciosweb

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Ejercicio3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio3)

        // obtenemos las referencias de la vista
        val btnPut = findViewById<Button>(R.id.btnPut)
        val btnPatch = findViewById<Button>(R.id.btnPatch)
        val tvResultado = findViewById<TextView>(R.id.tvResultadoPutPatch)

        // evento para el boton put
        btnPut.setOnClickListener {
            ejecutarPut(tvResultado)
        }

        // evento para el boton patch
        btnPatch.setOnClickListener {
            ejecutarPatch(tvResultado)
        }
    }

    private fun ejecutarPut(tvResultado: TextView) {
        val url = "https://jsonplaceholder.typicode.com/posts/1"

        // put reemplaza todo el objeto por lo que enviamos titulo y cuerpo
        val datosJson = JSONObject()
        datosJson.put("id", 1)
        datosJson.put("title", "Titulo totalmente reemplazado con PUT")
        datosJson.put("body", "Cuerpo totalmente reemplazado con PUT")
        datosJson.put("userId", 1)

        enviarPeticion(Request.Method.PUT, url, datosJson, "PUT", tvResultado)
    }

    private fun ejecutarPatch(tvResultado: TextView) {
        val url = "https://jsonplaceholder.typicode.com/posts/1"

        // patch solo modifica un campo por lo que solo enviamos titulo
        val datosJson = JSONObject()
        datosJson.put("title", "Titulo modificado con PATCH")

        enviarPeticion(Request.Method.PATCH, url, datosJson, "PATCH", tvResultado)
    }

    private fun enviarPeticion(metodo: Int, url: String, datos: JSONObject, tipo: String, tvResultado: TextView) {
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            metodo,
            url,
            datos,
            { response ->
                val texto = "Respuesta de la peticion " + tipo + ":\n\n" + response.toString(2)
                tvResultado.text = texto
            },
            { error ->
                tvResultado.text = "Error en la peticion " + tipo + " " + error.message
            }
        )

        queue.add(jsonObjectRequest)
    }
}
