package com.example.carsmotors.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotors.db.HelperDB

class TipoAutomovil(context: Context) {
    private var helper: HelperDB?= null
    private var db: SQLiteDatabase?= null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {

        val TABLE_NAME_TIPO_AUTOMOVIL = "tipo_automovil"

        val COL_ID = "idtipoautomovil"
        val COL_DESCRIPCION = "descripcion"

        val CREATE_TABLE_TIPO_AUTOMOVIL = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TIPO_AUTOMOVIL + "("
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
        val tipoautomovil = arrayOf(

            "Sedán: automóvil con cuatro puertas y un techo fijo.",
            "Minivan: vehículo con una carrocería alta y espaciosa diseñado para llevar a varias personas y sus pertenencias con comodidad.",
            "Hatchback: automóvil compacto con un diseño de carrocería de dos o cuatro puertas, con un portón trasero que se abre hacia arriba para acceder al maletero.",
            "Deportivo: automóvil con un diseño aerodinámico, una suspensión deportiva y un motor potente diseñado para la velocidad y el rendimiento.",
            "Descapotable: automóvil con techo retráctil que se puede quitar o plegar para disfrutar de la conducción al aire libre.",
            "Pickup: vehículo con una cabina para pasajeros y una plataforma de carga en la parte trasera, diseñado para transportar carga pesada y remolcar objetos."
        )

        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? =
            db!!.query(TABLE_NAME_TIPO_AUTOMOVIL, columns, null, null, null, null, null)

        if (cursor == null || cursor!!.count <= 0) {
            for (item in tipoautomovil) {
                db!!.insert(TABLE_NAME_TIPO_AUTOMOVIL, null, generarContentValues(item))
            }
        }
    }
    fun addNewTipo(nombre: String){
        db!!.insert(
            TipoAutomovil.TABLE_NAME_TIPO_AUTOMOVIL, null, generarContentValues(nombre)
        )
    }
    fun showAlltiposVehiculos(): Cursor? {
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        return db!!.query(
            TABLE_NAME_TIPO_AUTOMOVIL, columns , null, null, null, null, "$COL_DESCRIPCION MRC"
        )
    }

    fun searchID(descripcion: String): Int?{
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_TIPO_AUTOMOVIL, columns, "$COL_DESCRIPCION=?", arrayOf(descripcion.toString()), null,null,null
        )

        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }

    fun searchDescripcion(id: Int): String?{
        val columns = arrayOf(COL_ID, COL_DESCRIPCION)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME_TIPO_AUTOMOVIL, columns, "$COL_ID=?", arrayOf(id.toString()),null, null, null
        )

        cursor!!.moveToFirst()
        return cursor!!.getString(1)
    }
    fun deleteTipo(id: Int){
        db!!.delete(TipoAutomovil.TABLE_NAME_TIPO_AUTOMOVIL, "${TipoAutomovil.COL_ID}=?", arrayOf(id.toString()))
    }

    fun updateTipo(
        id: Int,
        nombre: String
    ){
        db!!.update(
            TipoAutomovil.TABLE_NAME_TIPO_AUTOMOVIL, generarContentValues(nombre),
            "${TipoAutomovil.COL_ID}=?", arrayOf(id.toString())
        )
    }
}