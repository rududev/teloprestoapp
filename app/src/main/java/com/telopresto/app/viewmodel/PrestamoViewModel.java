package com.telopresto.app.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.telopresto.app.model.Categoria;
import com.telopresto.app.model.Persona;
import com.telopresto.app.model.Prestamo;
import com.telopresto.app.repository.PrestamoRepository;
import com.telopresto.app.model.PrestamoConDetalles;

import java.util.List;

public class PrestamoViewModel extends AndroidViewModel {

    private final PrestamoRepository repository;

    public PrestamoViewModel(Application application) {
        super(application);
        repository = new PrestamoRepository(application);
    }

    public void insertar(Prestamo prestamo) {
        repository.insertar(prestamo);
    }

    public void actualizar(Prestamo prestamo) {
        repository.actualizar(prestamo);
    }

    public void eliminar(Prestamo prestamo) {
        repository.eliminar(prestamo);
    }

    public LiveData<List<Prestamo>> obtenerActivos() {
        return repository.obtenerActivos();
    }

    public LiveData<List<Prestamo>> obtenerDevueltos() {
        return repository.obtenerDevueltos();
    }

    public LiveData<Prestamo> obtenerPorId(int id) {
        return repository.obtenerPorId(id);
    }

    public void insertarPersona(Persona persona) {
        repository.insertarPersona(persona);
    }

    public LiveData<List<Persona>> obtenerPersonas() {
        return repository.obtenerPersonas();
    }

    public void insertarCategoria(Categoria categoria) {
        repository.insertarCategoria(categoria);
    }

    public LiveData<List<Categoria>> obtenerCategorias() {
        return repository.obtenerCategorias();
    }
    public void eliminarPersona(Persona persona) {
        repository.eliminarPersona(persona);
    }

    public void eliminarCategoria(Categoria categoria) {
        repository.eliminarCategoria(categoria);
    }

    public LiveData<List<PrestamoConDetalles>> obtenerActivosConDetalles() {
        return repository.obtenerActivosConDetalles();
    }

    public LiveData<List<PrestamoConDetalles>> obtenerDevueltosConDetalles() {
        return repository.obtenerDevueltosConDetalles();
    }

    public LiveData<PrestamoConDetalles> obtenerPorIdConDetalles(int id) {
        return repository.obtenerPorIdConDetalles(id);
    }
}