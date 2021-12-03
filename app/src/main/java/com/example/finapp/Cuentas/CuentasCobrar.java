package com.example.finapp.Cuentas;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CuentasCobrar extends Fragment {

    //Variablidad
    ListView listviewCuentas;
    int[] id_cuenta, id_empresa, id_metodo_pago, valor, id_cliente, id_tipocuenta;
    String[] empresa_nombre, metodo_pago_nombre, concepto, fecha, nombre_cliente, nombre_tipocuenta;
    TextView btnCrear;

    // Url apis
    String Selectcuentascorar = "https://teorganizo1.000webhostapp.com/cuentas/selectcuentascobrar.php";



    public CuentasCobrar() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CuentasCobrar newInstance(String param1, String param2) {
        CuentasCobrar fragment = new CuentasCobrar();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        //   args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //  mParam1 = getArguments().getString(ARG_PARAM1);
            //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cuentas_cobrar, container, false);
        listviewCuentas = view.findViewById(R.id.listCuentascobrar);
        btnCrear = view.findViewById(R.id.buttonCrear);
        btnCrear.setOnClickListener(v -> {
            agregar_Cuentas agregar_cuentas = new agregar_Cuentas();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, agregar_cuentas, "aCu")
                    .commit();
        });
        select_cuentascobrar();
        return view;
    }


    public void select_cuentascobrar() {

        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Obteniendo la data, por favor espera...");
        pDialog.show();
        StringRequest request_select_cuentas = new StringRequest(Request.Method.POST, Selectcuentascorar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
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
                                    pDialog.dismiss();
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

                                    adapter adapterclass = new adapter();
                                    listviewCuentas.setAdapter(adapterclass);
                                }
                            } catch (Exception er) {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText(er.toString())
                                        .show();
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
        requestQueue.add(request_select_cuentas);

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

            TextView valortv, conceptotv;
            ImageButton txt_edit, txt_delete, txt_view;
            CardView cardview;

            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_view_empleado, parent, false);
            txt_edit = convertView.findViewById(R.id.txt_edti);
            txt_view = convertView.findViewById(R.id.txt_view);
            txt_delete = convertView.findViewById(R.id.txt_delete);
            cardview = convertView.findViewById(R.id.cardview);

            valortv = convertView.findViewById(R.id.cargoEmpleado);
            conceptotv = convertView.findViewById(R.id.nombreEmpleado);

            cardview.setOnClickListener(v -> {
                    //background random color
                    txt_delete.setVisibility(View.VISIBLE);
                    txt_edit.setVisibility(View.VISIBLE);
                    txt_view.setVisibility(View.VISIBLE);
                    valortv.setVisibility(View.GONE);
                    conceptotv.setVisibility(View.GONE);
            });

            //Metodo Editar cliente
            txt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id_cuenta", id_cuenta[position]);
                        bundle.putInt("id_empresa", id_empresa[position]);
                        bundle.putInt("id_metodo_pago", id_metodo_pago[position]);
                        bundle.putInt("valor", valor[position]);
                        bundle.putString("concepto", concepto[position]);
                        bundle.putString("fecha", fecha[position]);
                        bundle.putInt("id_cliente", id_cliente[position]);
                        bundle.putString("nombreCliente", nombre_cliente[position]);
                        bundle.putString("nombreMetodoPago", metodo_pago_nombre[position]);

                        editar_Cuentas editar_cuentas_ = new editar_Cuentas();
                        editar_cuentas_.setArguments(bundle);

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout, editar_cuentas_, "edCuCo")
                                .commit();

                    } catch (Exception er) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(er.toString())
                                .show();
                    }
                }
            });

            //Metodo eliminar
            txt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id_cuenta", id_cuenta[position]);
                        bundle.putInt("id_empresa", id_empresa[position]);
                        bundle.putInt("id_metodo_pago", id_metodo_pago[position]);
                        bundle.putInt("valor", valor[position]);
                        bundle.putString("concepto", concepto[position]);
                        bundle.putString("fecha", fecha[position]);
                        bundle.putInt("id_cliente", id_cliente[position]);

                        eliminarCuentaCobrar eliminarCuentaCobrar = new eliminarCuentaCobrar();
                        eliminarCuentaCobrar.setArguments(bundle);

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout, eliminarCuentaCobrar, "elCuCo")
                                .commit();

                    } catch (Exception er) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(er.toString())
                                .show();                    }
                }
            });


            try {
                valortv.setText(  valor[position] + ""   );
                conceptotv.setText(  concepto[position] + " " + fecha[position]);
            }
            catch (Exception er){
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(er.toString())
                        .show();
            }


            return convertView;
        }

    }
}