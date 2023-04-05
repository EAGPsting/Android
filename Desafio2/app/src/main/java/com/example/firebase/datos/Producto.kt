package com.example.firebase.datos

class Producto {
    fun key(key: String?) {
    }

    var precio: String? = null
    var nombre: String? = null
    var indicaciones: String? = null
    var contraindicaciones: String? = null
    var foto: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(nombre: String?, indicaciones: String?, contraindicaciones: String?, precio: String?, foto: String?) {
        this.nombre = nombre
        this.indicaciones = indicaciones
        this.contraindicaciones = contraindicaciones
        this.precio = precio
        this.foto = foto
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "indicaciones" to indicaciones,
            "contraindicaciones" to contraindicaciones,
            "precio" to precio,
            "foto" to foto,
            "key" to key,
            "per" to per
        )
    }
}