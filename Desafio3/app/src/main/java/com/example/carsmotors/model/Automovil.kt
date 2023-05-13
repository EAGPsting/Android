package com.example.carsmotors.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotors.db.HelperDB


class Automovil(context: Context) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {

        val TABLE_NAME_AUTOMOVIL = "automovil"

        val COL_ID = "idautomovil"
        val COL_MODELO = "modelo"
        val COL_NUMERO_VIN = "numero_vin"
        val COL_NUMERO_CHASIS = "numero_chasis"
        val COL_NUMERO_MOTOR = "numero_motor"
        val COL_NUMERO_ASIENTOS = "numero_asientos"
        val COL_ANIO = "año"
        val COL_CAPACIDAD_ASIENTOS = "capacidad_asientos"
        val COL_PRECIO = "precio"
        val URI_IMG = "uri_img"
        val COL_DESCRIPCION = "descripcion"
        val COL_IDMARCAS = "idmarcas"
        val COL_IDTIPOAUTOMOVIL = "idtipoautomovil"
        val COL_IDCOLORES = "idcolores"


        val CREATE_TABLE_AUTOMOVIL = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_AUTOMOVIL + "("
                        + COL_ID + "integer primary key autoincrement,"
                        + COL_IDMARCAS + "integer NOT NULL,"
                        + COL_IDTIPOAUTOMOVIL + "integer NOT NULL,"
                        + COL_IDCOLORES + "integer NOT NULL,"
                        + COL_MODELO + "varchar(45) NOT NULL,"
                        + COL_NUMERO_VIN + "varchar(45) NOT NULL),"
                        + COL_NUMERO_CHASIS + "varchar(45) NOT NULL),"
                        + COL_NUMERO_MOTOR + "varchar(45) NOT NULL),"
                        + COL_NUMERO_ASIENTOS + "integer,"
                        + COL_ANIO + "year,"
                        + COL_CAPACIDAD_ASIENTOS + "integer,"
                        + COL_PRECIO + "decimal(10,2) NOT NULL,"
                        + URI_IMG + "varchar(45) NOT NULL,"
                        + COL_DESCRIPCION + "varchar(45) NOT NULL,"
                        + "FOREIGN KEY (idmarcas) REFERENCES marcas(idmarcas)"
                        + "FOREIGN KEY (idtipoautomovil) REFERENCES tipo_automovil(idtipoautomovil)"
                        + "FOREIGN KEY (idcolores) REFERENCES colores(idcolores));"
                )
    }

    fun generarContentValues(
        idmarcas: Int?,
        idtipoautomovil: Int?,
        idcolores: Int?,
        modelo: String?,
        numero_vin: String?,
        numero_chasis: String?,
        numero_motor: String?,
        numero_asientos: Int?,
        año: Int?,
        capacidad_asientos: Int?,
        precio: Double?,
        uri_img: String?,
        descripcion: String?
    ): ContentValues? {

        val valores = ContentValues()
        valores.put(Automovil.COL_IDMARCAS, idmarcas)
        valores.put(Automovil.COL_IDTIPOAUTOMOVIL, idtipoautomovil)
        valores.put(Automovil.COL_IDCOLORES, idcolores)
        valores.put(Automovil.COL_MODELO, modelo)
        valores.put(Automovil.COL_NUMERO_VIN, numero_vin)
        valores.put(Automovil.COL_NUMERO_CHASIS, numero_chasis)
        valores.put(Automovil.COL_NUMERO_MOTOR, numero_motor)
        valores.put(Automovil.COL_NUMERO_ASIENTOS, numero_asientos)
        valores.put(Automovil.COL_ANIO, año)
        valores.put(Automovil.COL_CAPACIDAD_ASIENTOS, capacidad_asientos)
        valores.put(Automovil.COL_PRECIO, precio)
        valores.put(Automovil.URI_IMG, uri_img)
        valores.put(Automovil.COL_DESCRIPCION, descripcion)
        return valores
    }


    fun addNewAutomovil(idmarcas: Int?,idtipoautomovil: Int?, idcolores: Int?, modelo: String?, numero_vin: String?,numero_chasis: String?, numero_motor: String?,numero_asientos: Int?,año: Int?,capacidad_asientos: Int?, precio: Double?, uri_img: String?, descripcion: String?){
        db!!.insert(
            TABLE_NAME_AUTOMOVIL, null, generarContentValues(idmarcas, idtipoautomovil, idcolores, modelo, numero_vin, numero_chasis, numero_motor, numero_asientos, año,capacidad_asientos,precio,uri_img,descripcion)
        )
    }

    fun deleteAutomovil(id: Int){
        db!!.delete(TABLE_NAME_AUTOMOVIL, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun updateAutomovil(
        id: Int,
        idmarcas: Int?,
        idtipoautomovil: Int?,
        idcolores: Int?,
        modelo: String?,
        numero_vin: String?,
        numero_chasis: String?,
        numero_motor: String?,
        numero_asientos: Int?,
        año: Int?,
        capacidad_asientos: Int?,
        precio: Double?,
        uri_img: String?,
        descripcion: String?
    ){
        db!!.update(
            TABLE_NAME_AUTOMOVIL, generarContentValues(idmarcas, idtipoautomovil, idcolores, modelo, numero_vin, numero_chasis, numero_motor, numero_asientos, año, capacidad_asientos, precio, uri_img, descripcion),
            "$COL_ID=?", arrayOf(id.toString())
        )
    }

    fun searchAutomovil(id: Int): Cursor? {
        val columns = arrayOf(COL_ID, COL_IDMARCAS, COL_IDTIPOAUTOMOVIL, COL_IDCOLORES, COL_MODELO, COL_NUMERO_VIN, COL_NUMERO_CHASIS,
            COL_NUMERO_MOTOR, COL_NUMERO_ASIENTOS, COL_ANIO, COL_CAPACIDAD_ASIENTOS, COL_PRECIO, URI_IMG, COL_DESCRIPCION)
        return db!!.query(
            TABLE_NAME_AUTOMOVIL, columns, "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
    }

    fun searchAutomovilAll(): Cursor? {
        val columns = arrayOf(COL_ID, COL_IDMARCAS, COL_IDTIPOAUTOMOVIL, COL_IDCOLORES, COL_MODELO, COL_NUMERO_VIN, COL_NUMERO_CHASIS,
            COL_NUMERO_MOTOR, COL_NUMERO_ASIENTOS, COL_ANIO, COL_CAPACIDAD_ASIENTOS, COL_PRECIO, URI_IMG, COL_DESCRIPCION)
        return db!!.query(
            TABLE_NAME_AUTOMOVIL, columns, null, null, null, null,"${Automovil.COL_DESCRIPCION} MRC"
        )
    }
}