package com.example.finapp.Cuentas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


public class CuentasFragment extends Fragment {

    //Variablidad
    ListView listviewCuentas;
    ProgressDialog progressDialog;
    int[] id_cuenta, id_empresa,id_metodo_pago, valor, id_cliente, id_tipocuenta;
    String[] empresa_nombre, metodo_pago_nombre, concepto, fecha, nombre_cliente, nombre_tipocuenta;


    String Selectgeneral = "https://teorganizo1.000webhostapp.com/cuentas/selectgeneral.php";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CuentasFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CuentasFragment newInstance(String param1, String param2) {
        CuentasFragment fragment = new CuentasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money, container, false);
        Button btnCobrar = (Button) view.findViewById(R.id.cobrar);
        Button btnPagar = (Button) view.findViewById(R.id.pagar);
        listviewCuentas = view.findViewById(R.id.listCuentasGeneral);
        btnCobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CuentasCobrar.class));
            }


        });

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CuentasPagar.class));
            }
        });

        this.select_cuentas();

        return view;

    }

    public void select_cuentas(){

        StringRequest request_select_cuentas = new StringRequest(Request.Method.POST, Selectgeneral,
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

                                    CuentasFragment.adapter adapterclass = new CuentasFragment.adapter();
                                    listviewCuentas.setAdapter(adapterclass);
                                }
                            }
                            catch (Exception er){
                                Toast.makeText(getContext(), er.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e){
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(MainActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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

            TextView valortv, conceptotv, fechatv;


            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cuentatemplate, parent, false);

            valortv = convertView.findViewById(R.id.valorGeneral);
            conceptotv = convertView.findViewById(R.id.conceptoGeneral);
            fechatv = convertView.findViewById(R.id.fechaGeneral);




            try {

                valortv.setText(  valor[position] + ""   );
                conceptotv.setText(  concepto[position]);
                fechatv.setText(fecha[position] +"");
            }
            catch (Exception er){
                Toast.makeText(getContext(), er.toString(), Toast.LENGTH_SHORT).show();
            }


            return convertView;
        }

    }
}