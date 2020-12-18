package com.example.actividad_fragmentos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.actividad_fragmentos.activities.FichaActivity
import com.example.actividad_fragmentos.database.*
import com.example.actividad_fragmentos.fragments.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    var frameLayoutFragmentProfesor: FrameLayout? = null
    var frameLayoutFragmentAlumnos: FrameLayout? = null
    var frameLayoutFragmentFicha: FrameLayout? = null

    var listaFragmentProfesor: ListaProfesorFragment? = null

    var listaFragmentAlumno: ListaAlumnoFragment? = null

    var fichaFragment: FichaAlumnoFragment? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rellenarAsignaturas()
        creacionActivity()


    }

    private fun rellenarAsignaturas() {

        var dRepository = DataRepository(this)

        var bufferedReaderRecurso = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.datos)))
        var textoLeido = bufferedReaderRecurso.readLine()
        val todo = StringBuilder()
        while (textoLeido != null) {
            todo.append(textoLeido + "\n")
            textoLeido = bufferedReaderRecurso.readLine()
        }
        textoLeido = todo.toString()
        bufferedReaderRecurso.close()

        val jsonObject = JSONObject(textoLeido)
        val jsonArray = jsonObject.optJSONArray("asignaturas")
        var listaAlumnos = ArrayList<Alumnos>()
        var listaProfesores = ArrayList<Profesores>()

        for (i in 0 until jsonArray.length()) {

            listaAlumnos.clear()
            listaProfesores.clear()

            var asignatura = Asignaturas(i + 1, jsonArray.get(i).toString())

            val asignaturaToText = jsonArray.get(i).toString()

            val jsonArrayProfesor = jsonObject.optJSONArray("profesores")

            for (i in 0 until jsonArrayProfesor.length()) {

                val objeto = jsonArrayProfesor.getJSONObject(i)

                if (objeto.optString("asignatura").equals(asignaturaToText)) {

                    var codigoProfesor = objeto.optString("codigo")
                    var nombreProfesor = objeto.optString("nombre")
                    var apellidoProfesor = objeto.optString("apellido")
                    var profesor = Profesores(codigoProfesor.toString().toInt(), nombreProfesor.toString(), apellidoProfesor.toString())
                    listaProfesores.add(profesor)
                }

            }
            val jsonArrayAlumno = jsonObject.optJSONArray("alumnos")

            for (i in 0 until jsonArrayAlumno.length()) {

                val objeto = jsonArrayAlumno.getJSONObject(i)

                val jsonArrayAsignaturas = objeto.optJSONArray("Asignaturas")

                for (i in 0 until jsonArrayAsignaturas.length()) {

                    val objetoAsignatura = jsonArrayAsignaturas[i].toString()

                    if (objetoAsignatura.equals(asignaturaToText)) {


                        var codigoAlumno = objeto.optString("codigo")
                        var nombreAlumno = objeto.optString("nombre")
                        var apellidoAlumno = objeto.optString("apellido")

                        //nombre.setText(nombreAlumno.toString())

                        var alumno = Alumnos(codigoAlumno.toString().toInt(), nombreAlumno.toString(), apellidoAlumno.toString())
                        listaAlumnos.add(alumno)
                    }
                }
            }


            var asignaturasAlumnos = AsignaturasAlumnos(asignatura, listaAlumnos)
            var asignaturasProfesores = AsignaturasProfesores(asignatura, listaProfesores)

            dRepository.insertAsignaturasAlumnos(asignaturasAlumnos)
            dRepository.insertAsignaturasProfesores(asignaturasProfesores)

        }





    }

    private fun verProfesores(asignatura: String){
        if (!asignatura.equals("Seleccione uno:")) {

            val asignaturaNombre = Bundle()
            asignaturaNombre.putString("asignatura", asignatura)


            frameLayoutFragmentProfesor = findViewById(R.id.frameLayoutProfesor)

            frameLayoutFragmentAlumnos = findViewById(R.id.frameLayoutAlumno)

            frameLayoutFragmentFicha = findViewById(R.id.frameLayoutFicha)

            frameLayoutFragmentProfesor?.removeAllViewsInLayout()
            frameLayoutFragmentAlumnos?.removeAllViewsInLayout()
            frameLayoutFragmentFicha?.removeAllViewsInLayout()



            listaFragmentProfesor = ListaProfesorFragment.newInstance()
            listaFragmentAlumno = ListaAlumnoFragment.newInstance()
            listaFragmentAlumno!!.activityListener = activityListener

            listaFragmentProfesor!!.setArguments(asignaturaNombre)
            listaFragmentAlumno!!.setArguments(asignaturaNombre)

            fichaFragment = FichaAlumnoFragment()

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

        if (frameLayoutFragmentFicha !=null){
            fragmentTransaction.add(R.id.frameLayoutProfesor, listaFragmentProfesor!!)
            fragmentTransaction.add(R.id.frameLayoutAlumno, listaFragmentAlumno!!)
            fragmentTransaction.add(R.id.frameLayoutFicha, fichaFragment!!)
        }
        else {
            fragmentTransaction.add(R.id.frameLayoutProfesor, listaFragmentProfesor!!)
            fragmentTransaction.add(R.id.frameLayoutAlumno, listaFragmentAlumno!!)
        }

            fragmentTransaction.commit()
        }
    }

    var activityListener = View.OnClickListener {
        if (frameLayoutFragmentFicha!=null) {
            fichaFragment!!.updateData(listaFragmentAlumno!!.itemSeleccionado)

        }else{
            val intent = Intent(this, FichaActivity::class.java).apply {
                putExtra("idAlumno", listaFragmentAlumno!!.itemSeleccionado?.id.toString())
            }
            startActivity(intent)
        }
    }

    private fun creacionActivity(){
        var dRepository = DataRepository(this)


        val spinner = findViewById<Spinner>(R.id.spinner)
        var pedidosGuardados = dRepository.getAsignaturas()
        var ArraySpinner = ArrayList<String>()
        ArraySpinner.add("Seleccione uno:")
        for (items in pedidosGuardados) {
            ArraySpinner.add(items.nombre.toString())
        }

        spinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArraySpinner)
        if (spinner != null) {

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    Toast.makeText(
                        this@MainActivity,
                        spinner.selectedItem.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    verProfesores(spinner.selectedItem.toString())

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }

    }

}