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

public class editarCliente extends AppCompatActivity {


    // Variables
    int cedula,id, id_empresa;
    String nombre, apellido;
    EditText cedulaEdit, nombreEdit,apellidoEdit;

    //URL de las APIS'S
    String update = "https://teorganizo1.000webhostapp.com/cliente/update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

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



    }

    public void regresar(View view) {

        Intent intent = new Intent(this, ClienteFragment.class );
        startActivity(intent);
    }

    public void aceptar(View view) {

        if(nombreEdit.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingrasar un nombre", Toast.LENGTH_SHORT).show();
        }
        else if (cedulaEdit.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingrasar una cedula", Toast.LENGTH_SHORT).show();
        }
        else if (apellidoEdit.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingrasar un apellido", Toast.LENGTH_SHORT).show();
        }
        else{

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Se esta editado la empresa, por favor espera...");
            progressDialog.show();

            nombre = nombreEdit.getText().toString().trim();
            apellido = apellidoEdit.getText().toString().trim();
            cedula = Integer.parseInt(cedulaEdit.getText().toString());

            StringRequest request = new StringRequest(Request.Method.POST, update, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if(response.equalsIgnoreCase("datos actualizados")){

                        limpiador();
                        Toast.makeText(editarCliente.this, "Se ha editado la empresa correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ClienteFragment.class));

                    }
                    else{
                        Toast.makeText(editarCliente.this, "Error al insertar datos", Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(editarCliente.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("cedula", cedula +"");
                    params.put("nombre", nombre  );
                    params.put("apellido", apellido);
                    params.put("id", id+"");

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(editarCliente.this);
            requestQueue.add(request);

        }



    }
    public void limpiador(){
        nombreEdit.setText("");
        apellidoEdit.setText("");
        cedulaEdit.setText("");

    }
}