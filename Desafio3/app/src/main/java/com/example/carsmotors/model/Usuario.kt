package com.example.carsmotors.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.carsmotors.db.HelperDB

class Usuario(context: Context) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {

        val TABLE_NAME_USUARIO = "usuario"

        val COL_ID = "idusuario"
        val COL_NOMBRE = "nombre"
        val COL_APELLIDOS = "apellido"
        val COL_EMAIL = "email"
        val COL_USER = "user"
        val COL_PASSWORD = "password"
        val COL_TIPO = "tipo"


        val CREATE_TABLE_USUARIO = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USUARIO + "("
                        + COL_ID + "integer primary key autoincrement,"
                        + COL_NOMBRE + "varchar(45) NOT NULL,"
                        + COL_APELLIDOS + "varchar(45) NOT NULL,"
                        + COL_EMAIL + "varchar(45) NOT NULL,"
                        + COL_USER + "varchar(45) NOT NULL,"
                        + COL_PASSWORD + "varchar(45) NOT NULL,"
                        + COL_TIPO + "varchar(45) NOT NULL);"
                )
    }

    fun generarContentValues(
        nombre: String?,
        apellido: String?,
        email: String?,
        user: String?,
        password: String?,
        tipo: String?
    ): ContentValues? {

        val valores = ContentValues()
        valores.put(COL_NOMBRE, nombre)
        valores.put(COL_APELLIDOS, apellido)
        valores.put(COL_EMAIL, email)
        valores.put(COL_USER, user)
        valores.put(COL_PASSWORD, password)
        valores.put(COL_TIPO, tipo)
        return valores
    }

    fun addNewUsuario(nombre: String?, apellido: String?, email: String?, user: String?, password: String?, tipo: String?){
        db!!.insert(
            TABLE_NAME_USUARIO, null, generarContentValues(nombre, apellido, email, user, password, tipo)
        )
    }

    fun deleteUsuario(id: Int){
        db!!.delete(TABLE_NAME_USUARIO, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun updateUsuario(
        id: Int,
        nombre: String?,
        apellido: String?,
        email: String?,
        user: String?,
        password: String?,
        tipo: String?
    ){
        db!!.update(
            TABLE_NAME_USUARIO, generarContentValues(nombre, apellido, email, user, password, tipo),
            "$COL_ID=?", arrayOf(id.toString())
        )
    }

    fun searchUsuario(id: Int): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_APELLIDOS, COL_EMAIL, COL_USER, COL_PASSWORD, COL_TIPO)
        return db!!.query(
            TABLE_NAME_USUARIO, columns, "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
    }

    fun searchUsuarioAll(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_APELLIDOS, COL_EMAIL, COL_USER, COL_PASSWORD, COL_TIPO)
        return db!!.query(
            TABLE_NAME_USUARIO, columns, null, null, null, null,"${Usuario.COL_USER} MRC"
        )
    }
}