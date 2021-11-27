package com.example.finapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.finapp.R;
import com.example.finapp.estados.estado;
import com.example.finapp.estados.estado_clase_modelo;

import java.util.ArrayList;

public class Login_registro extends AppCompatActivity {

    //Variables
    estado estado;
    estado_clase_modelo estado_clase_modelo;

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<estado_clase_modelo> adaptadoritems;
    String[] ksk = {"sdksdks", "sds", "jjjjjjjjjj"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registro);

        estado = new estado();
        estado_clase_modelo = new estado_clase_modelo();
        estado.select_estado();

         autoCompleteTextView = findViewById(R.id.autoComplete_estado);


       // com.example.finapp.estados.estado_clase_modelo.estados_list.


        for (estado_clase_modelo str: com.example.finapp.estados.estado_clase_modelo.estados_list ){
            Toast.makeText(getApplicationContext(),str.getNombre() + "",Toast.LENGTH_LONG).show();


        }
        //  com.example.finapp.estados.estado_clase_modelo.estados_list.toString();
      //  public static ArrayList<estado_clase_modelo> estados_list = new ArrayList<>();
/*
        adaptadoritems = new ArrayAdapter<estado_clase_modelo>(this,R.layout.logintemplate_estado, Integer.parseInt(com.example.finapp.estados.estado_clase_modelo.estados_list.toString()));
        autoCompleteTextView.setAdapter(adaptadoritems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "fd" + item, Toast.LENGTH_SHORT).show();
            }
        });


*/
    }
}