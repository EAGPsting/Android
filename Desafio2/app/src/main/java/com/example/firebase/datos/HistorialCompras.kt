package com.example.firebase.datos

class HistorialCompras {
    fun key(key: String?) {
    }

    var precio: String? = null
    var nombre: String? = null
    var IVA: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(nombre: String?, precio: String?, IVA: String?) {
        this.nombre = nombre
        this.precio = precio
        this.IVA= IVA
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "precio" to precio,
            "IVA" to IVA,
            "key" to key,
            "per" to per
        )
    }
}