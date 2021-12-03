package com.example.finapp.Cuentas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finapp.Cliente.ClienteFragment;
import com.example.finapp.R;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class eliminarCuentaCobrar extends Fragment {

    int id_cuenta, id_empresa, id_metodo_pago, valor, id_cliente, id_tipo_cuenta;
    String concepto, fecha;
    EditText EditValor, EditFecha, EditConcepto;
    TextView regresarBtn, eliminarBtn;

    public eliminarCuentaCobrar() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();

        if (bundle != null) {

            id_cuenta = bundle.getInt("id_cuenta");
            id_empresa = bundle.getInt("id_empresa");
            id_metodo_pago = bundle.getInt("id_metodo_pago");
            valor = bundle.getInt("valor");
            id_cliente = bundle.getInt("id_cliente");
            id_tipo_cuenta = bundle.getInt("id_tipo_cuenta");

            concepto = bundle.getString("concepto");
            fecha = bundle.getString("fecha");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_eliminar_cuenta_cobrar, container, false);
        //Asignando valores
        EditValor = view.findViewById(R.id.ValorEdit);
        EditFecha = view.findViewById(R.id.FechaEdit);
        EditConcepto = view.findViewById(R.id.ConceptoEdit);

        regresarBtn = view.findViewById(R.id.regresarBtn);
        eliminarBtn = view.findViewById(R.id.eliminarBtn);

        EditValor.setText(valor + "");
        EditFecha.setText(fecha);
        EditConcepto.setText(concepto);

        EditValor.setEnabled(false);
        EditFecha.setEnabled(false);
        EditConcepto.setEnabled(false);

        regresar();
        deleteData();
        return view;
    }

    public void regresar() {

        regresarBtn.setOnClickListener(v -> {
            CuentasCobrar cuentasCobrar = new CuentasCobrar();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, cuentasCobrar, "cuCo")
                    .commit();
        });
    }


    public void deleteData() {
        eliminarBtn.setOnClickListener(v -> {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("¿Estás seguro?")
                    .setContentText("No podrás recuperar la cuenta que estás eliminando!")
                    .setConfirmText("Eliminar!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Se esta eliminando la cuenta, por favor espera...");
                            pDialog.show();
                            StringRequest request = new StringRequest(Request.Method.POST, "https://teorganizo1.000webhostapp.com/cuentas/delete.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            pDialog.dismiss();
                                            if (response.equalsIgnoreCase("datos eliminados correctamente")) {
                                                CuentasCobrar cuentasCobrar = new CuentasCobrar();

                                                getActivity().getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.frame_layout, cuentasCobrar, "cuCo")
                                                        .commit();
                                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("Cuenta eliminada correctamente")
                                                        .show();

                                            } else {
                                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("Oops...")
                                                        .setContentText(response)
                                                        .show();
                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    pDialog.dismiss();
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Something went wrong!")
                                            .show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("id_cuenta", id_cuenta + "");
                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                            requestQueue.add(request);
                        }
                    })
                    .setCancelButton("Cancelar", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        });
    }
}