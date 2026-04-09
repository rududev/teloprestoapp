package com.telopresto.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "prestamos",
        foreignKeys = {
                @ForeignKey(
                        entity = Persona.class,
                        parentColumns = "id",
                        childColumns = "idPersona",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Categoria.class,
                        parentColumns = "id",
                        childColumns = "idCategoria",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Prestamo {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int idPersona;
    public int idCategoria;
    public String objeto;
    public String direccion;
    public String fechaPrestamo;
    public String fechaDevolucion;
    public String estado;
    public String fotoObjeto;
    public String firmaDigital;
    public String notas;
}