package com.example.firebase

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.*

import com.example.firebase.datos.Producto

class AdaptadorProducto(private val context: Activity, var productos: List<Producto>) :
    ArrayAdapter<Producto?>(context, R.layout.producto_layout, productos) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // Método invocado tantas veces como elementos tenga la coleccion personas
        // para formar a cada item que se visualizara en la lista personalizada
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
        // optimizando las diversas llamadas que se realizan a este método
        // pues a partir de la segunda llamada el objeto view ya viene formado
        // y no sera necesario hacer el proceso de "inflado" que conlleva tiempo y
        // desgaste de bateria del dispositivo
        rowview = view ?: layoutInflater.inflate(R.layout.producto_layout, null)
        val tvNombreProducto = rowview!!.findViewById<TextView>(R.id.tvNombreProducto)
        val tvPrecio = rowview.findViewById<TextView>(R.id.tvPrecio)
        val tvIndicaciones = rowview!!.findViewById<TextView>(R.id.tvIndicaciones)
        val tvContraIndicaciones = rowview.findViewById<TextView>(R.id.tvContraIndicaciones)
        val tvFoto = rowview.findViewById<ImageView>(R.id.ivFotoProd)
        tvNombreProducto.text = "Nombre : " + productos[position].nombre
        tvPrecio.text = "Precio : " + productos[position].precio
        tvIndicaciones.text = "Indicaciones : " + productos[position].indicaciones
        tvContraIndicaciones.text = "Contraindicaciones : " + productos[position].contraindicaciones
        Glide.with(rowview).load(productos[position].foto).into(tvFoto)
        return rowview
    }
}