package com.example.finapp.estados;

import java.util.ArrayList;

public class estado_clase_modelo {

    int id_estado;
    String codigo, nombre;
    public static ArrayList<String> estados_list = new ArrayList<>();

    public estado_clase_modelo() {
    }

    public estado_clase_modelo(int id_estado, String codigo, String nombre) {
        this.id_estado = id_estado;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
