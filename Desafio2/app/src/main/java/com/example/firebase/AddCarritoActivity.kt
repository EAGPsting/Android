package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.firebase.ProductosActivity.Companion.database
import com.example.firebase.datos.HistorialCompras
import com.google.firebase.auth.FirebaseAuth

class AddCarritoActivity : AppCompatActivity() {
    private var edtPrecio: EditText? = null
    private var edtNombre: EditText? = null
    private var edtIVA: EditText? = null
    private var key = ""
    private var nombre = ""
    private var precio = ""
    private var accion = ""
    private lateinit var  database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_carrito)
        inicializar()
    }
    private fun inicializar() {
        edtNombre = findViewById<EditText>(R.id.edtNombre)
        edtPrecio = findViewById<EditText>(R.id.edtPrecio)
        edtIVA = findViewById<EditText>(R.id.edtIVA)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtPrecio = findViewById<EditText>(R.id.edtPrecio)
        val edtIVA = findViewById<EditText>(R.id.edtIVA)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            edtPrecio.setText(intent.getStringExtra("precio").toString())
            val iva = (edtPrecio.text.toString().toDouble() * 0.13).toString()
            edtIVA.setText(iva.toString())
        }
        if (datos != null) {
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
        }

    }
    fun comprar(v: View?) {
        val nombre: String = edtNombre?.text.toString()
        val precio: String = edtPrecio?.text.toString()
        val IVA: String = (precio.toDouble() * 0.13).toString()

        database=FirebaseDatabase.getInstance().getReference("historialcompra")

        // Se forma objeto producto
        val historialCompras = HistorialCompras(nombre, precio, IVA)

        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(historialCompras).addOnSuccessListener {
                Toast.makeText(this,"Comprado con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed ", Toast.LENGTH_SHORT).show()
            }
        }
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