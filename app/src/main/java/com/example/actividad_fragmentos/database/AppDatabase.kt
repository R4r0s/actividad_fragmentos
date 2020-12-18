package com.example.actividad_fragmentos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Asignaturas::class, Profesores::class,Alumnos::class, AsignaturasProfesoresCrossRef::class, AsignaturasAlumnosCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun asignaturasDao(): AsignaturasDao
    abstract fun profesoresDao(): ProfesoresDao
    abstract fun alumnosDao(): AlumnosDao
    abstract fun profesoresAsignaturasDao(): AsignaturasProfesoresDao
    abstract fun alumnosAsignaturasDao(): AsignaturasAlumnosDao

    companion object {
        private const val DATABASE_NAME = "alumnos_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }

    }
}