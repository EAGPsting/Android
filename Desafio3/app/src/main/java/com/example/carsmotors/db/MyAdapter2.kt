package com.example.carsmotors.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotors.R

class MyAdapter2 (var favList: ArrayList<Datalist2>): RecyclerView.Adapter<MyAdapter2.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tIdFav: TextView = itemView.findViewById(R.id.viewIdFav)
        val tIdUsuario: TextView = itemView.findViewById(R.id.viewIdUsuario)
        val tIdAuto: TextView = itemView.findViewById(R.id.viewIdAuto)
        val tFecha: TextView = itemView.findViewById(R.id.viewFecha)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista_item_favoritos, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = favList[position]
        holder.tIdFav.text = currentItem.idfav.toString()
        holder.tIdUsuario.text = currentItem.idusuario.toString()
        holder.tIdAuto.text = currentItem.idauto.toString()
        holder.tFecha.text = currentItem.fecha.toString()

    }
}