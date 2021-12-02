package com.example.finapp.Cuentas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finapp.Cliente.ClienteFragment;
import com.example.finapp.Login.Login;
import com.example.finapp.Login.Login_registro;
import com.example.finapp.R;
import com.example.finapp.estados.estado_clase_modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class agregar_Cuentas extends AppCompatActivity {


    //Variables
    public static ArrayList<String> metodopagoArray = new ArrayList<>();
    public static ArrayList<String> clienteArray = new ArrayList<>();
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView_cliente;
    ArrayAdapter<String> adaptadoritems, adaptadoritems_idcliente;
    int id_metodopago;
    int[] id_empresa, id_cliente;
    EditText valorCuenta, conceptoCuenta, fechaCuenta;
    final Calendar myCalendar = Calendar.getInstance();


    //API'S
    String select_metodopago = "https://teorganizo1.000webhostapp.com/metodopago/select.php";
    String select_cliente = "https://teorganizo1.000webhostapp.com/cliente/select.php";
    String insert = "https://teorganizo1.000webhostapp.com/cuentas/insert.php";
    int id_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cuentas);

        autoCompleteTextView = findViewById(R.id.autoComplete_metodopago);
        autoCompleteTextView_cliente = findViewById(R.id.autoComplete_cliente);
        adaptadoritems = new ArrayAdapter<String>(this, R.layout.logintemplate_estado, metodopagoArray );
        adaptadoritems_idcliente =  new ArrayAdapter<String>(this, R.layout.logintemplate_estado, clienteArray );
        autoCompleteTextView.setAdapter(adaptadoritems);
        autoCompleteTextView_cliente.setAdapter(adaptadoritems_idcliente);

        valorCuenta = findViewById(R.id.valorCuenta);
        conceptoCuenta = findViewById(R.id.conceptoCuenta);
        fechaCuenta = findViewById(R.id.fechaCuenta);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Obtengo el nombre del elemento selecionado, i obtiene la posisicon
                //   Toast.makeText(getApplicationContext(),i+"" ,Toast.LENGTH_LONG).show();
                id_metodopago = i + 1;

            }
        });

        autoCompleteTextView_cliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Obtengo el nombre del elemento selecionado, i obtiene la posisicon
               //t   Toast.makeText(getApplicationContext(),view+"" ,Toast.LENGTH_LONG).show();
           //      ll = view.toString().trim();
                id_position = i;

            }
        });

        final DatePickerDialog.OnDateSetListener date = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int
                            month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDate();
                    }
                };
        fechaCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(agregar_Cuentas.this, date, myCalendar
                        .get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Select_metodopago();
        select_cliente();
        Select_tipocuenta();

    }

    private void updateDate() {
        String myFormat = "yy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,
                Locale.ENGLISH);
        fechaCuenta.setText(sdf.format(myCalendar.getTime()));
    }


    public void Select_tipocuenta() {
        StringRequest request_select_estado = new StringRequest(Request.Method.POST, select_cliente,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        clienteArray.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("cliente");
                            id_cliente = new int[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String nombre = object.getString("nombre");
                                id_cliente[i] = object.getInt("id_cliente");


                                clienteArray.add(nombre);
                                adaptadoritems.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(MainActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(agregar_Cuentas.this);
        requestQueue.add(request_select_estado);
    }

    public void Select_metodopago(){
        StringRequest request_select_estado = new StringRequest(Request.Method.POST, select_metodopago,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        metodopagoArray.clear();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);
                                String nombre = object.getString("nombre");

                                metodopagoArray.add(nombre);
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


        RequestQueue requestQueue = Volley.newRequestQueue(agregar_Cuentas.this);
        requestQueue.add(request_select_estado);
    }


    public void regresar(View view) {
        Intent regresar = new Intent(getApplicationContext(), CuentasCobrar.class);
        startActivity(regresar);
    }

    public void agregarCuenta(View view) {

        if(valorCuenta.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingrasar el valor de la deuda", Toast.LENGTH_SHORT).show();
        }
        else if (conceptoCuenta.getText().toString().equals("")){
            Toast.makeText( this, "Debe ingrasar el concepto de la deuda", Toast.LENGTH_SHORT).show();
        }
        else {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Se esta creando la empresa, por favor espera...");
            progressDialog.show();

            //Tomando valores
            String valorCuenta_str = valorCuenta.getText().toString().trim();
            String conceptoCuenta_str = conceptoCuenta.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();


                    if(response.equalsIgnoreCase("datos insertados")){

                        startActivity(new Intent(getApplicationContext(), CuentasCobrar.class));
                        Toast.makeText(agregar_Cuentas.this, "Se ha registrado el nuevo gasto" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(agregar_Cuentas.this, response, Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("id_empresa", id_empresa[0]+"");
                    params.put("id_metodo_pago", id_metodopago+"");
                    params.put("valor", valorCuenta_str+"");
                    params.put("concepto", conceptoCuenta_str);
                    params.put("fecha", fechaCuenta.getText().toString().trim());
                    params.put("id_cliente", id_cliente[id_position]+"");
                    params.put("id_tipo_cuenta", 2+"");

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(agregar_Cuentas.this);
            requestQueue.add(request);

        }
    }
    public void limpiador(){
        valorCuenta.setText("");
        conceptoCuenta.setText("");

    }
    public void select_cliente() {

        try {
            StringRequest request_select_estado = new StringRequest(Request.Method.POST, select_cliente,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("cliente");


                                id_empresa = new int[jsonArray.length()];



                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);


                                    id_empresa[0]= object.getInt("id_empresa");

                                }


                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Toast.makeText(MainActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request_select_estado);
        } catch (Exception err) {
            Toast.makeText(getApplicationContext(), err.toString(), Toast.LENGTH_LONG).show();
        }

    }
}