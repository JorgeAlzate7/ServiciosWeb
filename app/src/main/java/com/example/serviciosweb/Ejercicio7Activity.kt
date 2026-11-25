package com.example.serviciosweb

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Ejercicio7Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio7)

        // obtenemos referencias visuales
        val etTitulo = findViewById<EditText>(R.id.etLibroTitulo)
        val etAutor = findViewById<EditText>(R.id.etLibroAutor)
        val btnGet = findViewById<Button>(R.id.btnGetLibros)
        val btnPost = findViewById<Button>(R.id.btnPostLibro)
        val btnPut = findViewById<Button>(R.id.btnPutLibro)
        val btnDelete = findViewById<Button>(R.id.btnDeleteLibro)
        val tvResultado = findViewById<TextView>(R.id.tvResultadoLibros)

        // peticion get para listar los libros
        btnGet.setOnClickListener {
            obtenerLibros(tvResultado)
        }

        // peticion post para registrar un nuevo libro
        btnPost.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            val autor = etAutor.text.toString().trim()
            if (titulo.isNotEmpty() && autor.isNotEmpty()) {
                agregarLibro(titulo, autor, tvResultado)
            } else {
                tvResultado.text = "Ingrese titulo y autor del libro"
            }
        }

        // peticion put para actualizar un libro
        btnPut.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            val autor = etAutor.text.toString().trim()
            actualizarLibro(titulo, autor, tvResultado)
        }

        // peticion delete para eliminar un libro
        btnDelete.setOnClickListener {
            eliminarLibro(tvResultado)
        }
    }

    private fun obtenerLibros(tvResultado: TextView) {
        val url = "https://jsonplaceholder.typicode.com/posts"
        val queue = Volley.newRequestQueue(this)

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val sb = StringBuilder("LISTA DE LIBROS (GET):\n\n")
                // mostramos los primeros 5 libros
                val limite = if (response.length() > 5) 5 else response.length()
                for (i in 0 until limite) {
                    val item = response.getJSONObject(i)
                    val id = item.getInt("id")
                    val title = item.getString("title")
                    sb.append("ID: ").append(id).append("\nTitulo: ").append(title).append("\n---\n")
                }
                tvResultado.text = sb.toString()
            },
            { error ->
                tvResultado.text = "Error obteniendo libros " + error.message
            }
        )
        queue.add(request)
    }

    private fun agregarLibro(titulo: String, autor: String, tvResultado: TextView) {
        val url = "https://jsonplaceholder.typicode.com/posts"
        val queue = Volley.newRequestQueue(this)

        val datos = JSONObject()
        datos.put("title", titulo)
        datos.put("body", autor)
        datos.put("userId", 1)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            datos,
            { response ->
                val mensaje = "LIBRO AGREGADO (POST):\nID Generado: " + response.optString("id") + "\n" + response.toString()
                tvResultado.text = mensaje
            },
            { error ->
                tvResultado.text = "Error al agregar libro " + error.message
            }
        )
        queue.add(request)
    }

    private fun actualizarLibro(titulo: String, autor: String, tvResultado: TextView) {
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        val queue = Volley.newRequestQueue(this)

        val datos = JSONObject()
        val t = if (titulo.isEmpty()) "Libro Modificado" else titulo
        val a = if (autor.isEmpty()) "Autor Modificado" else autor
        datos.put("id", 1)
        datos.put("title", t)
        datos.put("body", a)
        datos.put("userId", 1)

        val request = JsonObjectRequest(
            Request.Method.PUT,
            url,
            datos,
            { response ->
                val mensaje = "LIBRO ACTUALIZADO (PUT):\n" + response.toString(2)
                tvResultado.text = mensaje
            },
            { error ->
                tvResultado.text = "Error al actualizar libro " + error.message
            }
        )
        queue.add(request)
    }

    private fun eliminarLibro(tvResultado: TextView) {
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        val queue = Volley.newRequestQueue(this)

        val request = StringRequest(
            Request.Method.DELETE,
            url,
            { response ->
                val mensaje = "LIBRO ELIMINADO (DELETE):\nEl libro con ID 1 ha sido eliminado correctamente del servidor"
                tvResultado.text = mensaje
            },
            { error ->
                tvResultado.text = "Error al eliminar libro " + error.message
            }
        )
        queue.add(request)
    }
}
