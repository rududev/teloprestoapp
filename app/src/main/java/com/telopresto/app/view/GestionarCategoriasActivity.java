package com.telopresto.app.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.telopresto.app.R;
import com.telopresto.app.model.Categoria;
import com.telopresto.app.viewmodel.PrestamoViewModel;

import java.util.ArrayList;
import java.util.List;

public class GestionarCategoriasActivity extends AppCompatActivity {

    private PrestamoViewModel viewModel;
    private android.widget.EditText editNombre;
    private List<Categoria> listaCategorias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_categorias);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editNombre = findViewById(R.id.editNombreCategoria);
        MaterialButton btnAnadir = findViewById(R.id.btnAnadirCategoria);
        RecyclerView recycler = findViewById(R.id.recyclerCategorias);

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
                Categoria c = listaCategorias.get(position);
                ((TextView) holder.itemView).setText(c.nombre);
                holder.itemView.setOnLongClickListener(v -> {
                    viewModel.eliminarCategoria(c);
                    return true;
                });
            }

            @Override
            public int getItemCount() {
                return listaCategorias.size();
            }
        };

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        viewModel.obtenerCategorias().observe(this, categorias -> {
            listaCategorias = categorias;
            adapter.notifyDataSetChanged();
        });

        btnAnadir.setOnClickListener(v -> {
            String nombre = editNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
                return;
            }
            Categoria categoria = new Categoria();
            categoria.nombre = nombre;
            viewModel.insertarCategoria(categoria);
            editNombre.setText("");
            Toast.makeText(this, "Categoría añadida", Toast.LENGTH_SHORT).show();
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