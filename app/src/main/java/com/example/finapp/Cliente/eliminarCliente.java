package com.example.finapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.finapp.Cuentas.CuentasCobrar;
import com.example.finapp.Cuentas.eliminarCuentaCobrar;
import com.example.finapp.Login.Login;
import com.example.finapp.Login.Login_registro;
import com.example.finapp.MainActivity;
import com.example.finapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class eliminarCliente extends Fragment {

    // Variables
    int cedula, id, id_empresa;
    String nombre, apellido;
    TextInputEditText cedulaEdit, nombreEdit, apellidoEdit;
    TextView aceptarBtn, regresarBtn;
    TextView usuario;


    //URL de las APIS'S
    String delete = "https://teorganizo1.000webhostapp.com/cliente/delete.php";

    public eliminarCliente() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // reciviendo variables desde el fragment
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            cedula = bundle.getInt("cedula");
            nombre = bundle.getString("nombre");
            apellido = bundle.getString("apellido");
            id_empresa = bundle.getInt("id_empresa");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_eliminar_cliente, container, false);

        //Asignando valores
        cedulaEdit = view.findViewById(R.id.cedulaEdit);
        nombreEdit = view.findViewById(R.id.nombreEdit);
        apellidoEdit = view.findViewById(R.id.apellidoEdit);
        aceptarBtn = view.findViewById(R.id.aceptarBtn);
        regresarBtn = view.findViewById(R.id.regresarBtn);
        usuario = view.findViewById(R.id.usuario);
        usuario.setText(Login.str_usuario);

        regresar();
        deleteDataCliente();

        cedulaEdit.setText(cedula + "");
        nombreEdit.setText(nombre);
        apellidoEdit.setText(apellido);

        cedulaEdit.setFocusable(false);
        nombreEdit.setFocusable(false);
        apellidoEdit.setFocusable(false);

        return view;
    }


    public void regresar() {

        regresarBtn.setOnClickListener(v -> {
            ClienteFragment clienteFragment = new ClienteFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, clienteFragment, "cli")
                    .commit();
        });
    }


    public void limpiador() {
        nombreEdit.setText("");
        apellidoEdit.setText("");
        cedulaEdit.setText("");
    }


    public void deleteDataCliente() {
        aceptarBtn.setOnClickListener(v -> {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("¿Estás seguro?")
                    .setContentText("No podrás recuperar al cliente que estás eliminando!")
                    .setConfirmText("Eliminar!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Se esta eliminando el cliente, por favor espera...");
                            pDialog.show();
                            delete = delete + "?id_cliente=" + id;
                            StringRequest request = new StringRequest(Request.Method.POST, delete,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            pDialog.dismissWithAnimation();
                                            if (response.equalsIgnoreCase("datos eliminados correctamente")) {
                                                ClienteFragment clienteFragment = new ClienteFragment();

                                                getActivity().getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.frame_layout, clienteFragment, "cli")
                                                        .commit();
                                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("Cliente eliminado correctamente")
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
                                    pDialog.dismissWithAnimation();
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Something went wrong!")
                                            .show();
                                }
                            }
                            );

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