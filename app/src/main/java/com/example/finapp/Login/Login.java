package com.example.finapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finapp.MainActivity;
import com.example.finapp.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText usuario, contraseña, nombre;

    String str_usuario,str_password, str_nombre;
    String url = "https://teorganizo1.000webhostapp.com/login/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contraseña);
        nombre = findViewById(R.id.nombre);
    }

    public void Login(View view) {

        if(usuario.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingrasar su usuario", Toast.LENGTH_SHORT).show();
        }
        else if(nombre.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar tu empresa", Toast.LENGTH_SHORT).show();
        }
        else if(contraseña.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar tu contraseña", Toast.LENGTH_SHORT).show();
        }
        else{


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espera...");

            progressDialog.show();

            str_usuario = usuario.getText().toString().trim();
            str_password = contraseña.getText().toString().trim();
            str_nombre = nombre.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if(response.equalsIgnoreCase("Usuario o contraseña o empresa correctos")){

                        usuario.setText("");
                        contraseña.setText("");
                        nombre.setText("");
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Toast.makeText(Login.this, "Bienvenido" + " " + str_usuario, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("usuario", str_usuario);
                    params.put("contrasena",str_password);
                    params.put("nombre", str_nombre);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(request);


        }
    }

    public void registrar(View view) {

        startActivity(new Intent(getApplicationContext(), Login_registro.class));
    }
}