package com.example.carsmotors.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotors.db.HelperDB

class FavoritoAutomovil(context: Context) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {

        val TABLE_NAME_FAVORITO_AUTOMOVIL = "favorito_automovil"

        val COL_ID = "idfavoritoautomovil"
        val COL_IDUSUARIO = "idusuario"
        val COL_IDAUTOMOVIL = "idautomovil"
        val COL_FECHA_AGREGADO = "fecha_agregado"


        val CREATE_TABLE_FAVORITOS_AUTOMOVIL = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FAVORITO_AUTOMOVIL + "("
                        + COL_ID + "integer primary key autoincrement,"
                        + COL_IDUSUARIO + "integer NOT NULL,"
                        + COL_IDAUTOMOVIL + "integer NOT NULL,"
                        + COL_FECHA_AGREGADO + "timestamp NOT NULL,"
                        + "FOREIGN KEY (idautomovil) REFERENCES automovil(idautomovil)"
                        + "FOREIGN KEY (idusuario) REFERENCES usuario(idusuario));"

                )
    }

    fun generarContentValues(
        idusuario: Int?,
        idautomovil: Int?,
        fecha_agregado: Int?
    ): ContentValues? {

        val valores = ContentValues()
        valores.put(COL_IDUSUARIO, idusuario)
        valores.put(COL_IDAUTOMOVIL, idautomovil)
        valores.put(COL_FECHA_AGREGADO, fecha_agregado)
        return valores
    }

    fun addNewFavoritoAutomovil(idusuario: Int?, idautomovil: Int?, fecha_agregado: Int?){
        db!!.insert(
            TABLE_NAME_FAVORITO_AUTOMOVIL, null, generarContentValues(idusuario,idautomovil, fecha_agregado)
        )
    }

    fun deleteFavoritoAutomovil(id: Int){
        db!!.delete(TABLE_NAME_FAVORITO_AUTOMOVIL, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun updateFavoritoAutomovil(
        id: Int,
        idusuario: Int?,
        idautomovil: Int?,
        fecha_agregado: Int?
    ){
        db!!.update(
            TABLE_NAME_FAVORITO_AUTOMOVIL, generarContentValues(idusuario, idautomovil, fecha_agregado),
            "$COL_ID=?", arrayOf(id.toString())
        )
    }

    fun searchAutomovil(id: Int): Cursor? {
        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_IDAUTOMOVIL, COL_FECHA_AGREGADO)
        return db!!.query(
            TABLE_NAME_FAVORITO_AUTOMOVIL, columns, "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
        }

    fun searchAutomovilAll(): Cursor? {
        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_IDAUTOMOVIL, COL_FECHA_AGREGADO)
        return db!!.query(
            TABLE_NAME_FAVORITO_AUTOMOVIL, columns, null, null, null, null,"${FavoritoAutomovil.COL_FECHA_AGREGADO} MRC"
        )
    }



}