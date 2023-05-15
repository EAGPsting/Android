package com.example.carsmotors.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotors.R

class MyAdapter (var carList: ArrayList<Datalist>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tId: TextView = itemView.findViewById(R.id.viewId)
        val tTipo: TextView = itemView.findViewById(R.id.viewTipo)
        val tMarca: TextView = itemView.findViewById(R.id.viewMarca)
        val tModelo: TextView = itemView.findViewById(R.id.viewModelo)
        val tColor: TextView = itemView.findViewById(R.id.viewColor)
        val tAño: TextView = itemView.findViewById(R.id.viewAño)
        val tChasis: TextView = itemView.findViewById(R.id.viewChasis)
        val tVIN: TextView = itemView.findViewById(R.id.viewVIN)
        val tMotor: TextView = itemView.findViewById(R.id.viewMotor)
        val tPrecio: TextView = itemView.findViewById(R.id.viewPrecio)
        val tFoto: TextView = itemView.findViewById(R.id.viewFoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista_item_carros, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = carList[position]
        holder.tId.text = currentItem.id.toString()
        holder.tTipo.text = currentItem.tipo.toString()
        holder.tMarca.text = currentItem.marca.toString()
        holder.tModelo.text = currentItem.modelo.toString()
        holder.tColor.text = currentItem.color.toString()
        holder.tAño.text = currentItem.año.toString()
        holder.tChasis.text = currentItem.chasis.toString()
        holder.tVIN.text = currentItem.vin.toString()
        holder.tMotor.text = currentItem.motor.toString()
        holder.tPrecio.text = currentItem.precio.toString()
        holder.tFoto.text = currentItem.foto.toString()
    }
}