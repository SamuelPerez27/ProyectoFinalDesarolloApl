package com.example.finapp.empleado;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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
import com.example.finapp.Login.Login;
import com.example.finapp.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmpleadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmpleadoFragment extends Fragment {

    String [] nombresEmpleado, apellidosEmpleado, cargosEmpleado, cedulaEmpleado, empresaEmpleado;

    ListView listViewEmpleados;
    Empleado_Modelo empleado;
    TextView usuario;
    boolean paso = false;

    String selectEmpresa = "https://teorganizo1.000webhostapp.com/empleado/selectempresa.php";


    public EmpleadoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static EmpleadoFragment newInstance(String param1, String param2) {
        EmpleadoFragment fragment = new EmpleadoFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_empleado, container, false);
        listViewEmpleados = view.findViewById(R.id.list_view_empleado);
        if (!paso){
            select_empleado();
            paso=true;
        }
        usuario = view.findViewById(R.id.usuario);
        usuario.setText(Login.str_usuario);
        Tools.setSystemBarLight(getActivity());
        Tools.setSystemBarColor(getActivity(),R.color.white);
        return view;
    }

    public void select_empleado(){
        StringRequest request_select_empleado = new StringRequest(Request.Method.GET, selectEmpresa + "?nombre="+Login.str_nombre,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("empleado");

                            nombresEmpleado = new String[jsonArray.length()];
                            cedulaEmpleado = new String[jsonArray.length()];
                            apellidosEmpleado= new String[jsonArray.length()];
                            empresaEmpleado= new String[jsonArray.length()];
                            cargosEmpleado = new String[jsonArray.length()];

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String nombre = object.getString("nombre");
                                String cedula = object.getString("cedula");
                                String apellido = object.getString("apellido");
                                String empresa = object.getString("nombre_empresa");
                                String cargo = object.getString("nombre_cargo");

                                nombresEmpleado[i]=object.getString("nombre");
                                cedulaEmpleado[i]=object.getString("cedula");
                                apellidosEmpleado[i]= object.getString("apellido");
                                empresaEmpleado[i]=object.getString("nombre_empresa");
                                cargosEmpleado[i]= object.getString("nombre_cargo");

                                empleado = new Empleado_Modelo(nombre,cedula,apellido,empresa, cargo);

                                Empleado_Modelo.empleado_list.add(empleado);
                            }
                            AdapterEmpleado adapterEmpleado = new AdapterEmpleado();
                            listViewEmpleados.setAdapter(adapterEmpleado);

                        }
                        catch (JSONException e){
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(MainActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request_select_empleado);
    }

    public class AdapterEmpleado extends BaseAdapter {

        @Override
        public int getCount() {
            return cedulaEmpleado.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView nombreEmpleado, cargoEmpleado;
            ImageButton txt_edit, txt_delete, txt_view;
            CardView cardview;

            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_view_empleado, parent, false);
            nombreEmpleado = convertView.findViewById(R.id.nombreEmpleado);
            cargoEmpleado = convertView.findViewById(R.id.cargoEmpleado);

            txt_edit = convertView.findViewById(R.id.txt_edti);
            txt_view = convertView.findViewById(R.id.txt_view);
            txt_delete = convertView.findViewById(R.id.txt_delete);
            cardview = convertView.findViewById(R.id.cardview);

            cardview.setOnClickListener(v -> {
                //background random color
                txt_delete.setVisibility(View.VISIBLE);
                txt_edit.setVisibility(View.VISIBLE);
                txt_view.setVisibility(View.VISIBLE);
                nombreEmpleado.setVisibility(View.GONE);
                cargoEmpleado.setVisibility(View.GONE);
            });
            try {
                nombreEmpleado.setText(nombresEmpleado[position]+ " " +apellidosEmpleado[position]);
                cargoEmpleado.setText(cargosEmpleado[position]);

            }catch (Exception er){
                Toast.makeText(getActivity(), er.toString(), Toast.LENGTH_SHORT).show();
            }

            try {
                txt_edit.setOnClickListener(v -> {
                    EmpleadoEditFragment empleadoEditFragment = new EmpleadoEditFragment();

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout, empleadoEditFragment, "emp")
                            .commit();
                });
                txt_view.setOnClickListener(v ->{

                });
                txt_delete.setOnClickListener(v->{

                });
            }catch (Exception er){
                Toast.makeText(getActivity(), er.toString(), Toast.LENGTH_SHORT).show();
            }



            return convertView;
        }
    }
}