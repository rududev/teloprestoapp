package com.telopresto.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.telopresto.app.model.Persona;

import java.util.List;

@Dao
public interface PersonaDao {

    @Insert
    void insertar(Persona persona);

    @Update
    void actualizar(Persona persona);

    @Delete
    void eliminar(Persona persona);

    @Query("SELECT * FROM personas ORDER BY nombre ASC")
    LiveData<List<Persona>> obtenerTodas();
}