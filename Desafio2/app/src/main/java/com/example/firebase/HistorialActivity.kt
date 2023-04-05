package com.example.firebase

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.example.firebase.datos.HistorialCompras
import com.example.firebase.datos.Producto
import com.google.firebase.auth.FirebaseAuth

class HistorialActivity : AppCompatActivity() {
    var consultaOrdenada: Query = HistorialActivity.refHistorial.orderByChild("nombre")
    var historialCompras: MutableList<HistorialCompras>? = null
    var listaHistorialCompras: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)
        inicializar()
    }
    private fun inicializar() {
        listaHistorialCompras = findViewById<ListView>(R.id.ListaHistorial)
        historialCompras = ArrayList<HistorialCompras>()

        // Cambiarlo refProductos a consultaOrdenada para ordenar lista
        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Procedimiento que se ejecuta cuando hubo algun cambio
                // en la base de datos
                // Se actualiza la coleccion de Productos
                historialCompras!!.removeAll(historialCompras!!)
                for (dato in dataSnapshot.getChildren()) {
                    val historial: HistorialCompras? = dato.getValue(HistorialCompras::class.java)
                    historial?.key(dato.key)
                    if (historial != null) {
                        historialCompras!!.add(historial)
                    }
                }
                val adapter = AdaptadorHistorial(
                    this@HistorialActivity,
                    historialCompras as ArrayList<HistorialCompras>
                )
                listaHistorialCompras!!.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refHistorial: DatabaseReference = database.getReference("historialcompra")
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
            R.id.action_option1->{
                val intent = Intent(this, HistorialActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_option3->{
                val intent = Intent(this, ProductosActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

}