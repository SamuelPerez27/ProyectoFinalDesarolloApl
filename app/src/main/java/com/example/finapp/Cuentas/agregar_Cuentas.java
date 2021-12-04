package com.example.finapp.Cuentas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
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
import com.example.finapp.Cliente.agregar_cliente;
import com.example.finapp.Login.Login;
import com.example.finapp.Login.Login_registro;
import com.example.finapp.R;
import com.example.finapp.estados.estado_clase_modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class agregar_Cuentas extends Fragment {

    //Variables
    public static ArrayList<String> metodopagoArray = new ArrayList<>();
    public static ArrayList<String> clienteArray = new ArrayList<>();

    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView_cliente;

    ArrayAdapter<String> adaptadoritems, adaptadoritems_idcliente;

    int id_metodopago;
    int[] id_empresa, id_cliente;
    EditText valorCuenta, conceptoCuenta, fechaCuenta;
    final Calendar myCalendar = Calendar.getInstance();
    TextView btnRegresar, btnAgregar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    //API'S
    String select_metodopago = "https://teorganizo1.000webhostapp.com/metodopago/select.php";
    String select_cliente = "https://teorganizo1.000webhostapp.com/cliente/select.php";
    String insert = "https://teorganizo1.000webhostapp.com/cuentas/insert.php";

    int id_position;

    public agregar_Cuentas() {

    }

    public static agregar_Cuentas newInstance(String param1, String param2) {
        agregar_Cuentas fragment = new agregar_Cuentas();
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
//            id_empresa = bundle.getInt("id_empresa");
//            nombreEmpresa = bundle.getString("nombreEmpresa");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_agregar_cuentas, container, false);
        autoCompleteTextView = view.findViewById(R.id.autoComplete_metodopago);
        autoCompleteTextView_cliente = view.findViewById(R.id.autoComplete_cliente);

        adaptadoritems = new ArrayAdapter<String>(getActivity(), R.layout.logintemplate_estado, metodopagoArray);
        adaptadoritems_idcliente = new ArrayAdapter<String>(getActivity(), R.layout.logintemplate_estado, clienteArray);
        autoCompleteTextView.setAdapter(adaptadoritems);
        autoCompleteTextView_cliente.setAdapter(adaptadoritems_idcliente);

        valorCuenta = view.findViewById(R.id.valorCuenta);
        conceptoCuenta = view.findViewById(R.id.conceptoCuenta);
        fechaCuenta = view.findViewById(R.id.fechaCuenta);

        btnAgregar = view.findViewById(R.id.btnAgregarC);
        btnRegresar = view.findViewById(R.id.btnRegresar);

        autoCompleteTextView.setOnItemClickListener((adapterView, view1, i, l) -> {
            //Obtengo el nombre del elemento selecionado, i obtiene la posicion
            id_metodopago = i + 1;
        });

        autoCompleteTextView_cliente.setOnItemClickListener((adapterView, view12, i, l) -> {
            //Obtengo el nombre del elemento selecionado, i obtiene la posisicon
            id_position = i;
        });

        final DatePickerDialog.OnDateSetListener date = (view13, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        };
        fechaCuenta.setOnClickListener(v -> {
            new DatePickerDialog(getActivity(), date, myCalendar
                    .get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        Select_metodopago();
        Select_tipocuenta();
        select_cliente();
        regresar();
        agregarCuenta();
        return view;
    }

    private void updateDate() {
        String myFormat = "yy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,
                Locale.ENGLISH);
        fechaCuenta.setText(sdf.format(myCalendar.getTime()));
    }


    public void Select_tipocuenta() {
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Obteniendo la data, por favor espera...");
        pDialog.show();
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

                                pDialog.dismiss();
                                JSONObject object = jsonArray.getJSONObject(i);
                                String nombre = object.getString("nombre");
                                id_cliente[i] = object.getInt("id_cliente");

                                clienteArray.add(nombre);
                                adaptadoritems.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(e.getMessage())
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(error.getMessage())
                        .show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request_select_estado);
    }

    public void Select_metodopago() {
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Obteniendo la data, por favor espera...");
        pDialog.show();
        StringRequest request_select_estado = new StringRequest(Request.Method.POST, select_metodopago,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        metodopagoArray.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                pDialog.dismiss();

                                JSONObject object = jsonArray.getJSONObject(i);
                                String nombre = object.getString("nombre");

                                metodopagoArray.add(nombre);
                                adaptadoritems.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(e.getMessage())
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(error.getMessage())
                        .show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request_select_estado);
    }


    public void regresar() {
        btnRegresar.setOnClickListener(v -> {
            CuentasCobrar cuentasCobrar = new CuentasCobrar();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, cuentasCobrar, "cli")
                    .commit();
        });
    }

    public void agregarCuenta() {
        btnAgregar.setOnClickListener(v -> {

            SweetAlertDialog pDialogError = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...");

            if (valorCuenta.getText().toString().equals("")) {
                pDialogError.setContentText("Debe ingresar el valor de la cuenta").show();
            } else if (conceptoCuenta.getText().toString().equals("")) {
                pDialogError.setContentText("Debe ingresar el concepto de la cuenta").show();
            } else {

                SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Se esta creando la nueva cuenta cliente, por favor espera...");
                pDialog.show();

                //Tomando valores
                String valorCuenta_str = valorCuenta.getText().toString().trim();
                String conceptoCuenta_str = conceptoCuenta.getText().toString().trim();

                StringRequest request = new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();

                        if (response.equalsIgnoreCase("datos insertados")) {

                            CuentasCobrar cuentasCobrar = new CuentasCobrar();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_layout, cuentasCobrar, "cuCo")
                                    .commit();
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Se ha registrado la nueva cuenta correctamente")
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
                        params.put("id_empresa", id_empresa[0] + "");
                        params.put("id_metodo_pago", id_metodopago + "");
                        params.put("valor", valorCuenta_str + "");
                        params.put("concepto", conceptoCuenta_str);
                        params.put("fecha", fechaCuenta.getText().toString().trim());
                        params.put("id_cliente", id_cliente[id_position] + "");
                        params.put("id_tipo_cuenta", 2 + "");

                        return params;

                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(request);

            }
        });
    }

    public void limpiador() {
        valorCuenta.setText("");
        conceptoCuenta.setText("");

    }

    public void select_cliente() {
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Obteniendo la data, por favor espera...");
        pDialog.show();

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
                                    pDialog.dismiss();
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    id_empresa[0] = object.getInt("id_empresa");

                                }


                            } catch (JSONException e) {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText(e.getMessage())
                                        .show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(error.getMessage())
                            .show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(request_select_estado);

        } catch (Exception err) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText(err.toString())
                    .show();
        }

    }
}