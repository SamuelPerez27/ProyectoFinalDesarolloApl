package com.example.finapp.Cliente;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class cliente_modelo {

    //Variables
    public static String totalCliente;
    public static ArrayList<cliente_modelo> clienteList = new ArrayList<>();
    int id, cedula,id_empresa;
    String nombre, apellido;




    //Constructor
    public cliente_modelo(int id, int cedula, String nombre, String apellido,int id_empresa) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id_empresa = id_empresa;
    }
    public cliente_modelo(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
// public static ArrayList<String> totalCliente = new ArrayList<>();

}
