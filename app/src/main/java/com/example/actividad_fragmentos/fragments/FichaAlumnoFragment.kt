package com.example.actividad_fragmentos.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.actividad_fragmentos.R
import com.example.actividad_fragmentos.database.DataRepository
import com.example.actividad_fragmentos.model.Alumno

class FichaAlumnoFragment : Fragment(){
    var textViewNombre: TextView? = null
    var textViewApellido: TextView? = null
    var thiscontext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.ficha_alumnosfragment, container, false)
        val idAlumno = arguments?.getString("idAlumno")?.toInt()

        thiscontext = container?.getContext();
        var dRepository = thiscontext?.let { DataRepository(it) }

        var guardados = idAlumno?.let { dRepository?.GetAlumnoOne(it.toInt()) }

        textViewNombre = v.findViewById<View>(R.id.textViewFichaNombre) as TextView
        textViewApellido = v.findViewById<View>(R.id.textViewFichaApellido) as TextView

        if (guardados != null) {
            textViewNombre!!.text = guardados.get(0).nombre
            textViewApellido!!.text = guardados.get(0).apellido
        }

        return v
    }
    fun updateData(item: Alumno?) {
        if (item!=null) {
            textViewNombre!!.text = item.nombre
            textViewApellido!!.text = item.apellido
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FichaAlumnoFragment().apply {
            }
    }
}