package com.example.actividad_fragmentos.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asignaturas(
    @PrimaryKey val asignaturasId: Int,
    val nombre: String?
)