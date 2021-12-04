package com.example.finapp.Estadisticas;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finapp.Login.Login;
import com.example.finapp.R;
import com.example.finapp.Tools;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraficoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraficoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView usuario;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GraficoFragment() {
        // Required empty public constructor
    }



    public static GraficoFragment newInstance(String param1, String param2) {
        GraficoFragment fragment = new GraficoFragment();
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
        View view = inflater.inflate(R.layout.fragment_grafico, container, false);


        PieChart pieChart1 = (PieChart) view.findViewById(R.id.barChart1);
        this.PieChartVentas(view);
        this.PieChartGastos(view);
        usuario = view.findViewById(R.id.usuario);
        usuario.setText(Login.str_usuario);
        Tools.setSystemBarLight(getActivity());
        Tools.setSystemBarColor(getActivity(),R.color.white);
        return view;
    }

    private void PieChartGastos(View view) {
        PieChart pieChart = (PieChart) view.findViewById(R.id.barChart1);
        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(108,"2016"));
        visitors.add(new PieEntry(110,"2017"));
        visitors.add(new PieEntry(98,"2018"));
        visitors.add(new PieEntry(30,"2019"));

        PieDataSet pieDataSet = new PieDataSet(visitors,"Ventas");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(20f);

        pieChart.setCenterText("Gastos");
        pieChart.setCenterTextSize(20f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setData(pieData);

        pieChart.animate();
    }

    private void PieChartVentas(View view) {
        PieChart pieChart = (PieChart) view.findViewById(R.id.barChart);
        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(508,"2016"));
        visitors.add(new PieEntry(408,"2017"));
        visitors.add(new PieEntry(208,"2018"));
        visitors.add(new PieEntry(308,"2019"));

        PieDataSet pieDataSet = new PieDataSet(visitors,"Ventas");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);

        pieDataSet.setValueTextSize(20f);

        pieChart.setCenterText("Ventas");
        pieChart.setCenterTextSize(20f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setData(pieData);

        pieChart.animate();
    }


}