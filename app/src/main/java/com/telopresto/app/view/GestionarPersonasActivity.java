package com.telopresto.app.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.telopresto.app.R;
import com.telopresto.app.model.Persona;
import com.telopresto.app.viewmodel.PrestamoViewModel;

import java.util.ArrayList;
import java.util.List;

public class GestionarPersonasActivity extends AppCompatActivity {

    private PrestamoViewModel viewModel;
    private EditText editNombre, editTelefono;
    private List<Persona> listaPersonas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_personas);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editNombre = findViewById(R.id.editNombrePersona);
        editTelefono = findViewById(R.id.editTelefonoPersona);
        MaterialButton btnAnadir = findViewById(R.id.btnAnadirPersona);
        RecyclerView recycler = findViewById(R.id.recyclerPersonas);

        viewModel = new ViewModelProvider(this).get(PrestamoViewModel.class);

        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView tv = new TextView(parent.getContext());
                tv.setPadding(32, 24, 32, 24);
                tv.setTextSize(16);
                return new RecyclerView.ViewHolder(tv) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                Persona p = listaPersonas.get(position);
                ((TextView) holder.itemView).setText(p.nombre + " — " + p.telefono);
                holder.itemView.setOnLongClickListener(v -> {
                    viewModel.eliminarPersona(p);
                    return true;
                });
            }

            @Override
            public int getItemCount() {
                return listaPersonas.size();
            }
        };

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        viewModel.obtenerPersonas().observe(this, personas -> {
            listaPersonas = personas;
            adapter.notifyDataSetChanged();
        });

        btnAnadir.setOnClickListener(v -> {
            String nombre = editNombre.getText().toString().trim();
            String telefono = editTelefono.getText().toString().trim();
            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
                return;
            }
            Persona persona = new Persona();
            persona.nombre = nombre;
            persona.telefono = telefono;
            viewModel.insertarPersona(persona);
            editNombre.setText("");
            editTelefono.setText("");
            Toast.makeText(this, "Persona añadida", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}