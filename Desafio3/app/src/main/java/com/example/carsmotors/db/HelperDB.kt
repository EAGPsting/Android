package com.example.carsmotors.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.carsmotors.model.Automovil
import com.example.carsmotors.model.Colores
import com.example.carsmotors.model.FavoritoAutomovil
import com.example.carsmotors.model.Marcas
import com.example.carsmotors.model.TipoAutomovil
import com.example.carsmotors.model.Usuario

class HelperDB(context: Context?): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "CarsMotors.db"
        private const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(Marcas.CREATE_TABLE_MARCAS)
        db.execSQL(Colores.CREATE_TABLE_COLORES)
        db.execSQL(TipoAutomovil.CREATE_TABLE_TIPO_AUTOMOVIL)
        db.execSQL(Automovil.CREATE_TABLE_AUTOMOVIL)
        db.execSQL(FavoritoAutomovil.CREATE_TABLE_FAVORITOS_AUTOMOVIL)
        db.execSQL(Usuario.CREATE_TABLE_USUARIO)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
    fun gettext(): Cursor?{
        val p0 = this.writableDatabase
        val cursor = p0.rawQuery("select automovil.idautomovil,tipo_automovil.descripcion,marcas.nombre, automovil.modelo,colores.descripcion,automovil.año,automovil.numero_chasis,automovil.numero_vin,automovil.numero_motor,automovil.precio,automovil.uri_img from automovil inner join marcas on marcas.idmarcas = automovil.idmarcas inner join colores on colores.idcolores=automovil.idcolores inner join tipo_automovil on tipo_automovil.idtipoautomovil=automovil.idtipoautomovil", null)
        return cursor
    }
    fun gettext2(idUsuario: String): Cursor?{
        val p0 = this.writableDatabase
        val cursor = p0.rawQuery("select idfavoritoautomovil, idusuario, idautomovil, fecha_agregado from favorito_automovil where idusuario="+idUsuario.toString(), null)
        return cursor
    }
}