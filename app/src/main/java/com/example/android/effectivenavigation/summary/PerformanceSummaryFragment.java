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
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static com.example.android.effectivenavigation.MainActivity.name;


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
        final LineChart lineChart1 = (LineChart) v.findViewById(R.id.chart);




        Firebase mRootRef =  new Firebase("https://habitbuddy-9bca7.firebaseio.com/");
        Firebase mUsersRef = mRootRef.child("users");


        Firebase planRef = mUsersRef.child(name).child("schedule").child("exercise");
//        Firebase recordRef = mUsersRef.child(name).child("schedule").child("exercise").child("record");
        final String[] sched = new String[2];

        final LineDataSet[] dataset = new LineDataSet[1];

        planRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //fetch data
                sched[0] = dataSnapshot.child("plan").getValue(String.class);
                sched[1] = dataSnapshot.child("record").getValue(String.class);
                Log.v("perf debug",sched[0]+"|||||"+sched[1]);

                ArrayList<Entry> entries = new ArrayList<>();


                String[] pla = sched[0].split(",");
                String[] rec = sched[1].split(",");

                int sco=0;
                int last=0;
                for(int i=0;i<pla.length;i++){
                    if(rec[i].equals("1")){
                        last=i;
                    }

                }



                for(int i=0;i<last+1;i++){
//                    adder = Integer.getInteger(rec[i])-Integer.getInteger(pla[i]);
                    Log.v("summ debug",pla[i]+"||"+rec[i]+"|||"+String.valueOf(i));
                    if(Integer.valueOf(pla[i])==0){
                        sco+=Integer.valueOf(rec[i]);
                    }else{
                        sco+=Integer.valueOf(rec[i])*2-1;
                    }
                    if(sco<0){
                        sco = 0;
                    }
                    entries.add(new Entry(sco, i));


                }



                dataset[0] = new LineDataSet(entries, "My habit forming progress");


                ArrayList<String> labels = new ArrayList<String>();
                for(int i=0;i<28;i++){
                    labels.add("Day "+String.valueOf(i));
                }

                LineData data = new LineData(labels, dataset[0]);
                dataset[0].setColors(ColorTemplate.COLORFUL_COLORS); //
                dataset[0].setDrawCubic(false);
                dataset[0].setDrawFilled(true);


                lineChart1.setData(data);
                lineChart1.animateY(1000);
                YAxis y = lineChart1.getAxisLeft();
                y.setStartAtZero(true);
                y.setAxisMaxValue(28);
                lineChart1.getAxisRight().setDrawLabels(false);
                lineChart1.getLegend().setEnabled(false);








            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0;i<28;i++){
            labels.add("Day "+String.valueOf(i));
        }

        if(dataset[0]==null){
            dataset[0] = new LineDataSet(new ArrayList<Entry>(), "My habit forming progress");
        }

        LineData data = new LineData(labels, dataset[0]);
        dataset[0].setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset[0].setDrawCubic(false);
        dataset[0].setDrawFilled(true);


        lineChart1.setData(data);
        lineChart1.animateY(1000);
        YAxis y = lineChart1.getAxisLeft();
        y.setStartAtZero(true);
        y.setAxisMaxValue(28);
        lineChart1.getAxisRight().setDrawLabels(false);
        lineChart1.getLegend().setEnabled(false);

//
//
//
//


//
//
//        ArrayList<Entry> entries = new ArrayList<>();
//
//
//
//        entries.add(new Entry(0, 0));
//        entries.add(new Entry(1, 1));
//        entries.add(new Entry(2, 2));
//        entries.add(new Entry(3, 3));
//        entries.add(new Entry(2, 4));
//        entries.add(new Entry(3, 5));
//        entries.add(new Entry(4, 6));
//        entries.add(new Entry(5, 7));
//        entries.add(new Entry(6, 8));
//        entries.add(new Entry(5, 9));
//        entries.add(new Entry(6, 10));
//        entries.add(new Entry(7, 11));
//
//
//
//
//
//        LineDataSet dataset = new LineDataSet(entries, "My habit forming progress");
//
//        ArrayList<String> labels = new ArrayList<String>();
//        for(int i=0;i<28;i++){
//            labels.add("Day "+String.valueOf(i));
//        }
//
//
//        LineData data = new LineData(labels, dataset);
//        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
//        dataset.setDrawCubic(true);
//        dataset.setDrawFilled(true);
//
//
//        lineChart.setData(data);
//        lineChart.animateY(1000);
//        YAxis y = lineChart.getAxisLeft();
//        y.setStartAtZero(true);
//        y.setAxisMaxValue(28);
//        lineChart.getAxisRight().setDrawLabels(false);
//        lineChart.getLegend().setEnabled(false);

        Button logout = (Button) v.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    name = null;
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
