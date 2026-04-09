package com.telopresto.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.telopresto.app.dao.CategoriaDao;
import com.telopresto.app.dao.PersonaDao;
import com.telopresto.app.dao.PrestamoDao;
import com.telopresto.app.model.Categoria;
import com.telopresto.app.model.Persona;
import com.telopresto.app.model.Prestamo;

@Database(entities = {Prestamo.class, Persona.class, Categoria.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instancia;

    public abstract PrestamoDao prestamoDao();
    public abstract PersonaDao personaDao();
    public abstract CategoriaDao categoriaDao();

    public static synchronized AppDatabase getInstancia(Context context) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "telopresto_db"
            ).build();
        }
        return instancia;
    }
}