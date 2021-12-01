package com.example.finapp.empleado;

import java.util.ArrayList;

public class Empleado_Modelo {
    String cedula, nombre, apellido, empresa, cargo;
    public static ArrayList<Empleado_Modelo> empleado_list = new ArrayList<Empleado_Modelo>();

    public Empleado_Modelo() {
    }

    public Empleado_Modelo(String cedula, String nombre, String apellido, String empresa, String cargo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.empresa = empresa;
        this.cargo = cargo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
