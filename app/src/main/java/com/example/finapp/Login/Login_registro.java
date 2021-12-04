package com.example.finapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finapp.MainActivity;
import com.example.finapp.R;
import com.example.finapp.Tools;
import com.example.finapp.estados.estado_clase_modelo;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login_registro extends AppCompatActivity {

    //Variables
    public static ArrayList<String> estadosArray = new ArrayList<>();
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adaptadoritems;
    estado_clase_modelo modelo;
    EditText nombreEmpresa, propietarioEmpresa, descricionEmpresa, contrasenaEmpresa,usuarioEmpresa;
    String nombreEmpresa_txt, propietarioEmpresa_txt, descricionEmpresa_txt, contrasenaEmpresa_txt, usuarioEmpresa_txt;
    TextInputLayout estado;
    int id_estado;

    //URL de las API
    String select = "https://teorganizo1.000webhostapp.com/estado/select.php";
    String insert = "https://teorganizo1.000webhostapp.com/empresa/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registro);
        Tools.setSystemBarLight(this);
        Tools.setSystemBarColor(this,R.color.white);

        //Estableciendo variables
        modelo = new estado_clase_modelo();
        autoCompleteTextView = findViewById(R.id.autoComplete_estado);
        adaptadoritems = new ArrayAdapter<String>(this, R.layout.logintemplate_estado, estado_clase_modelo.estados_list );
        autoCompleteTextView.setAdapter(adaptadoritems);
        nombreEmpresa = findViewById(R.id.nombreEmpresa);
        estado = findViewById(R.id.textInputLayout);
        propietarioEmpresa = findViewById(R.id.propietarioEmpresa);
        descricionEmpresa = findViewById(R.id.descricionEmpresa);
        contrasenaEmpresa = findViewById(R.id.contrasenaEmpresa);
        usuarioEmpresa = findViewById(R.id.usuarioEmpresa);


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Obtengo el nombre del elemento selecionado, i obtiene la posisicon
             //   Toast.makeText(getApplicationContext(),i+"" ,Toast.LENGTH_LONG).show();
                id_estado = i + 1;
            }
        });

        //Llamo al metodo de select estado
        select_estado();
    }

    //Selecciona los nombres de los estados
    public void select_estado() {
        StringRequest request_select_estado = new StringRequest(Request.Method.POST, select,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        estadosArray.clear();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);
                                String nombre = object.getString("nombre");

                                estado_clase_modelo.estados_list.add(nombre);
                                adaptadoritems.notifyDataSetChanged();
                            }
                        }
                        catch (JSONException e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(MainActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(Login_registro.this);
        requestQueue.add(request_select_estado);

    }


    public void regresar(View view) {

        startActivity(new Intent(getApplicationContext(), Login.class));

        //Limpiando variables
          limpiador();
    }

    public void registrar(View view) {

        if(nombreEmpresa.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingrasar una contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(descricionEmpresa.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar una descripción", Toast.LENGTH_SHORT).show();
        }
        else if(propietarioEmpresa.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar un propietario", Toast.LENGTH_SHORT).show();
        }
        else if(usuarioEmpresa.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar un usuario", Toast.LENGTH_SHORT).show();
        }
        else if(contrasenaEmpresa.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(estado.getHint().toString().equals("")){
            Toast.makeText(this, "Debe ingresar un estado", Toast.LENGTH_SHORT).show();
        }
        else {
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Se esta creando la empresa, por favor espera...");
            pDialog.show();

            //Tomando valores
            nombreEmpresa_txt = nombreEmpresa.getText().toString().trim();
            descricionEmpresa_txt = descricionEmpresa.getText().toString().trim();
            propietarioEmpresa_txt = propietarioEmpresa.getText().toString().trim();
            usuarioEmpresa_txt = usuarioEmpresa.getText().toString().trim();
            contrasenaEmpresa_txt = contrasenaEmpresa.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();

                    if(response.equalsIgnoreCase("datos insertados")){

                        limpiador();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        Toast.makeText(Login_registro.this, "Se ha registrado la empresa correctamente" + nombreEmpresa_txt , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Login_registro.this, response, Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    Toast.makeText(Login_registro.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("nombre", nombreEmpresa_txt);
                    params.put("descripcion",descricionEmpresa_txt);
                    params.put("propietario", propietarioEmpresa_txt);
                    params.put("usuario", usuarioEmpresa_txt);
                    params.put("contrasena", contrasenaEmpresa_txt);
                    params.put("id_estado", id_estado+"");

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Login_registro.this);
            requestQueue.add(request);

        }
    }

    //Limpiar los valores de los txt
    public void limpiador(){
        nombreEmpresa.setText("");
        descricionEmpresa.setText("");
        propietarioEmpresa.setText("");
        usuarioEmpresa.setText("");
        contrasenaEmpresa.setText("");
        estado.setHint("Selecciona un estado");
    }
}