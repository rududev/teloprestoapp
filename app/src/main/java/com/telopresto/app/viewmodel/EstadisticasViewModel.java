package com.telopresto.app.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.telopresto.app.repository.PrestamoRepository;

public class EstadisticasViewModel extends AndroidViewModel {

    private final PrestamoRepository repository;

    public EstadisticasViewModel(Application application) {
        super(application);
        repository = new PrestamoRepository(application);
    }

    public LiveData<Integer> contarActivos() {
        return repository.contarActivos();
    }

    public LiveData<Integer> contarTotal() {
        return repository.contarTotal();
    }
    public LiveData<String> personaConMasPrestamos() {
        return repository.personaConMasPrestamos();
    }

    public LiveData<String> categoriaMasPrestada() {
        return repository.categoriaMasPrestada();
    }
}