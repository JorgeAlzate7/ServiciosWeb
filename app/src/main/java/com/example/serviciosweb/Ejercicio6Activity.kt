package com.example.serviciosweb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class Ejercicio6Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio6)

        val rvGaleria = findViewById<RecyclerView>(R.id.rvGaleria)
        rvGaleria.layoutManager = LinearLayoutManager(this)

        cargarGaleria(rvGaleria)
    }

    private fun cargarGaleria(rvGaleria: RecyclerView) {
        val url = "https://rickandmortyapi.com/api/character"
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    val resultados = response.getJSONArray("results")
                    val lista = mutableListOf<CharacterModel>()

                    for (i in 0 until resultados.length()) {
                        val item = resultados.getJSONObject(i)
                        val nombre = item.getString("name")
                        val especie = item.getString("species")
                        val imagenUrl = item.getString("image")

                        lista.add(CharacterModel(nombre, especie, imagenUrl))
                    }

                    // asignamos el adaptador al recyclerview con la accion click
                    val adapter = CharacterAdapter(lista) { personaje ->
                        val intent = Intent(this, DetalleCharacterActivity::class.java)
                        intent.putExtra("nombre", personaje.nombre)
                        intent.putExtra("especie", personaje.especie)
                        intent.putExtra("imagenUrl", personaje.imagenUrl)
                        startActivity(intent)
                    }

                    rvGaleria.adapter = adapter
                } catch (e: Exception) {
                    // error al procesar el json
                }
            },
            { error ->
                // error en la conexion
            }
        )

        queue.add(jsonObjectRequest)
    }
}
