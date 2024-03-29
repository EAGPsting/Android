package com.example.carsmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible

class PrincipalActivity : AppCompatActivity() {
    private var btnMarcas: Button? = null
    private var btnAutomovil: Button? = null
    private var btnTipo: Button? = null
    private var btnColor: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        val bundle = intent.extras
        val tipo = bundle?.getString("tipoUsuario")
        val idUsuario = bundle?.getString("idUsuario")

        val btnMarcas : Button = findViewById(R.id.btnMarcas)
        val btnTipo : Button = findViewById(R.id.btnTipo)
        val btnColor : Button = findViewById(R.id.btnColor)
        val btnAutomovil : Button = findViewById(R.id.btnAutomovil)
        val btnUsuario : Button = findViewById(R.id.btnUsuario)
        val btnFavoritos : Button = findViewById(R.id.btnFavoritos)
        val btnFavoritos2 : Button = findViewById(R.id.btnFavoritos2)
        val btnFavoritos3 : Button = findViewById(R.id.btnFavoritos3)
        val btnCerrar : Button = findViewById(R.id.btnCerrarSesion)

        if (tipo == "client" )
        {
            btnMarcas.isVisible=false
            btnColor.isVisible=false
            btnTipo.isVisible=false
            btnUsuario.isVisible=false
            btnAutomovil.isVisible=false
            Toast.makeText(this, idUsuario, Toast.LENGTH_LONG).show()
        }
        btnCerrar.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        btnFavoritos3.setOnClickListener {
            val intent = Intent(this,FavoritoAutomovilActivity::class.java)
            startActivity(intent)
        }
        btnFavoritos.setOnClickListener {
            val intent = Intent(this,FavoritosActivity::class.java)
            startActivity(intent)
        }
        btnFavoritos2.setOnClickListener {
            val intent = Intent(this,FavActivity::class.java)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }
        btnUsuario.setOnClickListener {
            val intent = Intent(this,UsuarioActivity::class.java)
            startActivity(intent)
        }
        btnMarcas.setOnClickListener {
            val intent = Intent(this,MarcasActivity::class.java)
            startActivity(intent)
        }
        btnTipo.setOnClickListener {
            val intent = Intent(this,TiposActivity::class.java)
            startActivity(intent)
        }
        btnColor.setOnClickListener {
            val intent = Intent(this,ColoresActivity::class.java)
            startActivity(intent)
        }
        btnAutomovil.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sign_out->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_option1->{
                val intent = Intent(this, ColoresActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_option2->{
                val intent = Intent(this, MarcasActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_option3->{
                val intent = Intent(this, TiposActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_option4->{
                val intent = Intent(this, UsuarioActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_option5->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}