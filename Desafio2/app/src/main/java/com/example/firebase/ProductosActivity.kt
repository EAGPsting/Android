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
import com.example.firebase.datos.Producto
import com.google.firebase.auth.FirebaseAuth

class ProductosActivity : AppCompatActivity() {
    var consultaOrdenada: Query = ProductosActivity.refProductos.orderByChild("nombre")
    var productos: MutableList<Producto>? = null
    var listaProductos: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)
        inicializar()
    }
    private fun inicializar() {
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaProductos = findViewById<ListView>(R.id.ListaProductos)

        // Cuando el usuario haga clic en la lista (para editar registro)
        listaProductos!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val intent = Intent(getBaseContext(), AddProductoActivity::class.java)
                intent.putExtra("accion", "e") // Editar
                intent.putExtra("key", productos!![i].key)
                intent.putExtra("nombre", productos!![i].nombre)
                intent.putExtra("precio", productos!![i].precio)
                intent.putExtra("indicaciones", productos!![i].indicaciones)
                intent.putExtra("contraindicaciones", productos!![i].contraindicaciones)
                intent.putExtra("foto", productos!![i].foto)
                startActivity(intent)
            }
        })

        // Cuando el usuario hace un LongClic (clic sin soltar elemento por mas de 2 segundos)
        // Es por que el usuario quiere eliminar el registro
        listaProductos!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {
                // Preparando cuadro de dialogo para preguntar al usuario
                // Si esta seguro de eliminar o no el registro
                val ad = AlertDialog.Builder(this@ProductosActivity)
                ad.setMessage("¿Que desea hacer?")
                    .setTitle("Menu")
                ad.setPositiveButton("Eliminar"
                ) { dialog, id ->
                    productos!![position].nombre?.let {
                        ProductosActivity.refProductos.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@ProductosActivity,
                        "Registro borrado!", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("Agregar al carrito", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@ProductosActivity,
                            "Operación de borrado cancelada!", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
            }
        }

        fab_agregar.setOnClickListener(View.OnClickListener {
            // Cuando el usuario quiere agregar un nuevo registro
            val i = Intent(getBaseContext(), AddProductoActivity::class.java)
            i.putExtra("accion", "a") // Agregar
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            i.putExtra("indicaciones", "")
            i.putExtra("contraindicaciones", "")
            i.putExtra("precio", "")
            i.putExtra("foto", "")
            startActivity(i)
        })
        productos = ArrayList<Producto>()

        // Cambiarlo refProductos a consultaOrdenada para ordenar lista
        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Procedimiento que se ejecuta cuando hubo algun cambio
                // en la base de datos
                // Se actualiza la coleccion de Productos
                productos!!.removeAll(productos!!)
                for (dato in dataSnapshot.getChildren()) {
                    val producto: Producto? = dato.getValue(Producto::class.java)
                    producto?.key(dato.key)
                    if (producto != null) {
                        productos!!.add(producto)
                    }
                }
                val adapter = AdaptadorProducto(
                    this@ProductosActivity,
                    productos as ArrayList<Producto>
                )
                listaProductos!!.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refProductos: DatabaseReference = database.getReference("productos")
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