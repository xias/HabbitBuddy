package com.example.android.effectivenavigation.summary;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.effectivenavigation.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerformanceSummaryFragment extends Fragment {


    public PerformanceSummaryFragment() {
        // Required empty public constructor
    }


    private void getLables(String startDate){
//updates the lables for the summary

    }

    private void addEntries(){
        //add the score date to entry array

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_performance_summary, container, false);
        LineChart lineChart = (LineChart) v.findViewById(R.id.chart);


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0));
        entries.add(new Entry(1, 1));
        entries.add(new Entry(2, 2));
        entries.add(new Entry(3, 3));
        entries.add(new Entry(2, 4));
        entries.add(new Entry(3, 5));
        entries.add(new Entry(4, 6));
        entries.add(new Entry(5, 7));
        entries.add(new Entry(6, 8));
        entries.add(new Entry(5, 9));
        entries.add(new Entry(6, 10));
        entries.add(new Entry(7, 11));


        LineDataSet dataset = new LineDataSet(entries, "My habit forming progress");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0;i<28;i++){
            labels.add("Day "+String.valueOf(i));
        }


        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);


        lineChart.setData(data);
        lineChart.animateY(1000);
        YAxis y = lineChart.getAxisLeft();
        y.setStartAtZero(true);
        y.setAxisMaxValue(28);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getLegend().setEnabled(false);

        Button logout = (Button) v.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getContext().openFileOutput("config.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write("");
                    outputStreamWriter.close();
                }
                catch (IOException e) {
                    Log.e("Exception", "LOGOUT FAILED: " + e.toString());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getActivity().finishAffinity();
                }


            }
        });







        return v;
    }

}
