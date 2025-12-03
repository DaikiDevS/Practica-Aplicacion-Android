package org.utl.premiereleague.data

import androidx.room.*

/* ==========================
        DAO POSICIONES
   ========================== */
@Dao
interface TablaPosicionesDao {

    @Query("SELECT * FROM tabla_posiciones ORDER BY puntos DESC")
    fun getAll(): List<EquipoEntity>

    @Insert
    fun insert(equipo: EquipoEntity)

    @Update
    fun update(equipo: EquipoEntity)

    @Delete
    fun delete(equipo: EquipoEntity)
}

/* ==========================
          DAO GOLEADORES
   ========================== */
@Dao
interface GoleadoresDao {

    @Query("SELECT * FROM goleadores ORDER BY goles DESC")
    fun getAll(): List<GoleadorEntity>

    @Insert
    fun insert(goleador: GoleadorEntity)

    @Update
    fun update(goleador: GoleadorEntity)

    @Delete
    fun delete(goleador: GoleadorEntity)
}
