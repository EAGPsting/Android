package com.example.carsmotors

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.carsmotors.model.Usuario

class UsuarioActivity : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellidos: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextUser: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var radioGroupTipo: RadioGroup
    private lateinit var radioButtonAdmin: RadioButton
    private lateinit var radioButtonUser: RadioButton
    private lateinit var buttonAgregar: Button
    private lateinit var buttonBuscar: Button
    private lateinit var buttonActualizar: Button
    private lateinit var buttonEliminar: Button

    private lateinit var usuario: Usuario
    private var idUsuarioSeleccionado: Int = -1

    @SuppressLint("MissingInflatedId")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        editTextNombre = findViewById(R.id.edit_text_nombre)
        editTextApellidos = findViewById(R.id.edit_text_apellidos)
        editTextEmail = findViewById(R.id.edit_text_email)
        editTextUser = findViewById(R.id.edit_text_user)
        editTextPassword = findViewById(R.id.edit_text_password)
        radioGroupTipo = findViewById(R.id.radio_group_tipo)
        radioButtonAdmin = findViewById(R.id.radio_button_admin)
        radioButtonUser = findViewById(R.id.radio_button_user)
        buttonAgregar = findViewById(R.id.button_add)
        buttonBuscar = findViewById(R.id.button_search)
        buttonActualizar = findViewById(R.id.button_update)
        buttonEliminar = findViewById(R.id.button_delete)

        usuario = Usuario(this)

        buttonAgregar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val apellidos = editTextApellidos.text.toString()
            val email = editTextEmail.text.toString()
            val user = editTextUser.text.toString()
            val password = editTextPassword.text.toString()
            val tipo = if (radioButtonAdmin.isChecked) "admin" else "user"

            if (nombre.isNotEmpty() && apellidos.isNotEmpty() && email.isNotEmpty() && user.isNotEmpty() && password.isNotEmpty()) {
                usuario.addNewUsuario(nombre, apellidos, email, user, password, tipo)
                Toast.makeText(this, "Usuario agregado correctamente.", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonBuscar.setOnClickListener {
            val email = editTextEmail.text.toString()
            if (email.isNotEmpty()) {
                val cursor = usuario.searchUsuario(email)
                if (cursor != null && cursor.moveToFirst()) {
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow(Usuario.COL_NOMBRE))
                    val apellidos =
                        cursor.getString(cursor.getColumnIndexOrThrow(Usuario.COL_APELLIDOS))
                    val user = cursor.getString(cursor.getColumnIndexOrThrow(Usuario.COL_USER))
                    val password =
                        cursor.getString(cursor.getColumnIndexOrThrow(Usuario.COL_PASSWORD))
                    val tipo = cursor.getString(cursor.getColumnIndexOrThrow(Usuario.COL_TIPO))

                    editTextNombre.setText(nombre)
                    editTextApellidos.setText(apellidos)
                    editTextUser.setText(user)
                    editTextPassword.setText(password)
                    if (tipo == "admin") {
                        radioButtonAdmin.isChecked = true
                    } else {
                        radioButtonUser.isChecked = true
                    }

                    idUsuarioSeleccionado =
                        cursor.getInt(cursor.getColumnIndexOrThrow(Usuario.COL_ID))
                } else {
                    Toast.makeText(this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                }
            } else {
                Toast.makeText(this, "Por favor ingrese un correo electrónico.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonActualizar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val apellidos = editTextApellidos.text.toString()
            val email = editTextEmail.text.toString()
            val user = editTextUser.text.toString()
            val password = editTextPassword.text.toString()
            val tipo = if (radioButtonAdmin.isChecked) "admin" else "user"

            if (nombre.isNotEmpty() && apellidos.isNotEmpty() && email.isNotEmpty() && user.isNotEmpty() && password.isNotEmpty()) {
                if (idUsuarioSeleccionado != -1) {
                    usuario.updateUsuario(
                        idUsuarioSeleccionado,
                        nombre,
                        apellidos,
                        email,
                        user,
                        password,
                        tipo
                    )
                    Toast.makeText(this, "Usuario actualizado correctamente.", Toast.LENGTH_SHORT)
                        .show()
                    limpiarCampos()
                } else {
                    Toast.makeText(
                        this,
                        "Primero busque un usuario para actualizar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonEliminar.setOnClickListener {
            val email = editTextEmail.text.toString()
            if (email.isNotEmpty()) {
                if (idUsuarioSeleccionado != -1) {
                    usuario.deleteUsuario(idUsuarioSeleccionado)
                    Toast.makeText(this, "Usuario eliminado correctamente.", Toast.LENGTH_SHORT)
                        .show()
                    limpiarCampos()
                } else {
                    Toast.makeText(
                        this,
                        "Primero busque un usuario para eliminar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Por favor ingrese el email del usuario a eliminar.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun limpiarCampos() {
        editTextNombre.setText("")
        editTextApellidos.setText("")
        editTextEmail.setText("")
        editTextUser.setText("")
        editTextPassword.setText("")
        radioButtonUser.isChecked = true
    }

}


