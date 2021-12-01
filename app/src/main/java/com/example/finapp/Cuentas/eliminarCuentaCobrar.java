package com.example.finapp.Cuentas;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.finapp.R;

import java.util.HashMap;
import java.util.Map;

public class eliminarCuentaCobrar extends AppCompatActivity {

    int id_cuenta, id_empresa, id_metodo_pago, valor, id_cliente, id_tipo_cuenta;
    String concepto, fecha;
    EditText EditValor, EditFecha,EditConcepto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_cuenta_cobrar);

        Bundle bundle = getIntent().getBundleExtra("envio");

        id_cuenta = bundle.getInt("id_cuenta");
        id_empresa  = bundle.getInt("id_empresa");
        id_metodo_pago = bundle.getInt("id_metodo_pago");
        valor = bundle.getInt("valor");
        id_cliente = bundle.getInt("id_cliente");
        id_tipo_cuenta = bundle.getInt("id_tipo_cuenta");

        concepto = bundle.getString("concepto");
        fecha = bundle.getString("fecha");


        //Asignando valores
        EditValor = findViewById(R.id.ValorEdit);
        EditFecha = findViewById(R.id.FechaEdit);
        EditConcepto = findViewById(R.id.ConceptoEdit);

        EditValor.setText(valor + "");
        EditFecha.setText(fecha);
        EditConcepto.setText(concepto);

        EditValor.setEnabled(false);
        EditFecha.setEnabled(false);
        EditConcepto.setEnabled(false);

    }


    public void deleteData(View view) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://teorganizo1.000webhostapp.com/cuentas/delete.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("datos eliminados correctamente")){
                            Toast.makeText(eliminarCuentaCobrar.this, "Cuanta por cobrar eliminada correctamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),CuentasCobrar.class));

                        }else{
                            Toast.makeText(eliminarCuentaCobrar.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(eliminarCuentaCobrar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("id_cuenta", id_cuenta+"");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}