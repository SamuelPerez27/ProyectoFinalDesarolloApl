package com.example.finapp.Cliente;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finapp.Login.Login_registro;
import com.example.finapp.R;
import com.example.finapp.estados.estado_clase_modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class cliente extends AppCompatActivity {

    //Variables
    TextView total_cliente;

    //URL de las API
    String selectTCliente = "https://teorganizo1.000webhostapp.com/cliente/total_cliente.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cliente);
    }

    public void obtenerTotalCliente(){
        StringRequest request_select_cliente_total = new StringRequest(Request.Method.POST, selectTCliente,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");

                            cliente_clase_modelo.total_cliente.add(2);
                          /*  for(int i=0;i<jsonArray.length() + 1;i++){

                                JSONObject object = jsonArray.getJSONObject(i);
                                int total = object.getInt("total");

                                cliente_clase_modelo.total_cliente.add(total);

                            }*/
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


        RequestQueue requestQueue = Volley.newRequestQueue(cliente.this);
        requestQueue.add(request_select_cliente_total);
    }


    public void prueba(View view) {
        String ll = getString(R.string.nombreprueba);


        ll = "SAMUEL";
    }
}
