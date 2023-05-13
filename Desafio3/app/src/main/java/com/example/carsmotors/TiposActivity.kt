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
import com.example.carsmotors.model.TipoAutomovil

class TiposActivity : AppCompatActivity() , View.OnClickListener {
    private var managerTipoAutomovil: TipoAutomovil? = null
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
        setContentView(R.layout.activity_tipos)
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
        var idtipo_v = true
        var nombre_v = true

        val nombre: String = txtNombre!!.text.toString().trim()
        val idtipo: String = txtId!!.text.toString().trim()
        if (opc === "insertar" || opc == "actualizar") {
            if (nombre.isEmpty()) {
                txtNombre!!.error = "Ingrese el nombre del tipo de vehiculo"
                txtNombre!!.requestFocus()
                nombre_v = false
            }
            if (opc == "actualizar") {
                if (idtipo.isEmpty()) {
                    idtipo_v = false
                    notificacion = "No se ha seleccionado un tipo de vehiculo"
                }
                response = !(  nombre_v == false || idtipo_v == false)
            } else {
                response = !(nombre_v == false )
            }
        } else if (opc === "eliminar"||opc=="buscar") {
            if (idtipo.isEmpty()) {
                response = false
                notificacion = "No se ha seleccionado un tipo de vehiculo"
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
        managerTipoAutomovil = TipoAutomovil(this)
        val nombre: String = txtNombre!!.text.toString().trim()
        val idtipo: String = txtId!!.text.toString().trim()
        if (db != null) {
            if (view === btnAgregar) {
                if (vericarFormulario("insertar")) {
                    managerTipoAutomovil!!.addNewTipo(
                        nombre
                    )
                    Toast.makeText(this, "Tipo Automovil agregado",
                        Toast.LENGTH_LONG).show()
                }
            } else if (view === btnActualizar) {
                if (vericarFormulario("actualizar")) {
                    managerTipoAutomovil!!.updateTipo(
                        idtipo.toInt(),
                        nombre
                    )
                    Toast.makeText(this, "Tipo Automovil actualizado",
                        Toast.LENGTH_LONG).show()
                }
            } else if (view === btnEliminar) {
                if (vericarFormulario("eliminar")) {
                    // manager.eliminar(1);
                    managerTipoAutomovil!!.deleteTipo(idtipo.toInt())
                    Toast.makeText(this, "Tipo Automovil eliminada",
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