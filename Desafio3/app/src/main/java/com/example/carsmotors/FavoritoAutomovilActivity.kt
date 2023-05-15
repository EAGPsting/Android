package com.example.carsmotors

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.carsmotors.model.FavoritoAutomovil

class FavoritoAutomovilActivity : AppCompatActivity() {

    private lateinit var editTextFav: EditText
    private lateinit var editTextidUser: EditText
    private lateinit var editTextidAuto: EditText
    private lateinit var editTextFecha: EditText
    private lateinit var buttonAgregar: Button
    private lateinit var buttonBuscar: Button
    private lateinit var buttonActualizar: Button
    private lateinit var buttonEliminar: Button

    private lateinit var Favorito: FavoritoAutomovil
    private var idfavSeleccionado: Int = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorito_automovil)

        editTextFav = findViewById(R.id.editTextFav)
        editTextidUser = findViewById(R.id.editTextUsuarioId)
        editTextidAuto = findViewById(R.id.editTextAutomovilId)
        editTextFecha = findViewById(R.id.textViewFechaAgregado)
        buttonAgregar = findViewById(R.id.button_add)
        buttonBuscar = findViewById(R.id.button_search)
        buttonActualizar = findViewById(R.id.button_update)
        buttonEliminar = findViewById(R.id.button_delete)

        Favorito = FavoritoAutomovil(this)

        buttonAgregar.setOnClickListener {

            val iduser = editTextidUser.text.toString()
            val idAuto = editTextidAuto.text.toString()
            val fecha = editTextFecha.text.toString()


            if ( iduser.isNotEmpty() && idAuto.isNotEmpty() && fecha.isNotEmpty()) {
                Favorito.addNewFavoritoAutomovil(iduser.toInt(), idAuto.toInt(),fecha)
                Toast.makeText(this, "Automovil agregado correctamente.", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonActualizar.setOnClickListener {

            val iduser = editTextidUser.text.toString()
            val idAuto = editTextidAuto.text.toString()
            val fecha = editTextFecha.text.toString()

            if (iduser.isNotEmpty() && idAuto.isNotEmpty() && fecha.isNotEmpty()) {
                if (idfavSeleccionado != -1) {
                    Favorito.updateFavoritoAutomovil(
                        idfavSeleccionado,
                        idAuto.toInt(),
                        iduser.toInt(),
                        fecha
                    )
                    Toast.makeText(this, "Auto actualizado correctamente.", Toast.LENGTH_SHORT)
                        .show()
                    limpiarCampos()
                } else {
                    Toast.makeText(
                        this,
                        "Primero busque un auto para actualizar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonEliminar.setOnClickListener {
            val iduser = editTextidUser.text.toString()
            val idAuto = editTextidAuto.text.toString()
            val fecha = editTextFecha.text.toString()

            if ( iduser.isNotEmpty() && idAuto.isNotEmpty() && fecha.isNotEmpty()) {
                if (idfavSeleccionado != -1) {
                    Favorito.deleteFavoritoAutomovil(idfavSeleccionado)
                    Toast.makeText(this, "Auto eliminado correctamente.", Toast.LENGTH_SHORT)
                        .show()
                    limpiarCampos()
                } else {
                    Toast.makeText(
                        this,
                        "Primero busque un auto para eliminar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Por favor ingrese el id del usuario a eliminar.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun limpiarCampos() {
        editTextidUser.setText("")
        editTextidAuto.setText("")
        editTextFecha.setText("")
    }

}




