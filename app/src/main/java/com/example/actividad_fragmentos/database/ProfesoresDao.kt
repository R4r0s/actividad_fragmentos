package com.example.actividad_fragmentos.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ProfesoresDao {
    @Insert
    fun insertAll(vararg profesores: Profesores)
    @Insert
    fun insertAll(profesores: List<Profesores>)
}