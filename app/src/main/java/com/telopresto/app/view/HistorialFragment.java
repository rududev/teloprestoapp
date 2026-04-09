package com.telopresto.app.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.telopresto.app.R;
import com.telopresto.app.viewmodel.EstadisticasViewModel;
import com.telopresto.app.viewmodel.PrestamoViewModel;

public class HistorialFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textActivos = view.findViewById(R.id.textActivos);
        TextView textTotal = view.findViewById(R.id.textTotal);
        TextView textPersonaMas = view.findViewById(R.id.textPersonaMas);
        TextView textCategoriaMas = view.findViewById(R.id.textCategoriaMas);
        RecyclerView recycler = view.findViewById(R.id.recyclerHistorial);

        PrestamoViewModel prestamoViewModel = new ViewModelProvider(this).get(PrestamoViewModel.class);
        EstadisticasViewModel estadisticasViewModel = new ViewModelProvider(this).get(EstadisticasViewModel.class);

        estadisticasViewModel.contarActivos().observe(getViewLifecycleOwner(), activos ->
                textActivos.setText(activos != null ? String.valueOf(activos) : "0"));

        estadisticasViewModel.contarTotal().observe(getViewLifecycleOwner(), total ->
                textTotal.setText(total != null ? String.valueOf(total) : "0"));

        estadisticasViewModel.personaConMasPrestamos().observe(getViewLifecycleOwner(), nombre ->
                textPersonaMas.setText(nombre != null ? nombre : "—"));

        estadisticasViewModel.categoriaMasPrestada().observe(getViewLifecycleOwner(), nombre ->
                textCategoriaMas.setText(nombre != null ? nombre : "—"));

        PrestamoAdapter adapter = new PrestamoAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setAdapter(adapter);

        prestamoViewModel.obtenerDevueltosConDetalles().observe(getViewLifecycleOwner(), adapter::setPrestamos);
    }
}