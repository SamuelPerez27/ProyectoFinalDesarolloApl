package com.example.finapp.Cliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Variable
    TextView total_cliente;



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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cliente, container, false);

        Button button= (Button) view.findViewById(R.id.butonprueba);
        TextView kk = view.findViewById(R.id.total_cliente2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "sdsdds", Toast.LENGTH_LONG).show();
                kk.setText("Juan");
            }
        });
        return view;
    }





    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        try {
             view = inflater.inflate(R.layout.fragment_cliente,
                    container, false);
            Button button = (Button) view.findViewById(R.id.butonprueba);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(null, "sdsdds", Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (Exception er){
            Toast.makeText( getContext() , er.toString(),Toast.LENGTH_LONG).show();
        }
        return view;
    }*/
}