package com.example.practica2estebangamez

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


lateinit var nombre: EditText
lateinit var numero1: EditText
lateinit var numero2: EditText
lateinit var enviar: Button
lateinit var resultado: TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nombre = findViewById(R.id.TxtNombre)
        numero1 = findViewById(R.id.TxtNumero1)
        numero2 = findViewById(R.id.TxtNumero2)
        enviar = findViewById(R.id.BtnEnviar)
        resultado=findViewById(R.id.LlbResultado)

        enviar.setOnClickListener{
            sumar()
        }
    }

    private fun sumar() {
        val valor1: String = numero1.text.toString()
        val valor2: String = numero2.text.toString()
        val nro1 = valor1.toFloat()
        val nro2 = valor2.toFloat()
        val suma = nro1 + nro2
        val resu = suma.toString()
        resultado.setText(resu)
    }

}