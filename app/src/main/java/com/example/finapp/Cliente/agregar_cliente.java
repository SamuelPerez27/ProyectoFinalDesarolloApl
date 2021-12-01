package com.example.finapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.finapp.Login.Login;
import com.example.finapp.Login.Login_registro;
import com.example.finapp.MainActivity;
import com.example.finapp.R;

import java.util.HashMap;
import java.util.Map;

public class agregar_cliente extends AppCompatActivity {

    EditText cedulaCliente, nombreCliente, apellidoCliente, empresaCliente;
    int id_empresa, cedulaNewCliente;
    String nombreEmpresa, nombreNewCliente, apellidoNewCliente;


    //Api
    String insert = "https://teorganizo1.000webhostapp.com/cliente/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        cedulaCliente = findViewById(R.id.cedulaCliente);
        nombreCliente = findViewById(R.id.nombreCliente);
        apellidoCliente = findViewById(R.id.apellidoCliente);
        empresaCliente = findViewById(R.id.empresaCliente);


        Bundle bundle = getIntent().getBundleExtra("envio");
        id_empresa = bundle.getInt("id_empresa");
        nombreEmpresa = bundle.getString("nombreEmpresa");



        empresaCliente.setText(nombreEmpresa);
        empresaCliente.setFocusable(false);
    }

    public void regresar(View view) {

        limpiador();
        Intent intent = new Intent(this, ClienteFragment.class );
        startActivity(intent);
    }

    public void insertar(View view) {


        if(nombreCliente.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingrasar el nombre del Cliente", Toast.LENGTH_SHORT).show();
        }
        else if (apellidoCliente.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingrasar el apellido del Cliente", Toast.LENGTH_SHORT).show();
        }
        else if (cedulaCliente.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingrasar la cedula del Cliente", Toast.LENGTH_SHORT).show();
        }
        else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Se esta creando el nuevo cliente, por favor espera...");
            progressDialog.show();

            //Tomando valores
            nombreNewCliente = nombreCliente.getText().toString().trim();
            apellidoNewCliente = apellidoCliente.getText().toString().trim();
            cedulaNewCliente = cedulaCliente.getInputType();


            StringRequest request = new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if(response.equalsIgnoreCase("datos insertados")){

                        limpiador();
                        startActivity(new Intent(getApplicationContext(), ClienteFragment.class));
                        Toast.makeText(agregar_cliente.this, "Se ha registrado el nuevo cliente correctamente"  , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(agregar_cliente.this, response, Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(agregar_cliente.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("cedula", cedulaNewCliente+"");
                    params.put("nombre", nombreNewCliente);
                    params.put("apellido", apellidoNewCliente);
                    params.put("id_empresa", id_empresa +"");

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(agregar_cliente.this);
            requestQueue.add(request);

        }
    }

    public void limpiador(){
        nombreCliente.setText("");
        apellidoCliente.setText("");
        cedulaCliente.setText("");
    }
}