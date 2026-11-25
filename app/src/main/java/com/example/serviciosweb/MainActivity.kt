package com.example.serviciosweb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // vinculamos los botones del menu principal
        val btnEj1 = findViewById<Button>(R.id.btnEj1)
        val btnEj2 = findViewById<Button>(R.id.btnEj2)
        val btnEj3 = findViewById<Button>(R.id.btnEj3)
        val btnEj4 = findViewById<Button>(R.id.btnEj4)
        val btnEj5 = findViewById<Button>(R.id.btnEj5)
        val btnEj6 = findViewById<Button>(R.id.btnEj6)
        val btnEj7 = findViewById<Button>(R.id.btnEj7)

        // configuramos la navegacion a cada actividad usando intents explicitos
        btnEj1.setOnClickListener {
            startActivity(Intent(this, Ejercicio1Activity::class.java))
        }

        btnEj2.setOnClickListener {
            startActivity(Intent(this, Ejercicio2Activity::class.java))
        }

        btnEj3.setOnClickListener {
            startActivity(Intent(this, Ejercicio3Activity::class.java))
        }

        btnEj4.setOnClickListener {
            startActivity(Intent(this, Ejercicio4Activity::class.java))
        }

        btnEj5.setOnClickListener {
            startActivity(Intent(this, Ejercicio5Activity::class.java))
        }

        btnEj6.setOnClickListener {
            startActivity(Intent(this, Ejercicio6Activity::class.java))
        }

        btnEj7.setOnClickListener {
            startActivity(Intent(this, Ejercicio7Activity::class.java))
        }
    }
}
