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
import com.example.finapp.Login.Login;
import com.example.finapp.Login.Login_registro;
import com.example.finapp.MainActivity;
import com.example.finapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class agregar_cliente extends Fragment {

    TextInputEditText cedulaCliente, nombreCliente, apellidoCliente, empresaCliente;
    int id_empresa;
    String nombreEmpresa, nombreNewCliente, apellidoNewCliente, cedulaNewCliente;
    TextView btnAñadir, btnRegresar, usuario;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    //Api
    String insert = "https://teorganizo1.000webhostapp.com/cliente/insert.php";

    public agregar_cliente() {

    }

    public static agregar_cliente newInstance(String param1, String param2) {
        agregar_cliente fragment = new agregar_cliente();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id_empresa = bundle.getInt("id_empresa");
            nombreEmpresa = bundle.getString("nombreEmpresa");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_agregar_cliente, container, false);
        cedulaCliente = view.findViewById(R.id.cedulaCliente);
        nombreCliente = view.findViewById(R.id.nombreCliente);
        apellidoCliente = view.findViewById(R.id.apellidoCliente);
        empresaCliente = view.findViewById(R.id.empresaCliente);
        btnAñadir = view.findViewById(R.id.btnAñadir);
        btnRegresar = view.findViewById(R.id.btnRegresar);
        usuario = view.findViewById(R.id.usuario);
        usuario.setText(Login.str_usuario);

        regresar();
        insertar();

        empresaCliente.setText(nombreEmpresa);
        empresaCliente.setFocusable(false);
        return view;
    }

    public void regresar() {
        btnRegresar.setOnClickListener(v -> {
            ClienteFragment clienteFragment = new ClienteFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, clienteFragment, "cli")
                    .commit();
        });
    }

    public void insertar() {
        btnAñadir.setOnClickListener(v -> {
            SweetAlertDialog pDialogError = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...");

            if (nombreCliente.getText().toString().equals("")) {
                pDialogError.setContentText("Debe ingresar el nombre del cliente").show();
            } else if (apellidoCliente.getText().toString().equals("")) {
                pDialogError.setContentText("Debe ingresar el apellido del cliente").show();
            } else if (cedulaCliente.getText().toString().equals("")) {
                pDialogError.setContentText("Debe ingresar la cedula del cliente").show();
            } else {

                SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Se esta creando el nuevo cliente, por favor espera...");
                pDialog.show();

                //Tomando valores
                nombreNewCliente = nombreCliente.getText().toString().trim();
                apellidoNewCliente = apellidoCliente.getText().toString().trim();
                cedulaNewCliente = cedulaCliente.getText().toString().trim();


                StringRequest request = new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();

                        if (response.equalsIgnoreCase("datos insertados")) {

                            limpiador();

                            ClienteFragment clienteFragment = new ClienteFragment();

                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_layout, clienteFragment, "cli")
                                    .commit();
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Se ha registrado el nuevo cliente correctamente")
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
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("cedula", cedulaNewCliente);
                        params.put("nombre", nombreNewCliente);
                        params.put("apellido", apellidoNewCliente);
                        params.put("id_empresa", id_empresa + "");

                        return params;

                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(request);
            }
        });
    }

    public void limpiador() {
        nombreCliente.setText("");
        apellidoCliente.setText("");
        cedulaCliente.setText("");
    }
}