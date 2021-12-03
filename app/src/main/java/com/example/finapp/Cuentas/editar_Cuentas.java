package com.example.finapp.Cuentas;

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
import com.example.finapp.Login.Login;
import com.example.finapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class editar_Cuentas extends Fragment {

    //Variables
    public static ArrayList<String> metodopagoArray = new ArrayList<>();
    public static ArrayList<String> clienteArray = new ArrayList<>();
    int id_cuenta, id_empresa, id_metodo_pago, valor, id_cliente, id_tipo_cuenta;
    String concepto, fecha, nombre_cliente, metodo_pago_nombre;
    EditText EditValor, EditFecha, EditConcepto, EditfechaCuenta;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView_cliente;
    int[] id_cliente_, id_empresa_;
    ArrayAdapter<String> adaptadoritems, adaptadoritems_idcliente;
    int id_metodopago, id_position;

    TextView aceptarBtn, regresarBtn, usuario;

    final Calendar myCalendar = Calendar.getInstance();

    //API'S
    String select_metodopago = "https://teorganizo1.000webhostapp.com/metodopago/select.php";
    String select_cliente = "https://teorganizo1.000webhostapp.com/cliente/select.php";
    String update = "https://teorganizo1.000webhostapp.com/cuentas/update.php";

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
            metodo_pago_nombre = bundle.getString("metodo_pago_nombre");
            nombre_cliente = bundle.getString("nombre_cliente");
            concepto = bundle.getString("concepto");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_editar_cuentas_cobrar, container, false);

        usuario = view.findViewById(R.id.usuario);
        usuario.setText(Login.str_usuario);
        EditfechaCuenta = view.findViewById(R.id.fechaCuenta);

        autoCompleteTextView = view.findViewById(R.id.autoComplete_metodopagoEdit);
        autoCompleteTextView_cliente = view.findViewById(R.id.autoComplete_clienteEdit);
        aceptarBtn = view.findViewById(R.id.aceptarBtn);
        regresarBtn = view.findViewById(R.id.regresarBtn);

        adaptadoritems = new ArrayAdapter<String>(getActivity(), R.layout.logintemplate_estado, metodopagoArray);
        adaptadoritems_idcliente = new ArrayAdapter<String>(getActivity(), R.layout.logintemplate_estado, clienteArray);

        autoCompleteTextView.setAdapter(adaptadoritems);
        autoCompleteTextView_cliente.setAdapter(adaptadoritems_idcliente);

        EditValor = view.findViewById(R.id.valorCuenta);
        EditFecha = view.findViewById(R.id.fechaCuenta);
        EditConcepto = view.findViewById(R.id.conceptoCuenta);

        EditValor.setText(valor + "");
        EditFecha.setText(fecha);
        EditConcepto.setText(concepto);

        autoCompleteTextView.setOnItemClickListener((adapterView, view1, i, l) -> {
            //Obtengo el nombre del elemento selecionado, i obtiene la posisicon
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

        EditfechaCuenta.setOnClickListener(v -> new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());


        Select_metodopago();
        select_cliente();
        regresar();
        editar();

        return view;
    }

    private void updateDate() {
        String myFormat = "yy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,
                Locale.ENGLISH);
        EditfechaCuenta.setText(sdf.format(myCalendar.getTime()));
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

    public void select_cliente() {
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
                            id_cliente_ = new int[jsonArray.length()];
                            id_empresa_ = new int[jsonArray.length()];


                            for (int i = 0; i < jsonArray.length(); i++) {

                                pDialog.dismiss();
                                JSONObject object = jsonArray.getJSONObject(i);
                                String nombre = object.getString("nombre");
                                id_cliente_[i] = object.getInt("id_cliente");

                                id_empresa_[0] = object.getInt("id_empresa");

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

    public void regresar() {
        regresarBtn.setOnClickListener(v -> {
            CuentasCobrar cuentasCobrar = new CuentasCobrar();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, cuentasCobrar, "cli")
                    .commit();
        });
    }

    public void editar() {
        aceptarBtn.setOnClickListener(v -> {
            SweetAlertDialog pDialogError = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...");

            if (EditValor.getText().toString().equals("")) {
                pDialogError.setContentText("Debe ingresar el valor de la cuenta").show();

            } else if (EditFecha.getText().toString().equals("")) {
                pDialogError.setContentText("Debe ingresar Debe ingresar la fecha").show();
            } else if (EditConcepto.getText().toString().equals("")) {
                pDialogError.setContentText("Debe ingresar el concepto de la cuenta").show();
            } else {

                SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Se está editando la cuenta cliente, por favor espera...");
                pDialog.show();

                //Tomando valores
                String valorCuenta_str = EditValor.getText().toString().trim();
                String conceptoCuenta_str = EditConcepto.getText().toString().trim();

                StringRequest request = new StringRequest(Request.Method.POST, update, new Response.Listener<String>() {
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
                                    .setTitleText("Se ha editado la cuenta correctamente")
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
                        params.put("id_empresa", id_empresa_[0] + "");
                        params.put("id_metodo_pago", id_metodopago + "");
                        params.put("valor", valorCuenta_str + "");
                        params.put("concepto", conceptoCuenta_str);
                        params.put("fecha", EditfechaCuenta.getText().toString().trim());
                        params.put("id_cliente", id_cliente_[id_position] + "");
                        params.put("id_tipo_cuenta", 2 + "");
                        params.put("id_cuenta", id_cuenta + "");


                        return params;

                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(request);

            }
        });
    }
}