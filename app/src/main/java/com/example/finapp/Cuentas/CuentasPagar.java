package com.example.finapp.Cuentas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CuentasPagar extends AppCompatActivity {

    //Variablidad
    ListView listviewCuentas;
    ProgressDialog progressDialog;
    int[] id_cuenta, id_empresa,id_metodo_pago, valor, id_cliente, id_tipocuenta;
    String[] empresa_nombre, metodo_pago_nombre, concepto, fecha, nombre_cliente, nombre_tipocuenta;
    Button BtnCrear;

    // Url apis
    String Selectcuentascorar = "https://teorganizo1.000webhostapp.com/cuentas/selectcuentaspagar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas_pagar);

        //Inicializar
        try {
            listviewCuentas = findViewById(R.id.listCuentaspagar);
        }
        catch (Exception er){
            Toast.makeText(getApplicationContext(), er.toString(), Toast.LENGTH_LONG).show();
        }

        select_cuentascobrar();
    }
    public void select_cuentascobrar(){

        StringRequest request_select_cuentas = new StringRequest(Request.Method.POST, Selectcuentascorar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");

                            try {
                                id_cuenta = new int[jsonArray.length()];
                                id_empresa = new int[jsonArray.length()];
                                id_metodo_pago = new int[jsonArray.length()];
                                valor = new int[jsonArray.length()];
                                id_cliente = new int[jsonArray.length()];
                                id_tipocuenta = new int[jsonArray.length()];

                                empresa_nombre = new String[jsonArray.length()];
                                metodo_pago_nombre = new String[jsonArray.length()];
                                concepto = new String[jsonArray.length()];
                                fecha = new String[jsonArray.length()];
                                nombre_cliente = new String[jsonArray.length()];
                                nombre_tipocuenta = new String[jsonArray.length()];

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //  progressDialog.dismiss();
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    id_cuenta[i] = object.getInt("id_cuenta");
                                    id_empresa[i] = object.getInt("id_empresa");
                                    id_metodo_pago[i] = object.getInt("id_metodo_pago");
                                    valor[i] = object.getInt("valor");
                                    id_cliente[i] = object.getInt("id_cliente");
                                    id_tipocuenta[i] = object.getInt("id_tipocuenta");

                                    empresa_nombre[i] = object.getString("empresa_nombre");
                                    metodo_pago_nombre[i] = object.getString("metodo_pago_nombre");
                                    concepto[i] = object.getString("concepto");
                                    fecha[i] = object.getString("fecha");
                                    nombre_cliente[i] = object.getString("nombre_cliente");
                                    nombre_tipocuenta[i] = object.getString("nombre_tipocuenta");

                                    CuentasPagar.adapter adapterclass = new CuentasPagar.adapter();
                                    listviewCuentas.setAdapter(adapterclass);
                                }
                            }
                            catch (Exception er){
                                Toast.makeText(getApplicationContext(), er.toString(), Toast.LENGTH_LONG).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request_select_cuentas);

    }

    public void agregarCuenta(View view) {
        startActivity(new Intent(getApplicationContext(), agregar_Cuentas.class));
    }

    private class adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return id_cliente.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Variables

            TextView valortv, conceptotv, fechatv;
            Button editarCuentaCobrar, eliminarCuentaCobrar;

            convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cuentapagar_template, parent, false);

            valortv = convertView.findViewById(R.id.valorCliente);
            conceptotv = convertView.findViewById(R.id.conceptoCliente);
            fechatv = convertView.findViewById(R.id.fechaCliente);

            editarCuentaCobrar = convertView.findViewById(R.id.editarCuentaCobrar);
            eliminarCuentaCobrar = convertView.findViewById(R.id.eliminarCuentaCobrar);

            //Metodo Editar cliente
            editarCuentaCobrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id_cuenta",  id_cuenta[position]);
                        bundle.putInt("id_empresa", id_empresa[position]);
                        bundle.putInt("id_metodo_pago", id_metodo_pago[position]);
                        bundle.putInt("valor", valor[position]);
                        bundle.putString("concepto", concepto[position]);
                        bundle.putString("fecha", fecha[position]);
                        bundle.putInt("id_cliente", id_cliente[position]);
                        bundle.putString("nombreCliente", nombre_cliente[position]);
                        bundle.putString("nombreMetodoPago", metodo_pago_nombre[position]);



                        Intent intent = new Intent(getApplicationContext(), editar_Cuentas_Cobrar.class);
                        intent.putExtra("envio", bundle);
                        startActivity(intent);

                    }
                    catch (Exception er){
                        Toast.makeText(getApplicationContext(), er.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //Metodo eliminar
            eliminarCuentaCobrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id_cuenta",  id_cuenta[position]);
                        bundle.putInt("id_empresa", id_empresa[position]);
                        bundle.putInt("id_metodo_pago", id_metodo_pago[position]);
                        bundle.putInt("valor", valor[position]);
                        bundle.putString("concepto", concepto[position]);
                        bundle.putString("fecha", fecha[position]);
                        bundle.putInt("id_cliente", id_cliente[position]);

                        Intent intent = new Intent(getApplicationContext(), eliminarCuentaCobrar.class);
                        intent.putExtra("envio", bundle);
                        startActivity(intent);

                    }
                    catch (Exception er){
                        Toast.makeText(getApplicationContext(), er.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            try {

                valortv.setText(  valor[position] + ""   );
                conceptotv.setText(  concepto[position]);
                fechatv.setText(fecha[position] +"");
            }
            catch (Exception er){
                Toast.makeText(getApplicationContext(), er.toString(), Toast.LENGTH_SHORT).show();
            }


            return convertView;
        }

    }
}