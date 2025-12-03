package org.utl.premiereleague.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateListOf
import org.utl.premiereleague.data.*

/* ==========================
    VIEWMODEL TABLA POSICIONES
   ========================== */
class TablaPosicionesViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getDatabase(app).tablaPosicionesDao()

    val lista = mutableStateListOf<EquipoEntity>()

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        lista.clear()
        lista.addAll(dao.getAll())
    }

    fun agregar(nombre: String, puntos: Int) {
        dao.insert(EquipoEntity(nombre = nombre, puntos = puntos))
        cargarDatos()
    }

    fun actualizar(equipo: EquipoEntity) {
        dao.update(equipo)
        cargarDatos()
    }

    fun eliminar(equipo: EquipoEntity) {
        dao.delete(equipo)
        cargarDatos()
    }
}

/* ==========================
    VIEWMODEL GOLEADORES
   ========================== */
class GoleadoresViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getDatabase(app).goleadoresDao()

    val lista = mutableStateListOf<GoleadorEntity>()

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        lista.clear()
        lista.addAll(dao.getAll())
    }

    fun agregar(jugador: String, goles: Int) {
        dao.insert(GoleadorEntity(jugador = jugador, goles = goles))
        cargarDatos()
    }

    fun actualizar(goleador: GoleadorEntity) {
        dao.update(goleador)
        cargarDatos()
    }

    fun eliminar(goleador: GoleadorEntity) {
        dao.delete(goleador)
        cargarDatos()
    }
}
