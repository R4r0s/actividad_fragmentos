package com.example.actividad_fragmentos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.actividad_fragmentos.R
import com.example.actividad_fragmentos.model.Profesor

class ItemAdapterProfesor(var items: ArrayList<Profesor>, private val listener: (Profesor) -> Unit) : RecyclerView.Adapter<ItemAdapterProfesor.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterProfesor.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lista_items, parent, false)
        val viewHolder = ViewHolder(v)
        return viewHolder
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ItemAdapterProfesor.ViewHolder, position: Int) {
        holder.bindItems(items[position])
        holder.itemView.setOnClickListener { listener(items[position]) }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return items.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(cliente: Profesor) {
            val textViewNombre = itemView.findViewById<TextView>(R.id.textViewNombre)
            textViewNombre.text = cliente.nombre + " " +cliente.apellido
        }
    }
}