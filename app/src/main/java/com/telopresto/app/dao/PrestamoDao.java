package com.telopresto.app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Transaction;

import com.telopresto.app.model.Prestamo;
import com.telopresto.app.model.PrestamoConDetalles;

import java.util.List;

@Dao
public interface PrestamoDao {

    @Insert
    void insertar(Prestamo prestamo);

    @Update
    void actualizar(Prestamo prestamo);

    @Delete
    void eliminar(Prestamo prestamo);

    @Query("SELECT * FROM prestamos WHERE estado = 'activo' ORDER BY fechaPrestamo DESC")
    LiveData<List<Prestamo>> obtenerActivos();

    @Query("SELECT * FROM prestamos WHERE estado = 'devuelto' ORDER BY fechaDevolucion DESC")
    LiveData<List<Prestamo>> obtenerDevueltos();

    @Query("SELECT * FROM prestamos WHERE id = :id")
    LiveData<Prestamo> obtenerPorId(int id);

    @Query("SELECT COUNT(*) FROM prestamos WHERE estado = 'activo'")
    LiveData<Integer> contarActivos();

    @Query("SELECT COUNT(*) FROM prestamos")
    LiveData<Integer> contarTotal();

    @Query("SELECT p.nombre FROM prestamos pr " +
            "JOIN personas p ON pr.idPersona = p.id " +
            "WHERE pr.estado = 'activo' " +
            "GROUP BY pr.idPersona " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 1")
    LiveData<String> personaConMasPrestamos();

    @Query("SELECT c.nombre FROM prestamos pr " +
            "JOIN categorias c ON pr.idCategoria = c.id " +
            "GROUP BY pr.idCategoria " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 1")
    LiveData<String> categoriaMasPrestada();

    @Transaction
    @Query("SELECT * FROM prestamos WHERE estado = 'activo' ORDER BY fechaPrestamo DESC")
    LiveData<List<PrestamoConDetalles>> obtenerActivosConDetalles();

    @Transaction
    @Query("SELECT * FROM prestamos WHERE estado = 'devuelto' ORDER BY fechaDevolucion DESC")
    LiveData<List<PrestamoConDetalles>> obtenerDevueltosConDetalles();

    @Transaction
    @Query("SELECT * FROM prestamos WHERE id = :id")
    LiveData<PrestamoConDetalles> obtenerPorIdConDetalles(int id);
}