package com.telopresto.app.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PrestamoConDetalles {

    @Embedded
    public Prestamo prestamo;

    @Relation(parentColumn = "idPersona", entityColumn = "id")
    public Persona persona;

    @Relation(parentColumn = "idCategoria", entityColumn = "id")
    public Categoria categoria;
}