package com.example.ac_listar_11_13_2025;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_EDIT = 1001;
    private EditText edtNombreRol;
    private EditText edtDescripcionRol;
    private androidx.appcompat.widget.AppCompatButton btnGuardarRol;
    private RecyclerView rvRoles;
    private RolAdapter adapter;
    private DBHelper dbHelper;
    private final List<Rol> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        edtNombreRol = findViewById(R.id.edtNombreRol);
        edtDescripcionRol = findViewById(R.id.edtDescripcionRol);
        btnGuardarRol = findViewById(R.id.btnGuardarRol);
        rvRoles = findViewById(R.id.rvRoles);

        rvRoles.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RolAdapter(new RolAdapter.OnRolActionListener() {
            @Override
            public void onEdit(Rol rol) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this, EditRolActivity.class);
                intent.putExtra(EditRolActivity.EXTRA_ID, rol.getId());
                intent.putExtra(EditRolActivity.EXTRA_NOMBRE, rol.getNombre());
                intent.putExtra(EditRolActivity.EXTRA_DESCRIPCION, rol.getDescripcion());
                startActivityForResult(intent, REQ_EDIT);
            }

            @Override
            public void onDelete(Rol rol) {
                confirmDelete(rol);
            }
        });
        rvRoles.setAdapter(adapter);

        btnGuardarRol.setOnClickListener(v -> addRol());

        loadRoles();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        if (requestCode == REQ_EDIT && resultCode == RESULT_OK) {
            loadRoles();
        }
    }

    private void loadRoles() {
        data.clear();
        data.addAll(dbHelper.obtenerRoles());
        adapter.submitList(new ArrayList<>(data));
    }

    private void addRol() {
        String nombre = edtNombreRol.getText().toString().trim();
        String descripcion = edtDescripcionRol.getText().toString().trim();
        if (TextUtils.isEmpty(nombre)) {
            edtNombreRol.setError("Requerido");
            return;
        }
        Rol nuevo = new Rol(nombre, descripcion);
        boolean ok = dbHelper.insertarRol2(nuevo);
        if (ok) {
            edtNombreRol.setText("");
            edtDescripcionRol.setText("");
            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
            loadRoles();
        } else {
            Toast.makeText(this, "No se pudo guardar (nombre duplicado?)", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDelete(Rol rol) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("¿Eliminar el rol '" + rol.getNombre() + "'?")
                .setPositiveButton("Eliminar", (d, w) -> {
                    boolean ok = dbHelper.eliminarRol(rol.getId());
                    if (ok) {
                        Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
                        loadRoles();
                    } else {
                        Toast.makeText(this, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}