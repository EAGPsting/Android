package com.example.carsmotors

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotors.db.Datalist2
import com.example.carsmotors.db.HelperDB
import com.example.carsmotors.db.MyAdapter2

class FavActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    lateinit var dbh: HelperDB
    private lateinit var newArry: ArrayList<Datalist2>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)
        recyclerView = findViewById(R.id.listaFavoritos)

        dbh = HelperDB(this)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        displayfavs()
    }
    private fun displayfavs(){
        var newcursor: Cursor? = dbh!!.gettext()
        newArry= ArrayList<Datalist2>()

        while (newcursor!!.moveToNext()){
            val uidfav = newcursor.getString(0)
            val uidusuario = newcursor.getString(1)
            val uidauto = newcursor.getString(2)
            val ufecha = newcursor.getString(3)

            newArry.add(Datalist2(uidfav.toInt(), uidusuario.toInt(),uidauto.toInt(),ufecha))
        }
        recyclerView.adapter= MyAdapter2(newArry)
    }
}