package com.telopresto.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.telopresto.app.R;

public class OpcionesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_opciones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnPersonas = view.findViewById(R.id.btnGestionarPersonas);
        MaterialButton btnCategorias = view.findViewById(R.id.btnGestionarCategorias);

        btnPersonas.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), GestionarPersonasActivity.class)));

        btnCategorias.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), GestionarCategoriasActivity.class)));
    }
}