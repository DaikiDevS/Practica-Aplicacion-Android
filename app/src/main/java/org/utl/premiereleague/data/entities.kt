package org.utl.premiereleague.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/* ==========================
   ENTIDAD TABLA DE POSICIONES
   ========================== */
@Entity(tableName = "tabla_posiciones")
data class EquipoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val puntos: Int
)

/* ==========================
      ENTIDAD GOLEADORES
   ========================== */
@Entity(tableName = "goleadores")
data class GoleadorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jugador: String,
    val goles: Int
)
