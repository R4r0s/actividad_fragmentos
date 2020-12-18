package com.example.actividad_fragmentos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.actividad_fragmentos.R
import com.example.actividad_fragmentos.model.Alumno


class ItemAdapterAlumno(var items: ArrayList<Alumno>, private val listener: (Alumno) -> Unit) : RecyclerView.Adapter<ItemAdapterAlumno.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterAlumno.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lista_items, parent, false)
        val viewHolder = ViewHolder(v)
        return viewHolder
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ItemAdapterAlumno.ViewHolder, position: Int) {
        holder.bindItems(items[position])
        holder.itemView.setOnClickListener { listener(items[position]) }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return items.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(cliente: Alumno) {
            val textViewNombre = itemView.findViewById<TextView>(R.id.textViewNombre)
            textViewNombre.text = cliente.nombre + " " +cliente.apellido
        }
    }
}
