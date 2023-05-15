package com.example.carsmotors

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.carsmotors.model.Usuario
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signInEmail : EditText = findViewById(R.id.signInEmail)
        val signInPassword : EditText = findViewById(R.id.signInPassword)
        val signInPasswordLayout : TextInputLayout = findViewById(R.id.signInPasswordLayout)
        val signInBtn : Button = findViewById(R.id.SignInBtn)
        val signInProgressBar : ProgressBar = findViewById(R.id.signInProgressBar)

        val signUpText : TextView = findViewById(R.id.SignUpText)

        signUpText.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {
            signInProgressBar.visibility = View.VISIBLE
            signInPasswordLayout.isPasswordVisibilityToggleEnabled = true

            val email = signInEmail.text.toString()
            val password = signInPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                if (email.isEmpty()){
                    signInEmail.error = "Ingrese su correo electronico"
                }
                if (password.isEmpty()){
                    signInPassword.error = "Ingrese su contraseña"
                    signInPasswordLayout.isPasswordVisibilityToggleEnabled = false
                }

                signInProgressBar.visibility = View.GONE
                Toast.makeText(this,"Ingrese datos validos", Toast.LENGTH_SHORT).show()

            }else if (!email.matches(emailPattern.toRegex())){
                signInProgressBar.visibility = View.GONE
                signInEmail.error = "Ingrese un correo electronico valido"
                Toast.makeText(this,"Ingrese un correo electronico valido", Toast.LENGTH_SHORT).show()
            }else if (password.length < 6 ){
                signInPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signInProgressBar.visibility = View.GONE
                signInPassword.error = "Ingrese una contraseña con mas de 6 caracteres"
                Toast.makeText(this,"Ingrese una contraseña con mas de 6 caracteres", Toast.LENGTH_SHORT).show()
            }else{
                loginUser(email,password)
            }
        }

    }

    @SuppressLint("Range")
    private fun loginUser(email: String, pass: String) {
        val email = email.toString().trim()
        val pass = pass.toString().trim()

        val db = Usuario(this)
        val cursor = db.searchUsuario(email)

        if (cursor != null && cursor.moveToFirst()) {
            val tipoUsuario = cursor.getString(cursor.getColumnIndex(Usuario.COL_TIPO))

            if (pass == cursor.getString(cursor.getColumnIndex(Usuario.COL_PASSWORD))) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("tipoUsuario", tipoUsuario)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_LONG).show()
        }
    }


}