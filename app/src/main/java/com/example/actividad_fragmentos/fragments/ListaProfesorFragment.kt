package com.example.actividad_fragmentos.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actividad_fragmentos.R
import com.example.actividad_fragmentos.adapters.ItemAdapterProfesor
import com.example.actividad_fragmentos.database.DataRepository
import com.example.actividad_fragmentos.model.Profesor

class ListaProfesorFragment : Fragment(){
    //var activityListener: View.OnClickListener? = null
    var itemSeleccionado: Profesor? = null
    var thiscontext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_lista_profesor, container, false)

        val asignatura = arguments!!.getString("asignatura")

        val recyclerViewLista: RecyclerView = v.findViewById<View>(R.id.recyclerviewlista) as RecyclerView
        thiscontext = container?.getContext();
        var dRepository = DataRepository(thiscontext!!)
        var numeroAsignatura: Int
        if (asignatura.equals("BBDD")){
            numeroAsignatura = 2
        }else{
            numeroAsignatura = 1
        }


        var guardados = dRepository.getProfesorOne(numeroAsignatura)
        var profesor = guardados.get(0).profesores

        var items = ArrayList<Profesor>()

        for (i in 0..profesor.size-1) {
            items.add(Profesor(profesor.get(i).nombre.toString(), profesor.get(i).apellido.toString()))
        }

        val adapter = ItemAdapterProfesor(items) { item ->
            itemSeleccionado = item

        }

        recyclerViewLista.setAdapter(adapter)
        recyclerViewLista.setLayoutManager(LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false))

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ListaProfesorFragment().apply {}
    }
}