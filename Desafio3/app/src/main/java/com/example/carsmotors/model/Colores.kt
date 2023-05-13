package com.example.carsmotors.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotors.db.HelperDB

class Colores(context: Context) {

    private var helper: HelperDB?= null
    private var db: SQLiteDatabase?= null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {

        val TABLE_NAME_COLORES = "colores"

        val COL_ID = "idcolores"
        val COL_DESCRIPCION = "descripcion"

        val CREATE_TABLE_COLORES = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_COLORES + "("
                        + COL_ID + "integer primary key autoincrement,"
                        + COL_DESCRIPCION + "varchar(45) NOT NULL);"
                )
    }

    fun generarContentValues(descripcion: String?): ContentValues? {
        val valores = ContentValues()
        valores.put(COL_DESCRIPCION, descripcion)
        return valores
    }

    fun insertarValuesDefault() {
        val colores = arrayOf(
            "Rojo fuego: un rojo vibrante y llamativo.",
            "Blanco perla: un blanco con un brillo nacarado y sofisticado.",
            "Azul eléctrico: un azul intenso y brillante.",
            "Gris carbón: un gris oscuro y elegante que se asemeja al color del carbón.",
            "Gris plata: un gris metálico y sofisticado.",
            "Verde esmeralda: un verde brillante y lujoso.",
            "Amarillo sol: un amarillo brillante y alegre.",
            "Naranja cálido: un naranja brillante y acogedor.",
            "Morado profundo: un morado oscuro y misterioso.",
            "Gris grafito: un gris oscuro y moderno."
        )

        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? =
            db!!.query(TABLE_NAME_COLORES, columns, null, null, null, null, null)

        if (cursor == null || cursor!!.count <= 0) {
            for (item in colores) {
                db!!.insert(TABLE_NAME_COLORES, null, generarContentValues(item))
            }
        }
    }
    fun addNewColor(nombre: String){
        db!!.insert(
            Colores.CREATE_TABLE_COLORES, null, generarContentValues(nombre)
        )
    }
    fun showAllColores(): Cursor? {
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        return db!!.query(
            TABLE_NAME_COLORES, columns , null, null, null, null, "$COL_DESCRIPCION MRC"
        )
    }

    fun searchID(descripcion: String): Int?{
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_COLORES, columns, "$COL_DESCRIPCION=?", arrayOf(descripcion.toString()), null,null,null
        )

        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }

    fun searchDescripcion(id: Int): String?{
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_COLORES, columns, "$COL_ID=?", arrayOf(id.toString()),null, null, null
        )

        cursor!!.moveToFirst()
        return cursor!!.getString(1)
    }
    fun deleteColor(id: Int){
        db!!.delete(Colores.TABLE_NAME_COLORES, "${Colores.COL_ID}=?", arrayOf(id.toString()))
    }

    fun updateColor(
        id: Int,
        nombre: String
    ){
        db!!.update(
            Colores.TABLE_NAME_COLORES, generarContentValues(nombre),
            "${Colores.COL_ID}=?", arrayOf(id.toString())
        )
    }
}