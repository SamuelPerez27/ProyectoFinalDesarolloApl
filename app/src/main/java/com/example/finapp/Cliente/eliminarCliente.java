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
import com.example.finapp.R;

import java.util.HashMap;
import java.util.Map;

public class eliminarCliente extends AppCompatActivity {

    // Variables
    int cedula,id, id_empresa;
    String nombre, apellido;
    EditText cedulaEdit, nombreEdit,apellidoEdit;


    //URL de las APIS'S
    String delete = "https://teorganizo1.000webhostapp.com/cliente/delete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_cliente);

        // resiviendo variables desde el frament
        Bundle bundle = getIntent().getBundleExtra("envio");
        id = bundle.getInt("id");
        cedula  = bundle.getInt("cedula");
        nombre  = bundle.getString("nombre");
        apellido  = bundle.getString("apellido");
        id_empresa = bundle.getInt("id_empresa");


        //Asignando valores
        cedulaEdit = findViewById(R.id.cedulaEdit);
        nombreEdit = findViewById(R.id.nombreEdit);
        apellidoEdit = findViewById(R.id.apellidoEdit);

        cedulaEdit.setText(cedula+"");
        nombreEdit.setText(nombre);
        apellidoEdit.setText(apellido);

      //  cedulaEdit.setFocusable(false);
       // nombreEdit.setFocusable(false);
        //apellidoEdit.setFocusable(false);

    }


    public void regresar(View view) {

        Intent intent = new Intent(this, ClienteFragment.class );
        startActivity(intent);
    }

    public void Eliminar(View view) {
       final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Se esta eliminando el cliente, por favor espera...");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                if(response.equalsIgnoreCase("datos insertados")){

                    limpiador();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    Toast.makeText(eliminarCliente.this, "Se ha registrado el cliente correctamente" , Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(eliminarCliente.this, response, Toast.LENGTH_SHORT).show();
                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(eliminarCliente.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id", id+"");

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(eliminarCliente.this);
        requestQueue.add(request);




    }


    public void limpiador(){
        nombreEdit.setText("");
        apellidoEdit.setText("");
        cedulaEdit.setText("");

    }
}