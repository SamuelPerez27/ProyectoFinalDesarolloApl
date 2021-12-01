package com.example.finapp.Cliente;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.example.finapp.MainActivity;
import com.example.finapp.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finapp.Login.Login_registro;
import com.example.finapp.R;
import com.example.finapp.estados.estado_clase_modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClienteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // private static final String ARG_PARAM1 = "param1";
    // private static final String ARG_PARAM2 = "param2";

    //Variable
    TextView total_cliente;
    String total;
    cliente_modelo cliente_modelo;
    ListView listviewCliente;
    Button añadir;


    public int[] id, cedula, id_empresa;
    public String[] nombre, apellido, nombreEmpresa;

    //URL de las API
    String selectTcliente = "https://teorganizo1.000webhostapp.com/cliente/total_cliente.php";
    String selectCliente = "https://teorganizo1.000webhostapp.com/cliente/select.php";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClienteFragment newInstance(String param1, String param2) {
        ClienteFragment fragment = new ClienteFragment();
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

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //inicializar
        View view = inflater.inflate(R.layout.fragment_cliente, container, false);
        total_cliente = view.findViewById(R.id.total_cliente);
        listviewCliente = view.findViewById(R.id.listCLientes);
        añadir = view.findViewById(R.id.anadirCliente);


        //Llamo el metodo que se encarga de obtener el total de empleados
        //select_estado();
        select_cliente();
        //Asignacion
        total_cliente.setText(cliente_modelo.totalCliente);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent agregar = new Intent(getActivity(), agregar_cliente.class);
               Bundle bundle = new Bundle();
               bundle.putInt("id_empresa", id_empresa[0]);
               bundle.putString("nombreEmpresa", nombreEmpresa[0]);
               agregar.putExtra("envio", bundle);
               startActivity(agregar);


            }
        });
        select_estado();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Obteniendo la data, por favor espera...");
        //   progressDialog.show();

        return view;
    }


    //Selecciona los nombres de los estados
    public void select_estado() {

        StringRequest request_select_estado = new StringRequest(Request.Method.POST, selectTcliente,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("cliente");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                progressDialog.dismiss();
                                JSONObject object = jsonArray.getJSONObject(i);
                                total = object.getString("total");
                                cliente_modelo.totalCliente = total;
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(MainActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request_select_estado);

    }


    public void select_cliente() {

        try {
            StringRequest request_select_estado = new StringRequest(Request.Method.POST, selectCliente,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("cliente");

                                id = new int[jsonArray.length()];
                                cedula = new int[jsonArray.length()];
                                nombre = new String[jsonArray.length()];
                                apellido = new String[jsonArray.length()];
                                id_empresa = new int[jsonArray.length()];
                                nombreEmpresa = new String[jsonArray.length()];

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    id[i] = object.getInt("id_cliente");
                                    cedula[i] = object.getInt("cedula");
                                    nombre[i] = object.getString("nombre");
                                    apellido[i] = object.getString("apellido");
                                    id_empresa[i] = object.getInt("id_empresa");
                                    nombreEmpresa[i]  = object.getString("nombreEmpresa");

                                }

                                adapter adapterclass = new adapter();
                                listviewCliente.setAdapter(adapterclass);
                            } catch (JSONException e) {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Toast.makeText(MainActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(request_select_estado);
        } catch (Exception err) {
            Toast.makeText(getActivity(), err.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private class adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return id.length;
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

            TextView cedulaCliente, nombreCliente, apellidoCliente;
            Button editarCliente, eliminarCliente;

            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.clientetemplate, parent, false);

            cedulaCliente = convertView.findViewById(R.id.cedulaCliente);
            nombreCliente = convertView.findViewById(R.id.nombreCliente);
            apellidoCliente = convertView.findViewById(R.id.apellidoCliente);

            editarCliente = convertView.findViewById(R.id.editarCliente);
            eliminarCliente = convertView.findViewById(R.id.eliminarCliente);

            //Metodo Editar cliente
            editarCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id[position]);
                        bundle.putInt("cedula", cedula[position]);
                        bundle.putString("nombre", nombre[position]);
                        bundle.putString("apellido", apellido[position]);
                        bundle.putInt("id_empresa", id_empresa[position]);

                        Intent intent = new Intent(getActivity(), com.example.finapp.Cliente.editarCliente.class);
                        intent.putExtra("envio", bundle);
                        startActivity(intent);

                    } catch (Exception er) {
                        Toast.makeText(getActivity(), er.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //Metodo eliminar
            eliminarCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id[position]);
                        bundle.putInt("cedula", cedula[position]);
                        bundle.putString("nombre", nombre[position]);
                        bundle.putString("apellido", apellido[position]);
                        bundle.putInt("id_empresa", id_empresa[position]);

                        Intent intent = new Intent(getActivity(), com.example.finapp.Cliente.eliminarCliente.class);
                        intent.putExtra("envio", bundle);
                        startActivity(intent);

                    } catch (Exception er) {
                        Toast.makeText(getActivity(), er.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            try {

                nombreCliente.setText(nombre[position]);
                apellidoCliente.setText(apellido[position]);
                cedulaCliente.setText(cedula[position] + "");
            } catch (Exception er) {
                Toast.makeText(getActivity(), er.toString(), Toast.LENGTH_SHORT).show();
            }


            return convertView;
        }


    }

}