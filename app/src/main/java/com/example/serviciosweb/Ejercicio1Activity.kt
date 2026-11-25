package com.example.serviciosweb

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class Ejercicio1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio1)

        // obtenemos referencias de la vista
        val btnConsultar = findViewById<Button>(R.id.btnConsultar)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        // asignamos el evento click al boton
        btnConsultar.setOnClickListener {
            consultarUsuarios(tvResultado)
        }
    }

    private fun consultarUsuarios(tvResultado: TextView) {
        // url de la api publica para obtener usuarios
        val url = "https://jsonplaceholder.typicode.com/users"

        // inicializamos la cola de peticiones de volley
        val queue = Volley.newRequestQueue(this)

        // creamos la peticion stringrequest get
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                try {
                    // procesamos la respuesta json
                    val jsonArray = JSONArray(response)
                    val nombres = StringBuilder()

                    // recorremos cada objeto usuario y extraemos la propiedad name
                    for (i in 0 until jsonArray.length()) {
                        val usuario = jsonArray.getJSONObject(i)
                        val nombre = usuario.getString("name")
                        nombres.append(nombre).append("\n")
                    }

                    // mostramos los nombres obtenidos en pantalla
                    tvResultado.text = nombres.toString()
                } catch (e: Exception) {
                    tvResultado.text = "Error al procesar la respuesta json"
                }
            },
            { error ->
                tvResultado.text = "Error en la conexion " + error.message
            }
        )

        // agregamos la peticion a la cola
        queue.add(stringRequest)
    }
}
