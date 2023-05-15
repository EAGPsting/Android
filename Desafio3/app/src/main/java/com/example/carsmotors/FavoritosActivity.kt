package com.example.carsmotors

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotors.db.Datalist
import com.example.carsmotors.db.HelperDB
import com.example.carsmotors.db.MyAdapter

class FavoritosActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    lateinit var dbh: HelperDB
    private lateinit var newArry: ArrayList<Datalist>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)
        recyclerView = findViewById(R.id.listaCarros)

        dbh = HelperDB(this)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        displaycars()
    }
    private fun displaycars(){
        var newcursor: Cursor? = dbh!!.gettext()
        newArry= ArrayList<Datalist>()
        while (newcursor!!.moveToNext()){
            val uid = newcursor.getString(0)
            val utipo = newcursor.getString(1)
            val umarca = newcursor.getString(2)
            val umodelo = newcursor.getString(3)
            val ucolor = newcursor.getString(4)
            val uaño = newcursor.getString(5)
            val uchasis = newcursor.getString(6)
            val uvin = newcursor.getString(7)
            val umotor = newcursor.getString(8)
            val uprecio = newcursor.getString(9)
            val ufoto = newcursor.getString(10)
            newArry.add(Datalist(uid.toInt(),utipo, umarca,umodelo,ucolor,uaño.toInt(), uchasis,uvin, umotor, uprecio,ufoto))
        }
        recyclerView.adapter= MyAdapter(newArry)
    }
}