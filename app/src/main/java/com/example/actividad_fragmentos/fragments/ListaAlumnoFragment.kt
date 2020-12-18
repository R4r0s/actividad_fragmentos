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
import com.example.actividad_fragmentos.adapters.ItemAdapterAlumno
import com.example.actividad_fragmentos.database.DataRepository
import com.example.actividad_fragmentos.model.Alumno

class ListaAlumnoFragment : Fragment() {
    var activityListener: View.OnClickListener? = null
    var itemSeleccionado: Alumno? = null

    var thiscontext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.lista_alumnosfragment, container, false)

        val asignatura = arguments!!.getString("asignatura")

        val recyclerViewLista: RecyclerView =
            v.findViewById<View>(R.id.recyclerviewlista) as RecyclerView
        thiscontext = container?.getContext();
        var dRepository = DataRepository(thiscontext!!)
        var numeroAsignatura: Int
        if (asignatura.equals("BBDD")) {
            numeroAsignatura = 2
        } else {
            numeroAsignatura = 1
        }


        var guardados = dRepository.getAlumnos(numeroAsignatura)
        var alumno = guardados.component1().alumnos

        var items = ArrayList<Alumno>()
        for (i in 0..alumno.size-1) {
            items.add(Alumno(alumno.get(i).alumnosId.toInt(),alumno.get(i).nombre.toString(), alumno.get(i).apellido.toString()))
        }


        val adapter = ItemAdapterAlumno(items) { item ->
            itemSeleccionado = item
            if (activityListener != null) {
                activityListener!!.onClick(view)
            }
        }

        recyclerViewLista.setAdapter(adapter)
        recyclerViewLista.setLayoutManager(LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false))

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ListaAlumnoFragment().apply {}
    }
}