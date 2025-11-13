package com.example.ac_listar_11_13_2025;

import androidx.annotation.NonNull;

public class Rol {
    public int id;
    public String nombre;
    public String descripcion;

    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Rol () {

    }
    public String getNombre () {
        return nombre;
    }
    public String getDescripcion () {
        return descripcion;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre + " " + descripcion;
    }
}