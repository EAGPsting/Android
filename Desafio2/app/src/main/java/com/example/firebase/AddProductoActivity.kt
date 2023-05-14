package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.example.firebase.ProductosActivity.Companion.database
import com.example.firebase.datos.HistorialCompras
import com.example.firebase.datos.Producto
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth

class AddProductoActivity : AppCompatActivity() {
    private var edtIndicaciones: EditText? = null
    private var edtPrecio: EditText? = null
    private var edtContraIndicaciones: EditText? = null
    private var edtNombre: EditText? = null
    private var edtIVA: EditText? = null
    private var edtfoto: EditText? = null
    private var key = ""
    private var nombre = ""
    private var precio = ""
    private var indicaciones = ""
    private var contraindicaciones = ""
    private var accion = ""
    private lateinit var  database:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_producto)
        inicializar()
    }

    private fun inicializar() {
        edtNombre = findViewById<EditText>(R.id.edtNombre)
        edtIndicaciones = findViewById<EditText>(R.id.edtIndicaciones)
        edtContraIndicaciones = findViewById<EditText>(R.id.edtContraIndicaciones)
        edtPrecio = findViewById<EditText>(R.id.edtPrecio)
        edtIVA = findViewById<EditText>(R.id.edtIVA)
        edtfoto = findViewById<EditText>(R.id.edtfoto)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtIndicaciones = findViewById<EditText>(R.id.edtIndicaciones)
        val edtContraIndicaciones = findViewById<EditText>(R.id.edtContraIndicaciones)
        val edtPrecio = findViewById<EditText>(R.id.edtPrecio)
        val edtIVA = findViewById<EditText>(R.id.edtIVA)
        val edtfoto = findViewById<EditText>(R.id.edtfoto)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            edtIndicaciones.setText(intent.getStringExtra("indicaciones").toString())
        }
        if (datos != null) {
            edtContraIndicaciones.setText(intent.getStringExtra("contraindicaciones").toString())
        }
        if (datos != null) {
            edtPrecio.setText(intent.getStringExtra("precio").toString())
        }
        if (datos != null) {
            var iva = ""
            if (edtPrecio.text.toString()==""){
                iva = edtPrecio.text.toString()
            }
            else
            {
                iva = (edtPrecio.text.toString().toDouble()*0.13).toString()
            }
            edtIVA.setText(iva.toString())
        }
        if (datos != null) {
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            edtfoto.setText(intent.getStringExtra("foto").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
        }

    }

    fun guardar(v: View?) {
        val nombre: String = edtNombre?.text.toString()
        val indicaciones: String = edtIndicaciones?.text.toString()
        val contraindicaciones: String = edtContraIndicaciones?.text.toString()
        val precio: String = edtPrecio?.text.toString()
        val foto: String = edtfoto?.text.toString()

        database=FirebaseDatabase.getInstance().getReference("productos")

        // Se forma objeto producto
        val producto = Producto(nombre, indicaciones, contraindicaciones, precio, foto)

        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(producto).addOnSuccessListener {
                Toast.makeText(this,"Se guardo con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed ", Toast.LENGTH_SHORT).show()
            }
        } else  // Editar registro
        {
            val key = database.child("nombre").push().key
            if (key == null) {
                Toast.makeText(this,"Llave vacia", Toast.LENGTH_SHORT).show()
            }
            val productosValues = producto.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to productosValues
            )
            database.updateChildren(childUpdates)
            Toast.makeText(this,"Se actualizo con exito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
    fun comprar(v: View?) {
        val nombre: String = edtNombre?.text.toString()
        val precio: String = edtPrecio?.text.toString()
        val IVA: String = (precio.toDouble() * 0.13).toString()

        val intent = Intent(getBaseContext(), AddCarritoActivity::class.java)
        intent.putExtra("accion", "a") // Editar
        intent.putExtra("key", "")
        intent.putExtra("nombre", nombre)
        intent.putExtra("precio", precio)
        intent.putExtra("IVA", IVA)
        startActivity(intent)
        finish()

    }
    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refHistorialCompras: DatabaseReference = database.getReference("historialcompras")
    }
    fun cancelar(v: View?) {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sign_out->{
                FirebaseAuth.getInstance().signOut().also {
                    Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }
}