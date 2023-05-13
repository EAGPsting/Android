package com.example.carsmotors

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.*
import com.example.carsmotors.db.HelperDB
import com.example.carsmotors.model.Marcas

class MarcasActivity : AppCompatActivity() , View.OnClickListener {
    private var managerMarcas: Marcas? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null
    private var txtIdDB: TextView? = null
    private var txtId: EditText? = null
    private var txtNombre: EditText? = null
    private var btnAgregar: Button? = null
    private var btnActualizar: Button? = null
    private var btnEliminar: Button? = null
    private var btnBuscar: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marcas)
        txtIdDB = findViewById(R.id.txtIdDB)
        txtId = findViewById(R.id.txtId)
        txtNombre = findViewById(R.id.txtNombre)
        btnAgregar = findViewById(R.id.btnAgregar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnBuscar = findViewById(R.id.btnBuscar)
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        btnAgregar!!.setOnClickListener(this)
        btnActualizar!!.setOnClickListener(this)
        btnEliminar!!.setOnClickListener(this)
        btnBuscar!!.setOnClickListener(this)
    }
    private fun vericarFormulario(opc: String): Boolean {
        var notificacion: String = "Se han generado algunos errores, favor verifiquelos"
        var response = true
        var idmarca_v = true
        var nombre_v = true

        val nombre: String = txtNombre!!.text.toString().trim()
        val idmarca: String = txtId!!.text.toString().trim()
        if (opc === "insertar" || opc == "actualizar") {
            if (nombre.isEmpty()) {
                txtNombre!!.error = "Ingrese el nombre de la marca"
                txtNombre!!.requestFocus()
                nombre_v = false
            }
            if (opc == "actualizar") {
                if (idmarca.isEmpty()) {
                    idmarca_v = false
                    notificacion = "No se ha seleccionado una marca"
                }
                response = !(  nombre_v == false || idmarca_v == false)
            } else {
                response = !(nombre_v == false )
            }
        } else if (opc === "eliminar"||opc=="buscar") {
            if (idmarca.isEmpty()) {
                response = false
                notificacion = "No se ha seleccionado una marca"
            }
        }
        //Mostrar errores
        if (response == false) {
            Toast.makeText(
                this,
                notificacion,
                Toast.LENGTH_LONG
            ).show()
        }
        return response
    }
    override fun onClick(view: View) {
        managerMarcas = Marcas(this)
        val nombre: String = txtNombre!!.text.toString().trim()
        val idmarcas: String = txtId!!.text.toString().trim()
        if (db != null) {
            if (view === btnAgregar) {
                if (vericarFormulario("insertar")) {
                    managerMarcas!!.addNewMarca(
                        nombre
                    )
                    Toast.makeText(this, "Marca agregada",
                        Toast.LENGTH_LONG).show()
                }
            } else if (view === btnActualizar) {
                if (vericarFormulario("actualizar")) {
                    managerMarcas!!.updateMarca(
                        idmarcas.toInt(),
                        nombre
                    )
                    Toast.makeText(this, "Marca actualizado",
                        Toast.LENGTH_LONG).show()
                }
            } else if (view === btnEliminar) {
                if (vericarFormulario("eliminar")) {
                    // manager.eliminar(1);
                    managerMarcas!!.deleteMarca(idmarcas.toInt())
                    Toast.makeText(this, "Marca eliminada",
                        Toast.LENGTH_LONG).show()
                }
            } else if (view === btnBuscar) {
                /*IMPLEMENTE LA BUSQUEDA*/
            } else {
                Toast.makeText(this, "No se puede conectar a la Base de Datos",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

}