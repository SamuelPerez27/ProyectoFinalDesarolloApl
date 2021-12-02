package com.example.finapp.Cuentas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.finapp.R;

public class editar_Cuentas_Cobrar extends AppCompatActivity {
    int id_cuenta, id_empresa, id_metodo_pago, valor, id_cliente, id_tipo_cuenta;
    String concepto, fecha;
    EditText EditValor, EditFecha,EditConcepto;
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

        concepto = bundle.getString("concepto");
        fecha = bundle.getString("fecha");

        EditValor.setText(valor + "");
        EditFecha.setText(fecha);
        EditConcepto.setText(concepto);
    }
}