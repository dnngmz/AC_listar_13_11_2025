package com.example.ac_listar_11_13_2025;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditRolActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_NOMBRE = "extra_nombre";
    public static final String EXTRA_DESCRIPCION = "extra_descripcion";

    private EditText etNombre;
    private EditText etDescripcion;
    private int rolId;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rol);
        dbHelper = new DBHelper(this);

        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);

        rolId = getIntent().getIntExtra(EXTRA_ID, -1);
        String nombre = getIntent().getStringExtra(EXTRA_NOMBRE);
        String descripcion = getIntent().getStringExtra(EXTRA_DESCRIPCION);

        if (rolId == -1) {
            Toast.makeText(this, "Rol inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etNombre.setText(nombre);
        etDescripcion.setText(descripcion);

        findViewById(R.id.btnGuardar).setOnClickListener(v -> save());
    }

    private void save() {
        String nuevoNombre = etNombre.getText().toString().trim();
        String nuevaDesc = etDescripcion.getText().toString().trim();
        if (TextUtils.isEmpty(nuevoNombre)) {
            etNombre.setError("Requerido");
            return;
        }
        boolean ok = dbHelper.actualizarRol(rolId, nuevoNombre, nuevaDesc);
        if (ok) {
            Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "No se pudo actualizar", Toast.LENGTH_SHORT).show();
        }
    }
}
