package com.example.carsmotors

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.view.View
import android.widget.*
import com.example.carsmotors.db.HelperDB
import com.example.carsmotors.model.Colores
import com.example.carsmotors.model.Marcas
import com.example.carsmotors.model.TipoAutomovil
import com.example.carsmotors.model.Automovil
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import java.util.HashMap

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var managerColores: Colores? = null
    private var managerMarcas: Marcas? = null
    private var managerTipoAutomovil: TipoAutomovil? = null
    private var managerAutomovil: Automovil? = null

    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null
    private var cursor1: Cursor? = null
    private var cursor2: Cursor? = null

    private var txtIdDB: TextView? = null
    private var txtId: EditText? = null
    private var txtModelo: EditText? = null
    private var txtVIN: EditText? = null
    private var txtChasis: EditText? = null
    private var txtMotor: EditText? = null
    private var txtAsientos: EditText? = null
    private var txtAnio: EditText? = null
    private var txtPrecio: EditText? = null
    private var txtDescripcion: EditText? = null

    private var cmbColores: Spinner? = null
    private var cmbMarcas: Spinner? = null
    private var cmbTipoAutomovil: Spinner? = null

    private var btnImagen: Button? = null
    private var btnAgregar: Button? = null
    private var btnActualizar: Button? = null
    private var btnEliminar: Button? = null
    private var btnBuscar: Button? = null

    private val File = 1
    private val database1 = Firebase.database
    val myRef = database1.getReference("CarrosPictures")
    private var link = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtIdDB = findViewById(R.id.txtIdDB)
        txtId = findViewById(R.id.txtId)
        txtModelo = findViewById(R.id.txtModelo)
        txtChasis = findViewById(R.id.txtChasis)
        txtMotor = findViewById(R.id.txtMotor)
        txtAsientos = findViewById(R.id.txtAsientos)
        txtAnio = findViewById(R.id.txtAnio)
        txtDescripcion = findViewById(R.id.txtDescripcion)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtVIN = findViewById(R.id.txtVIN)

        cmbColores = findViewById<Spinner>(R.id.cmbColor)
        cmbMarcas = findViewById<Spinner>(R.id.cmbMarca)
        cmbTipoAutomovil = findViewById<Spinner>(R.id.cmbTipo)

        btnAgregar = findViewById(R.id.btnAgregar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnImagen= findViewById(R.id.btnImagen)

        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
        setSpinnerColores()
        setSpinnerMarcas()
        setSpinnerTipo()
        btnAgregar!!.setOnClickListener(this)
        btnActualizar!!.setOnClickListener(this)
        btnEliminar!!.setOnClickListener(this)
        btnBuscar!!.setOnClickListener(this)

        btnImagen!!.setOnClickListener {
            fileUpload()
        }
    }
    fun fileUpload() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, File)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == File) {
            if (resultCode == RESULT_OK) {
                val FileUri = data!!.data
                val Folder: StorageReference =
                    FirebaseStorage.getInstance().getReference().child("CarrosPictures")
                val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    file_name.getDownloadUrl().addOnSuccessListener { uri ->
                        val hashMap =
                            HashMap<String, String>()
                        hashMap["link"] = java.lang.String.valueOf(uri)
                        myRef.setValue(hashMap)
                        link=java.lang.String.valueOf(uri)
                        Log.d("uri", link)
                        Toast.makeText(
                            this,
                            "Imagen cargada con exito",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
    fun setSpinnerColores() {
        // Cargando valores por defecto
        managerColores = Colores(this)
        managerColores!!.insertarValuesDefault()
        cursor = managerColores!!.showAllColores()
        var cat = ArrayList<String>()
        if (cursor != null && cursor!!.count > 0) {
            cursor!!.moveToFirst()
            cat.add(cursor!!.getString(1))
            do {
                cat.add(cursor!!.getString(1))
            } while (cursor!!.moveToNext())
        }
        var adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, cat)

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbColores!!.adapter = adaptador
    }
    fun setSpinnerMarcas() {
        // Cargando valores por defecto
        managerMarcas = Marcas(this)
        managerMarcas!!.insertarValuesDefault()
        cursor1 = managerMarcas!!.showAllMarcas()
        var cat = ArrayList<String>()
        if (cursor1 != null && cursor1!!.count > 0) {
            cursor1!!.moveToFirst()
            cat.add(cursor1!!.getString(1))
            do {
                cat.add(cursor1!!.getString(1))
            } while (cursor1!!.moveToNext())
        }
        var adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, cat)

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbMarcas!!.adapter = adaptador
    }
    fun setSpinnerTipo() {
        // Cargando valores por defecto
        managerTipoAutomovil = TipoAutomovil(this)
        managerTipoAutomovil!!.insertarValuesDefault()
        cursor2 = managerTipoAutomovil!!.showAlltiposVehiculos()
        var cat = ArrayList<String>()
        if (cursor2 != null && cursor2!!.count > 0) {
            cursor2!!.moveToFirst()
            cat.add(cursor2!!.getString(1))
            do {
                cat.add(cursor2!!.getString(1))
            } while (cursor2!!.moveToNext())
        }
        var adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, cat)

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbTipoAutomovil!!.adapter = adaptador
    }
    override fun onClick(view: View) {
        managerAutomovil = Automovil(this)
        val modelo: String = txtModelo!!.text.toString().trim()
        val vin: String = txtVIN!!.text.toString().trim()
        val chasis: String = txtChasis!!.text.toString().trim()
        val motor: String = txtMotor!!.text.toString().trim()
        val asientos: String = txtAsientos!!.text.toString().trim()
        val año: String = txtAnio!!.text.toString().trim()
        val precio: String = txtPrecio!!.text.toString().trim()
        val descripcion: String = txtDescripcion!!.text.toString().trim()
        val tipo: String = cmbTipoAutomovil!!.selectedItem.toString().trim()
        val marca: String = cmbMarcas!!.selectedItem.toString().trim()
        val color: String = cmbColores!!.selectedItem.toString().trim()
        val foto: String = "SIN IMAGEN"
        val idvehiculo: String = txtId!!.text.toString().trim()
        val idmarca = managerMarcas!!.searchID(marca)
        val idtipo = managerTipoAutomovil!!.searchID(tipo)
        val idcolor = managerColores!!.searchID(color)
        if (db != null) {
            if (view === btnAgregar) {
                if (vericarFormulario("insertar")) {
                    managerAutomovil!!.addNewAutomovil(
                        idmarca,
                        idtipo,
                        idcolor,
                        modelo,
                        vin,
                        chasis,
                        motor,
                        asientos.toInt(),
                        año.toInt(),
                        asientos.toInt(),
                        precio.toDouble(),
                        link,
                        descripcion
                    )
                    Toast.makeText(this, "Automovil agregado",
                        Toast.LENGTH_LONG).show()
                }
            } else if (view === btnActualizar) {
                if (vericarFormulario("actualizar")) {
                    managerAutomovil!!.updateAutomovil(
                        idvehiculo.toInt(),
                        idmarca,
                        idtipo,
                        idcolor,
                        modelo,
                        vin,
                        chasis,
                        motor,
                        asientos.toInt(),
                        año.toInt(),
                        asientos.toInt(),
                        precio.toDouble(),
                        link,
                        descripcion
                    )
                    Toast.makeText(this, "Automovil actualizado",
                        Toast.LENGTH_LONG).show()
                }
            } else if (view === btnEliminar) {
                if (vericarFormulario("eliminar")) {
                    // manager.eliminar(1);
                    managerAutomovil!!.deleteAutomovil(idvehiculo.toInt())
                    Toast.makeText(this, "Automovil eliminado",
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
    private fun vericarFormulario(opc: String): Boolean {
        var notificacion: String = "Se han generado algunos errores, favor verifiquelos"
        var response = true
        var idautomovil_v = true
        var idcolores_v = true
        var idmarcas_v = true
        var idtipo_v = true
        var modelo_v = true
        var precio_v = true
        var vin_v = true
        var chasis_v = true
        var motor_v = true
        var asientos_v = true
        var año_v = true

        val modelo: String = txtModelo!!.text.toString().trim()
        val vin: String = txtVIN!!.text.toString().trim()
        val chasis: String = txtChasis!!.text.toString().trim()
        val motor: String = txtMotor!!.text.toString().trim()
        val asientos: String = txtAsientos!!.text.toString().trim()
        val año: String = txtAnio!!.text.toString().trim()
        val precio: String = txtPrecio!!.text.toString().trim()
        val tipo: String = cmbTipoAutomovil!!.selectedItem.toString().trim()
        val marca: String = cmbMarcas!!.selectedItem.toString().trim()
        val color: String = cmbColores!!.selectedItem.toString().trim()
        val idvehiculo: String = txtId!!.text.toString().trim()
        if (opc === "insertar" || opc == "actualizar") {
            if (modelo.isEmpty()) {
                txtModelo!!.error = "Ingrese el nombre del modelo"
                txtModelo!!.requestFocus()
                modelo_v = false
            }
            if (motor.isEmpty()) {
                txtMotor!!.error = "Ingrese el numero de motor"
                txtMotor!!.requestFocus()
                motor_v = false
            }
            if (año.isEmpty()) {
                txtAnio!!.error = "Ingrese el año del carro"
                txtAnio!!.requestFocus()
                año_v = false
            }
            if (asientos.isEmpty()) {
                txtAsientos!!.error = "Ingrese la cantidad de asientos"
                txtAsientos!!.requestFocus()
                asientos_v = false
            }
            if (precio.isEmpty()) {
                txtPrecio!!.error = "Ingrese el precio del vehiculo"
                txtPrecio!!.requestFocus()
                precio_v = false
            }
            if (chasis.isEmpty()) {
                txtChasis!!.error = "Ingrese el numero de Chasis"
                txtChasis!!.requestFocus()
                chasis_v = false
            }
            if (vin.isEmpty()) {
                txtVIN!!.error = "Ingrese el numero de VIN"
                txtVIN!!.requestFocus()
                vin_v = false
            }
            if (opc == "actualizar") {
                if (idvehiculo.isEmpty()) {
                    idautomovil_v = false
                    notificacion = "No se ha seleccionado un vehiculo"
                }
                response = !(modelo_v == false || precio_v == false || motor_v == false || año_v == false || chasis_v == false || vin_v == false || asientos_v==false || idautomovil_v == false)
            } else {
                response = !(modelo_v == false || precio_v == false || motor_v == false || año_v == false || chasis_v == false  || asientos_v==false || vin_v == false )
            }
        } else if (opc === "eliminar"||opc=="buscar") {
            if (idvehiculo.isEmpty()) {
                response = false
                notificacion = "No se ha seleccionado un vehiculo"
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

}