package com.telopresto.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "personas")
public class Persona {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;
    public String telefono;
    public String fotoPerfil;
}