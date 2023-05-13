package com.example.carsmotors.db

import android.content.Context
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


}