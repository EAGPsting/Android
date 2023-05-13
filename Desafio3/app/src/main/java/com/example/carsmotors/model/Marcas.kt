package com.example.carsmotors.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotors.db.HelperDB

class Marcas(context: Context) {

    private var helper: HelperDB?= null
    private var db: SQLiteDatabase?= null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {

        val TABLE_NAME_MARCAS = "marcas"

        val COL_ID = "idmarcas"
        val COL_NOMBRE = "nombre"

        val CREATE_TABLE_MARCAS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_MARCAS + "("
                + COL_ID + "integer primary key autoincrement,"
                + COL_NOMBRE + "varchar(45) NOT NULL);"
                )
    }

    fun generarContentValues(nombre: String?): ContentValues? {
        val valores = ContentValues()
        valores.put(COL_NOMBRE, nombre)
        return valores
    }
    fun addNewMarca(nombre: String){
        db!!.insert(
            Marcas.TABLE_NAME_MARCAS, null, generarContentValues(nombre)
        )
    }
    fun insertarValuesDefault(){
        val marcas = arrayOf(
            "Toyota",
            "Ford",
            "Honda",
            "Chevrolet",
            "Nissan",
            "Volkswagen",
            "BMW",
            "Mercedes-Benz",
            "Audi",
            "Hyundai"
        )

        val columns = arrayOf(COL_ID, COL_NOMBRE)
        var cursor: Cursor? = db!!.query(TABLE_NAME_MARCAS, columns,null,null,null,null,null)

        if (cursor == null || cursor!!.count <= 0){
            for (item in marcas){
                db!!.insert(TABLE_NAME_MARCAS, null, generarContentValues(item))
            }
        }
    }

    fun showAllMarcas(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        return db!!.query(
            TABLE_NAME_MARCAS, columns , null, null, null, null, "$COL_NOMBRE MRC"
        )
    }

    fun searchID(nombre: String): Int?{
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_MARCAS, columns, "$COL_NOMBRE=?", arrayOf(nombre.toString()), null,null,null
        )

        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }

    fun searchNombre(id: Int): String?{
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_MARCAS, columns, "$COL_ID=?", arrayOf(id.toString()),null, null, null
        )

        cursor!!.moveToFirst()
        return cursor!!.getString(1)
    }
    fun deleteMarca(id: Int){
        db!!.delete(Marcas.TABLE_NAME_MARCAS, "${Marcas.COL_ID}=?", arrayOf(id.toString()))
    }

    fun updateMarca(
        id: Int,
        nombre: String
    ){
        db!!.update(
            Marcas.TABLE_NAME_MARCAS, generarContentValues(nombre),
            "${Marcas.COL_ID}=?", arrayOf(id.toString())
        )
    }
}