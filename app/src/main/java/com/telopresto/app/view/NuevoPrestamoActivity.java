package com.telopresto.app.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.telopresto.app.R;
import com.telopresto.app.model.Categoria;
import com.telopresto.app.model.Persona;
import com.telopresto.app.model.Prestamo;
import com.telopresto.app.viewmodel.PrestamoViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NuevoPrestamoActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private PrestamoViewModel viewModel;
    private Spinner spinnerPersona, spinnerCategoria;
    private EditText editObjeto, editFechaPrestamo, editFechaDevolucion, editNotas;
    private ImageView imageFotoPreview;
    private FirmaView firmaView;
    private String rutaFoto = null;

    private List<Persona> listaPersonas = new ArrayList<>();
    private List<Categoria> listaCategorias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_prestamo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spinnerPersona = findViewById(R.id.spinnerPersona);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        editObjeto = findViewById(R.id.editObjeto);
        editFechaPrestamo = findViewById(R.id.editFechaPrestamo);
        editFechaDevolucion = findViewById(R.id.editFechaDevolucion);
        editNotas = findViewById(R.id.editNotas);
        imageFotoPreview = findViewById(R.id.imageFotoPreview);
        firmaView = findViewById(R.id.firmaView);
        MaterialButton btnFoto = findViewById(R.id.btnFoto);
        MaterialButton btnLimpiarFirma = findViewById(R.id.btnLimpiarFirma);
        MaterialButton btnGuardar = findViewById(R.id.btnGuardar);

        viewModel = new ViewModelProvider(this).get(PrestamoViewModel.class);

        viewModel.obtenerPersonas().observe(this, personas -> {
            listaPersonas = personas;
            List<String> nombres = new ArrayList<>();
            for (Persona p : personas) nombres.add(p.nombre);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, nombres);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPersona.setAdapter(adapter);
        });

        viewModel.obtenerCategorias().observe(this, categorias -> {
            listaCategorias = categorias;
            List<String> nombres = new ArrayList<>();
            for (Categoria c : categorias) nombres.add(c.nombre);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, nombres);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategoria.setAdapter(adapter);
        });

        editFechaPrestamo.setOnClickListener(v -> mostrarDatePicker(editFechaPrestamo));
        editFechaDevolucion.setOnClickListener(v -> mostrarDatePicker(editFechaDevolucion));

        btnFoto.setOnClickListener(v -> lanzarCamara());
        btnLimpiarFirma.setOnClickListener(v -> firmaView.limpiar());

        btnGuardar.setOnClickListener(v -> guardarPrestamo());

        editFechaPrestamo.setText(new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(new Date()));
    }

    private void mostrarDatePicker(EditText editText) {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            String fecha = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
            editText.setText(fecha);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void lanzarCamara() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 100);
        } else {
            abrirCamara();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            abrirCamara();
        } else {
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                File fotoFile = crearArchivoFoto();
                Uri fotoUri = FileProvider.getUriForFile(this,
                        "com.telopresto.app.fileprovider", fotoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                Toast.makeText(this, "Error al crear archivo de foto", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File crearArchivoFoto() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String nombreFoto = "FOTO_" + timestamp;
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File foto = File.createTempFile(nombreFoto, ".jpg", dir);
        rutaFoto = foto.getAbsolutePath();
        return foto;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageFotoPreview.setImageURI(Uri.fromFile(new File(rutaFoto)));
        }
    }

    private void guardarPrestamo() {
        if (editObjeto.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "El objeto es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (listaPersonas.isEmpty()) {
            Toast.makeText(this, "Añade al menos una persona primero", Toast.LENGTH_SHORT).show();
            return;
        }
        if (listaCategorias.isEmpty()) {
            Toast.makeText(this, "Añade al menos una categoría primero", Toast.LENGTH_SHORT).show();
            return;
        }

        String rutaFirma = null;
        if (!firmaView.estaVacia()) {
            rutaFirma = guardarFirma(firmaView.getBitmap());
        }

        Prestamo prestamo = new Prestamo();
        prestamo.idPersona = listaPersonas.get(spinnerPersona.getSelectedItemPosition()).id;
        prestamo.idCategoria = listaCategorias.get(spinnerCategoria.getSelectedItemPosition()).id;
        prestamo.objeto = editObjeto.getText().toString().trim();
        prestamo.fechaPrestamo = editFechaPrestamo.getText().toString();
        prestamo.fechaDevolucion = editFechaDevolucion.getText().toString();
        prestamo.estado = "activo";
        prestamo.fotoObjeto = rutaFoto;
        prestamo.firmaDigital = rutaFirma;
        prestamo.notas = editNotas.getText().toString().trim();

        viewModel.insertar(prestamo);
        RecordatorioManager.programarRecordatorio(this, prestamo,
                listaPersonas.get(spinnerPersona.getSelectedItemPosition()).nombre);
        Toast.makeText(this, "Préstamo guardado", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String guardarFirma(Bitmap bitmap) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File firmaFile = new File(dir, "FIRMA_" + timestamp + ".png");
            FileOutputStream fos = new FileOutputStream(firmaFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return firmaFile.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
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