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
import com.example.finapp.Cuentas.CuentasCobrar;
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
        StringRequest request = new StringRequest(Request.Method.POST, "https://teorganizo.000webhostapp.com/cliente/delete.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("datos eliminados correctamente")){
                            Toast.makeText(eliminarCliente.this, "Cuanta por cobrar eliminada correctamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CuentasCobrar.class));

                        }else{
                            Toast.makeText(eliminarCliente.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(eliminarCliente.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("id_cuenta", id+"");
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