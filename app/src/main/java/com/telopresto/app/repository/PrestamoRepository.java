package com.telopresto.app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.telopresto.app.database.AppDatabase;
import com.telopresto.app.dao.PrestamoDao;
import com.telopresto.app.dao.PersonaDao;
import com.telopresto.app.dao.CategoriaDao;
import com.telopresto.app.model.Prestamo;
import com.telopresto.app.model.Persona;
import com.telopresto.app.model.Categoria;
import com.telopresto.app.model.PrestamoConDetalles;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrestamoRepository {

    private final PrestamoDao prestamoDao;
    private final PersonaDao personaDao;
    private final CategoriaDao categoriaDao;
    private final ExecutorService executor;

    public PrestamoRepository(Context context) {
        AppDatabase db = AppDatabase.getInstancia(context);
        prestamoDao = db.prestamoDao();
        personaDao = db.personaDao();
        categoriaDao = db.categoriaDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // Préstamos
    public void insertar(Prestamo prestamo) {
        executor.execute(() -> prestamoDao.insertar(prestamo));
    }

    public void actualizar(Prestamo prestamo) {
        executor.execute(() -> prestamoDao.actualizar(prestamo));
    }

    public void eliminar(Prestamo prestamo) {
        executor.execute(() -> prestamoDao.eliminar(prestamo));
    }

    public LiveData<List<Prestamo>> obtenerActivos() {
        return prestamoDao.obtenerActivos();
    }

    public LiveData<List<Prestamo>> obtenerDevueltos() {
        return prestamoDao.obtenerDevueltos();
    }

    public LiveData<Prestamo> obtenerPorId(int id) {
        return prestamoDao.obtenerPorId(id);
    }

    public LiveData<Integer> contarActivos() {
        return prestamoDao.contarActivos();
    }

    public LiveData<Integer> contarTotal() {
        return prestamoDao.contarTotal();
    }

    // Personas
    public void insertarPersona(Persona persona) {
        executor.execute(() -> personaDao.insertar(persona));
    }

    public LiveData<List<Persona>> obtenerPersonas() {
        return personaDao.obtenerTodas();
    }

    // Categorías
    public void insertarCategoria(Categoria categoria) {
        executor.execute(() -> categoriaDao.insertar(categoria));
    }

    public LiveData<List<Categoria>> obtenerCategorias() {
        return categoriaDao.obtenerTodas();
    }
    public void eliminarPersona(Persona persona) {
        executor.execute(() -> personaDao.eliminar(persona));
    }

    public void eliminarCategoria(Categoria categoria) {
        executor.execute(() -> categoriaDao.eliminar(categoria));
    }

    public LiveData<String> personaConMasPrestamos() {
        return prestamoDao.personaConMasPrestamos();
    }

    public LiveData<String> categoriaMasPrestada() {
        return prestamoDao.categoriaMasPrestada();
    }

    public LiveData<List<PrestamoConDetalles>> obtenerActivosConDetalles() {
        return prestamoDao.obtenerActivosConDetalles();
    }

    public LiveData<List<PrestamoConDetalles>> obtenerDevueltosConDetalles() {
        return prestamoDao.obtenerDevueltosConDetalles();
    }

    public LiveData<PrestamoConDetalles> obtenerPorIdConDetalles(int id) {
        return prestamoDao.obtenerPorIdConDetalles(id);
    }
}