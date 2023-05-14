package com.example.carsmotors

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

class SignUpActivity : AppCompatActivity() {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signUpName: EditText = findViewById(R.id.signUpName)
        val signUpApellido: EditText = findViewById(R.id.signUpApellido)
        val signUpEmail: EditText = findViewById(R.id.signUpEmail)
        val signUpUser: EditText = findViewById(R.id.signUpUser)
        val signUpPassword: EditText = findViewById(R.id.signUpPassword)
        val signUpTipo: EditText = findViewById(R.id.signUpTipo)
        val signUpPasswordLayout: TextInputLayout = findViewById(R.id.signUpPasswordLayout)
        val signUpBtn: Button = findViewById(R.id.SignUpBtn)
        val signUpProgress: ProgressBar = findViewById(R.id.SignUpProgress)

        val signInText: TextView = findViewById(R.id.SignInText)

        signInText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signUpBtn.setOnClickListener {
            val name = signUpName.text.toString()
            val apellido = signUpApellido.text.toString()
            val email = signUpEmail.text.toString()
            val user = signUpUser.text.toString()
            val password = signUpPassword.text.toString()
            val tipo = signUpTipo.text.toString()

            signUpProgress.visibility = View.VISIBLE
            signUpPasswordLayout.isPasswordVisibilityToggleEnabled = true


            if (name.isEmpty() || apellido.isEmpty() || user.isEmpty() || email.isEmpty() || password.isEmpty() || tipo.isEmpty()) {
                if (name.isEmpty()) {
                    signUpName.error = "Ingrese su nombre"
                }

                if (apellido.isEmpty()) {
                    signUpApellido.error = "Ingrese su apellido"
                }

                if (email.isEmpty()) {
                    signUpEmail.error = "Ingrese su correo electronico"
                }

                if (user.isEmpty()) {
                    signUpUser.error = "Ingrese su usuario"
                }

                if (password.isEmpty()) {
                    signUpPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpPassword.error = "Ingrese su contraseña"
                }

                if (tipo.isEmpty()) {
                    signUpTipo.error = "Ingrese el tipo de usuario"
                }

                Toast.makeText(this, "Ingrese datos validos", Toast.LENGTH_SHORT).show()
                signUpProgress.visibility = View.GONE
            } else if (!email.matches(emailPattern.toRegex())) {
                signUpProgress.visibility = View.GONE
                signUpEmail.error = "Ingrese un correo electronico valido"
                Toast.makeText(this, "Ingrese un correo electronico valido", Toast.LENGTH_SHORT)
                    .show()
            } else if (password.length < 6) {
                signUpPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpProgress.visibility = View.GONE
                signUpPassword.error = "Ingrese una contraseña con mas de 6 caracteres"
                Toast.makeText(
                    this,
                    "Ingrese una contraseña con más de 6 caracteres",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                val usuario = Usuario(this)
                usuario.addNewUsuario(name, apellido, email, user, password, tipo)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}