package com.telopresto.app.view;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.telopresto.app.R;
import com.telopresto.app.model.Prestamo;
import com.telopresto.app.viewmodel.PrestamoViewModel;
import com.telopresto.app.model.PrestamoConDetalles;

import java.io.File;

public class DetallePrestamoActivity extends AppCompatActivity {

    private PrestamoViewModel viewModel;
    private Prestamo prestamoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_prestamo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView textPersona = findViewById(R.id.textPersona);
        TextView textObjeto = findViewById(R.id.textObjeto);
        TextView textCategoria = findViewById(R.id.textCategoria);
        TextView textFechaPrestamo = findViewById(R.id.textFechaPrestamo);
        TextView textFechaDevolucion = findViewById(R.id.textFechaDevolucion);
        TextView textEstado = findViewById(R.id.textEstado);
        TextView textNotas = findViewById(R.id.textNotas);
        ImageView imageFoto = findViewById(R.id.imageFotoObjeto);
        ImageView imageFirma = findViewById(R.id.imageFirma);
        MaterialButton btnDevuelto = findViewById(R.id.btnMarcarDevuelto);
        MaterialButton btnEliminar = findViewById(R.id.btnEliminar);

        int prestamoId = getIntent().getIntExtra("prestamoId", -1);
        if (prestamoId == -1) {
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(PrestamoViewModel.class);
        viewModel.obtenerPorIdConDetalles(prestamoId).observe(this, item -> {
            if (item == null) { finish(); return; }
            prestamoActual = item.prestamo;

            textObjeto.setText(item.prestamo.objeto);
            textFechaPrestamo.setText(item.prestamo.fechaPrestamo);
            textFechaDevolucion.setText(item.prestamo.fechaDevolucion != null ? item.prestamo.fechaDevolucion : "Sin fecha");
            textEstado.setText(item.prestamo.estado);
            textNotas.setText(item.prestamo.notas != null ? item.prestamo.notas : "Sin notas");
            textPersona.setText(item.persona != null ? item.persona.nombre : "—");
            textCategoria.setText(item.categoria != null ? item.categoria.nombre : "—");

            if (item.prestamo.fotoObjeto != null) {
                File fotoFile = new File(item.prestamo.fotoObjeto);
                if (fotoFile.exists()) {
                    imageFoto.setImageBitmap(BitmapFactory.decodeFile(item.prestamo.fotoObjeto));
                }
            }

            if (item.prestamo.firmaDigital != null) {
                File firmaFile = new File(item.prestamo.firmaDigital);
                if (firmaFile.exists()) {
                    imageFirma.setImageBitmap(BitmapFactory.decodeFile(item.prestamo.firmaDigital));
                }
            }

            if ("devuelto".equals(item.prestamo.estado)) {
                btnDevuelto.setEnabled(false);
                btnDevuelto.setText("Ya devuelto");
            }
        });

        btnDevuelto.setOnClickListener(v -> {
            if (prestamoActual != null) {
                prestamoActual.estado = "devuelto";
                prestamoActual.fechaDevolucion = java.time.LocalDate.now().toString();
                viewModel.actualizar(prestamoActual);
                Toast.makeText(this, "Préstamo marcado como devuelto", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnEliminar.setOnClickListener(v -> {
            if (prestamoActual != null) {
                viewModel.eliminar(prestamoActual);
                Toast.makeText(this, "Préstamo eliminado", Toast.LENGTH_SHORT).show();
                finish();
            }
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