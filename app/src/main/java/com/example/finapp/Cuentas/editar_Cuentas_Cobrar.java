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
import com.example.finapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class editar_Cuentas_Cobrar extends AppCompatActivity {

    //Variables
    public static ArrayList<String> metodopagoArray = new ArrayList<>();
    public static ArrayList<String> clienteArray = new ArrayList<>();
    int id_cuenta, id_empresa, id_metodo_pago, valor, id_cliente, id_tipo_cuenta;
    String concepto, fecha, nombre_cliente, metodo_pago_nombre;
    EditText EditValor, EditFecha,EditConcepto, EditfechaCuenta;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView_cliente;
    int[] id_cliente_,id_empresa_;
    ArrayAdapter<String> adaptadoritems, adaptadoritems_idcliente;
    int id_metodopago, id_position;

    final Calendar myCalendar = Calendar.getInstance();

    //API'S
    String select_metodopago = "https://teorganizo1.000webhostapp.com/metodopago/select.php";
    String select_cliente = "https://teorganizo1.000webhostapp.com/cliente/select.php";
    String update = "https://teorganizo1.000webhostapp.com/cuentas/update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuentas_cobrar);

        Bundle bundle = getIntent().getBundleExtra("envio");

        id_cuenta = bundle.getInt("id_cuenta");
        id_empresa  = bundle.getInt("id_empresa");
        id_metodo_pago = bundle.getInt("id_metodo_pago");
        valor = bundle.getInt("valor");
        id_cliente = bundle.getInt("id_cliente");
        id_tipo_cuenta = bundle.getInt("id_tipo_cuenta");
        metodo_pago_nombre = bundle.getString("metodo_pago_nombre");
        nombre_cliente = bundle.getString("nombre_cliente");
        concepto = bundle.getString("concepto");
        EditfechaCuenta = findViewById(R.id.fechaCuenta);

        autoCompleteTextView = findViewById(R.id.autoComplete_metodopagoEdit);
        autoCompleteTextView_cliente = findViewById(R.id.autoComplete_clienteEdit);

        adaptadoritems = new ArrayAdapter<String>(this, R.layout.logintemplate_estado, metodopagoArray);
        adaptadoritems_idcliente =  new ArrayAdapter<String>(this, R.layout.logintemplate_estado, clienteArray );

        autoCompleteTextView.setAdapter(adaptadoritems);
        autoCompleteTextView_cliente.setAdapter(adaptadoritems_idcliente);

        EditValor = findViewById(R.id.valorCuenta);
        EditFecha = findViewById(R.id.fechaCuenta);
        EditConcepto = findViewById(R.id.conceptoCuenta);

        EditValor.setText(valor + "");
        EditFecha.setText(fecha);
        EditConcepto.setText(concepto);

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

       EditfechaCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(editar_Cuentas_Cobrar.this, date, myCalendar
                        .get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Select_metodopago();
        select_cliente();
    }

    private void updateDate() {
        String myFormat = "yy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,
                Locale.ENGLISH);
        EditfechaCuenta.setText(sdf.format(myCalendar.getTime()));
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


        RequestQueue requestQueue = Volley.newRequestQueue(editar_Cuentas_Cobrar.this);
        requestQueue.add(request_select_estado);

    }
    public void select_cliente(){
        StringRequest request_select_estado = new StringRequest(Request.Method.POST, select_cliente,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        clienteArray.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("cliente");
                            id_cliente_ = new int[jsonArray.length()];
                            id_empresa_ = new int[jsonArray.length()];


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String nombre = object.getString("nombre");
                                id_cliente_[i] = object.getInt("id_cliente");

                                id_empresa_[0]= object.getInt("id_empresa");

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


        RequestQueue requestQueue = Volley.newRequestQueue(editar_Cuentas_Cobrar.this);
        requestQueue.add(request_select_estado);
    }

    public void regresar(View view) {
        Intent regresar = new Intent(getApplicationContext(), CuentasCobrar.class);
        startActivity(regresar);
    }

    public void editar(View view) {
        if(EditValor.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingrasar un valor", Toast.LENGTH_SHORT).show();
        }
        else if (EditFecha.getText().toString().equals("")){
            Toast.makeText( this, "Debe ingrasar el la fecha", Toast.LENGTH_SHORT).show();
        }
        else if (EditConcepto.getText().toString().equals("")){
            Toast.makeText( this, "Debe ingrasar    ", Toast.LENGTH_SHORT).show();
        }
        else {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Se esta Editando el cobro, por favor espera...");
            progressDialog.show();

            //Tomando valores
            String valorCuenta_str = EditValor.getText().toString().trim();
            String conceptoCuenta_str = EditConcepto.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, update, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    Toast.makeText(editar_Cuentas_Cobrar.this, ""+id_cuenta , Toast.LENGTH_SHORT).show();

                    if(response.equalsIgnoreCase("datos insertados")){

                        startActivity(new Intent(getApplicationContext(), CuentasCobrar.class));
                        Toast.makeText(editar_Cuentas_Cobrar.this, "Se ha editado la empresa correctamente" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(editar_Cuentas_Cobrar.this, response, Toast.LENGTH_SHORT).show();
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
                    params.put("id_empresa", id_empresa_[0]+"");
                    params.put("id_metodo_pago", id_metodopago+"");
                    params.put("valor", valorCuenta_str+"");
                    params.put("concepto", conceptoCuenta_str);
                    params.put("fecha", EditfechaCuenta.getText().toString().trim());
                    params.put("id_cliente", id_cliente_[id_position]+"");
                    params.put("id_tipo_cuenta", 2+"");
                    params.put("id_cuenta", id_cuenta+"");


                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(editar_Cuentas_Cobrar.this);
            requestQueue.add(request);

        }

    }
}