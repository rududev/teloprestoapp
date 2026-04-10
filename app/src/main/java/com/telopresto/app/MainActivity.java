package com.telopresto.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.telopresto.app.view.DetallePrestamoActivity;
import com.telopresto.app.view.HistorialFragment;
import com.telopresto.app.view.NuevoPrestamoActivity;
import com.telopresto.app.view.OpcionesFragment;
import com.telopresto.app.view.PrestamoAdapter;
import com.telopresto.app.viewmodel.PrestamoViewModel;

public class MainActivity extends AppCompatActivity {

    private PrestamoAdapter adapter;
    private TextView textViewVacio;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNav;

    private boolean hayPrestamos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerViewPrestamos);
        textViewVacio = findViewById(R.id.textViewVacio);
        bottomNav = findViewById(R.id.bottomNavigation);

        adapter = new PrestamoAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        PrestamoViewModel viewModel = new ViewModelProvider(this).get(PrestamoViewModel.class);
        viewModel.obtenerActivosConDetalles().observe(this, prestamos -> {
            if (prestamos == null || prestamos.isEmpty()) {
                hayPrestamos = false;
                recyclerView.setVisibility(View.GONE);
                textViewVacio.setVisibility(View.VISIBLE);
            } else {
                hayPrestamos = true;
                recyclerView.setVisibility(View.VISIBLE);
                textViewVacio.setVisibility(View.GONE);
                adapter.setPrestamos(prestamos);
            }
        });

        adapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(this, DetallePrestamoActivity.class);
            intent.putExtra("prestamoId", item.prestamo.id);
            startActivity(intent);
        });

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inicio) {
                mostrarInicio();
                return true;
            } else if (id == R.id.nav_historial) {
                cargarFragment(new HistorialFragment());
                return true;
            } else if (id == R.id.nav_opciones) {
                cargarFragment(new OpcionesFragment());
                return true;
            } else if (id == R.id.nav_añadir) {
                startActivity(new Intent(this, NuevoPrestamoActivity.class));
                return true;
            }
            return true;
        });

        mostrarInicio();
    }

    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
        recyclerView.setVisibility(View.GONE);
        textViewVacio.setVisibility(View.GONE);
    }

    private void mostrarInicio() {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (current != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(current)
                    .commit();
        }
        if (hayPrestamos) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNav.getSelectedItemId() == R.id.nav_añadir) {
            bottomNav.setSelectedItemId(R.id.nav_inicio);
        }
        mostrarInicio();
    }
}