package com.example.finapp.cargo;

import java.util.ArrayList;

public class Cargo_Modelo {
    int id_cargo, salario;
    String nombre;
    public static ArrayList<String> cargo_list = new ArrayList<>();

    public Cargo_Modelo(){

    }

    public Cargo_Modelo(int id_cargo, int salario, String nombre) {
        this.id_cargo = id_cargo;
        this.salario = salario;
        this.nombre = nombre;
    }

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
